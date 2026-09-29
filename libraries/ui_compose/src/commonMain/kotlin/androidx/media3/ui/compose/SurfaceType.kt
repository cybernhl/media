/*
 * Copyright 2024 The Android Open Source Project
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
package androidx.media3.ui.compose

import androidx.media3.common.util.UnstableApi

/**
 * The type of surface used for media playbacks.
 */
@UnstableApi
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE, AnnotationTarget.TYPE_PARAMETER)
public annotation class SurfaceType

/** Surface type to create SurfaceView. */
@UnstableApi public const val SURFACE_TYPE_SURFACE_VIEW: Int = 1

/** Surface type to create TextureView. */
@UnstableApi public const val SURFACE_TYPE_TEXTURE_VIEW: Int = 2
