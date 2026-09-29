/*
 * Copyright (C) 2020 The Android Open Source Project
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

package androidx.media3.buildlogic

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

fun Project.configureCommonConfig(android: Any, libs: VersionCatalog) {
  try {
    val targetSdkInt = libs.findVersion("compileSdkVersion").get().requiredVersion.toInt()
    val minSdkInt = libs.findVersion("minSdkVersion").get().requiredVersion.toInt()

    // Configure compileSdk via reflection
    try {
      val setCompileSdk = android.javaClass.getMethod("setCompileSdk", Integer::class.java)
      setCompileSdk.invoke(android, targetSdkInt)
    } catch (_: Throwable) {
      try {
        val setCompileSdk = android.javaClass.getMethod("setCompileSdk", Int::class.javaPrimitiveType)
        setCompileSdk.invoke(android, targetSdkInt)
      } catch (_: Throwable) {}
    }

    // Configure defaultConfig
    try {
      val getDefaultConfig = android.javaClass.getMethod("getDefaultConfig")
      val defaultConfig = getDefaultConfig.invoke(android)
      if (defaultConfig != null) {
        try {
          val setMinSdk = defaultConfig.javaClass.getMethod("setMinSdk", Integer::class.java)
          setMinSdk.invoke(defaultConfig, minSdkInt)
        } catch (_: Throwable) {
          try {
            val setMinSdk = defaultConfig.javaClass.getMethod("setMinSdk", Int::class.javaPrimitiveType)
            setMinSdk.invoke(defaultConfig, minSdkInt)
          } catch (_: Throwable) {}
        }
      }
    } catch (_: Throwable) {}

    // Configure compileOptions
    try {
      val getCompileOptions = android.javaClass.getMethod("getCompileOptions")
      val compileOptions = getCompileOptions.invoke(android)
      if (compileOptions != null) {
        val setSourceCompatibility = compileOptions.javaClass.getMethod("setSourceCompatibility", JavaVersion::class.java)
        val setTargetCompatibility = compileOptions.javaClass.getMethod("setTargetCompatibility", JavaVersion::class.java)
        setSourceCompatibility.invoke(compileOptions, JavaVersion.VERSION_1_8)
        setTargetCompatibility.invoke(compileOptions, JavaVersion.VERSION_1_8)
      }
    } catch (_: Throwable) {}

    dependencies {
      // Add the missing annotations to all compile classpaths to satisfy Javac
      // when it parses Guava and other dependencies, without bundling them in the release.
      val annotationLibs =
        listOf(
          "jsr305", // E.g. javax.annotation.meta.When.MAYBE
          "errorprone-annotations",
          "checkerframework-qual",
          "kotlin-annotations-jvm", // E.g. MigrationStatus.STRICT
          "j2objc-annotations", // E.g. j2objc.annotations.ReflectionSupport.Level.FULL
          "animal-sniffer-annotations",
        )

      for (libName in annotationLibs) {
        val lib = libs.findLibrary(libName).get()
        add("compileOnly", lib)
        add("testCompileOnly", lib)
        add("androidTestCompileOnly", lib)
      }
    }

    tasks.withType<JavaCompile>().configureEach { options.compilerArgs.add("-Xlint:-options") }
  } catch (_: Throwable) {}

  plugins.withId("org.jetbrains.kotlin.android") {
    extensions.configure<KotlinAndroidProjectExtension>("kotlin") {
      compilerOptions {
        freeCompilerArgs.add("-Xannotation-default-target=param-property")
        freeCompilerArgs.add("-Xwarning-level=OPT_IN_USAGE:error")
      }
    }
  }

  val media3Module = Media3Modules.EXTERNAL_MODULES[project.name]
  if (media3Module?.allowKt == false) {
    // Using afterEvaluate is required to ensure we run after the Kotlin Gradle plugin
    // has attached its default kotlin-stdlib dependencies to the configurations.
    project.afterEvaluate {
      configurations.configureEach {
        val configName = name
        dependencies.removeIf {
          !configName.contains("test", ignoreCase = true) &&
            !configName.contains("androidTest", ignoreCase = true) &&
            it.group == "org.jetbrains.kotlin" &&
            it.name.startsWith("kotlin-stdlib")
        }
      }
    }
  }
}
