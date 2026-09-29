/*
 * Copyright 2026 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.media3.ui.compose.material3

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.BitmapLoader
import androidx.media3.common.util.UnstableApi
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.guava.await

@UnstableApi
actual suspend fun loadArtworkImageBitmap(
  bitmapLoader: BitmapLoader?,
  metadata: MediaMetadata,
): ImageBitmap? {
  if (bitmapLoader == null) return null
  val result = bitmapLoader.loadBitmapFromMetadata(metadata) ?: return null
  return when (result) {
    is ListenableFuture<*> -> {
      val bitmap = result.await() as? Bitmap
      bitmap?.asImageBitmap()
    }
    is Bitmap -> result.asImageBitmap()
    else -> null
  }
}
