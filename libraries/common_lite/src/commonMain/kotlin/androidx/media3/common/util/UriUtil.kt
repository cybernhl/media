/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package androidx.media3.common.util

/** Utility methods for manipulating URIs in Pure Kotlin. */
@UnstableApi
public object UriUtil {

    private const val SCHEME_COLON = 0
    private const val PATH = 1
    private const val QUERY = 2
    private const val FRAGMENT = 3

    public fun resolve(baseUri: String?, referenceUri: String?): String {
        val uri = StringBuilder()
        val base = baseUri ?: ""
        val ref = referenceUri ?: ""

        val refIndices = getUriIndices(ref)
        if (refIndices[SCHEME_COLON] != -1) {
            uri.append(ref)
            removeDotSegments(uri, refIndices[PATH], refIndices[QUERY])
            return uri.toString()
        }

        val baseIndices = getUriIndices(base)
        if (refIndices[PATH] == 0) {
            return uri.append(base, 0, baseIndices[PATH]).append(ref).toString()
        }

        if (refIndices[PATH] != refIndices[QUERY] && ref[refIndices[PATH]] == '/') {
            uri.append(base, 0, baseIndices[PATH]).append(ref)
            removeDotSegments(uri, baseIndices[PATH] + refIndices[PATH], baseIndices[PATH] + refIndices[QUERY])
            return uri.toString()
        }

        if (baseIndices[SCHEME_COLON] + 2 < baseIndices[PATH] &&
            base[baseIndices[SCHEME_COLON] + 1] == '/' &&
            base[baseIndices[SCHEME_COLON] + 2] == '/'
        ) {
            uri.append(base, 0, baseIndices[PATH])
            uri.append(ref)
            removeDotSegments(uri, baseIndices[PATH], baseIndices[PATH] + refIndices[QUERY])
            return uri.toString()
        }

        val lastSlash = base.lastIndexOf('/', baseIndices[QUERY] - 1)
        val pathIndex = if (lastSlash == -1) baseIndices[PATH] else lastSlash + 1
        uri.append(base, 0, pathIndex).append(ref)
        removeDotSegments(uri, baseIndices[PATH], pathIndex + refIndices[QUERY])
        return uri.toString()
    }

    private fun getUriIndices(uri: String): IntArray {
        val indices = IntArray(4)
        if (uri.isEmpty()) {
            indices[SCHEME_COLON] = -1
            indices[PATH] = 0
            indices[QUERY] = 0
            indices[FRAGMENT] = 0
            return indices
        }

        val length = uri.length
        var colonIndex = uri.indexOf(':')
        if (colonIndex != -1) {
            for (i in 0 until colonIndex) {
                val c = uri[i]
                if (c == '/' || c == '?' || c == '#') {
                    colonIndex = -1
                    break
                }
            }
        }

        if (colonIndex != -1) {
            indices[SCHEME_COLON] = colonIndex
        } else {
            indices[SCHEME_COLON] = -1
        }

        var pathIndex = if (colonIndex != -1) colonIndex + 1 else 0
        if (pathIndex < length - 1 && uri[pathIndex] == '/' && uri[pathIndex + 1] == '/') {
            pathIndex = uri.indexOf('/', pathIndex + 2)
            if (pathIndex == -1 || pathIndex > length) {
                pathIndex = length
            }
        }
        indices[PATH] = pathIndex

        var queryIndex = uri.indexOf('?', pathIndex)
        var fragmentIndex = uri.indexOf('#', if (queryIndex != -1) queryIndex else pathIndex)

        if (queryIndex != -1 && fragmentIndex != -1 && fragmentIndex < queryIndex) {
            queryIndex = -1
        }

        indices[QUERY] = if (queryIndex != -1) queryIndex else if (fragmentIndex != -1) fragmentIndex else length
        indices[FRAGMENT] = if (fragmentIndex != -1) fragmentIndex else length

        return indices
    }

    private fun removeDotSegments(uri: StringBuilder, offset: Int, limit: Int) {
        var start = offset
        var end = limit
        if (start >= end) return

        if (uri[start] == '/') {
            start++
        }
        var segmentStart = start
        var i = start
        while (i <= end) {
            var nextSlash = if (i == end) i else -1
            if (i < end && uri[i] == '/') {
                nextSlash = i
            }
            if (nextSlash != -1) {
                if (nextSlash == segmentStart + 1 && uri[segmentStart] == '.') {
                    uri.delete(segmentStart, nextSlash + 1)
                    end -= (nextSlash + 1 - segmentStart)
                    i = segmentStart
                } else if (nextSlash == segmentStart + 2 && uri[segmentStart] == '.' && uri[segmentStart + 1] == '.') {
                    val prevSlash = uri.lastIndexOf('/', segmentStart - 2)
                    val deleteStart = if (prevSlash < offset) offset else prevSlash + 1
                    uri.delete(deleteStart, nextSlash + 1)
                    end -= (nextSlash + 1 - deleteStart)
                    segmentStart = deleteStart
                    i = deleteStart
                } else {
                    i++
                    segmentStart = i
                }
            } else {
                i++
            }
        }
    }
}
