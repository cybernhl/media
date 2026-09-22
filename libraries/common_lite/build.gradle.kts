import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

// Copyright (C) 2026 The Android Open Source Project

plugins {
    id("media3.kotlin-multiplatform")
    id("media3.android-kmp-library")
}

group = "androidx.media3.common"
version = "1.0-SNAPSHOT"

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, "seconds")
}

kotlin {
    // JVM and Android targets are automatically added and configured by the plugins.
    
    // Config android namespace which is module-specific
    targets.withType(KotlinMultiplatformAndroidLibraryTarget::class.java).configureEach {
        namespace = "androidx.media3.common"
    }

    sourceSets {
        val commonMain by getting {
            dependencies {

                // Pure Kotlin Multiplatform dependencies
                implementation(libs.kotlinx.datetime)
                implementation(libs.kermit)
                implementation(libs.kotlinx.coroutines.core)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        val jvmMain by getting {
            dependencies {
            }
        }
        
        val androidMain by getting {
            dependencies {
            }
        }
    }
}
