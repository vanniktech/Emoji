import com.android.build.gradle.api.AndroidBasePlugin
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
  repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
  }
  dependencies {
    classpath(libs.plugin.android.cache.fix)
    classpath(libs.plugin.androidgradleplugin)
    classpath(libs.plugin.dokka)
    classpath(libs.plugin.kotlin)
    classpath(libs.plugin.licensee)
    classpath(libs.plugin.metalava)
    classpath(libs.plugin.publish)
  }
}

plugins {
  alias(libs.plugins.codequalitytools)
  alias(libs.plugins.dependencygraphgenerator)
}

codeQualityTools {
  lint {
    lintConfig = rootProject.file("lint.xml")
    checkAllWarnings = true
    textReport = true
  }
  checkstyle {
    enabled = false // Kotlin only.
  }
  pmd {
   enabled = false // Kotlin only.
  }
  ktlint {
    toolVersion = libs.versions.ktlint.get()
  }
  detekt {
    enabled = false
  }
  cpd {
    enabled = false
  }
}

allprojects {
  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
  }
}

subprojects {
  plugins.withType<AndroidBasePlugin> {
    apply(plugin = "org.gradle.android.cache-fix")
  }

  tasks.withType<Test>().configureEach {
    testLogging.exceptionFormat = TestExceptionFormat.FULL
  }

  tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
      freeCompilerArgs.addAll(
        "-Xconsistent-data-class-copy-visibility",
        "-Xannotation-default-target=param-property",
      )
    }
  }
}
