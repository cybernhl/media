// Copyright (C) 2025 The Android Open Source Project
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

plugins {
    id("media3.kotlin-multiplatform")
    id("media3.android-kmp-library")
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlin.compose.compiler)
}

group = "androidx.media3.ui.compose.material3"

kotlin {
    targets.withType(KotlinMultiplatformAndroidLibraryTarget::class.java).configureEach {
        namespace = "androidx.media3.ui.compose.material3"
        androidResources {
            enable = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                compileOnly(project(":lib-common-lite"))
                compileOnly(project(":lib-ui-compose"))

                implementation(libs.jetbrains.compose.runtime)
                implementation(libs.jetbrains.compose.foundation)
                implementation(libs.jetbrains.compose.material3)
                implementation(libs.jetbrains.compose.ui)
                implementation(libs.jetbrains.compose.ui.util)
                implementation(libs.jetbrains.compose.components.resources)
                implementation(libs.kotlinx.coroutines.core)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.core)
                implementation(libs.kotlinx.coroutines.guava)
            }
        }

        val jvmMain by getting {
            dependencies {
            }
        }
    }
}

compose.resources {
    packageOfResClass = "androidx.media3.ui.compose.material3"
    publicResClass = true
}
