// Copyright (C) 2026 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
    id("com.android.kotlin.multiplatform.library")
}

group = "androidx.media3"

val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

extensions.configure<KotlinMultiplatformExtension>("kotlin") {
    // Note: jvm() is provided by media3.kotlin-multiplatform if used together.
    
    targets.withType(KotlinMultiplatformAndroidLibraryTarget::class.java).configureEach {
        compileSdk = libs.findVersion("compileSdkVersion").get().requiredVersion.toInt()
        minSdk = libs.findVersion("minSdkVersion").get().requiredVersion.toInt()
        
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
}
