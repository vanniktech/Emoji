plugins {
  id("org.jetbrains.dokka")
  id("org.jetbrains.kotlin.multiplatform")
  id("com.android.kotlin.multiplatform.library")
  id("org.jetbrains.kotlin.plugin.parcelize")
  id("me.tylerbwong.gradle.metalava")
  id("com.vanniktech.maven.publish")
  id("app.cash.licensee")
  id("com.android.lint")
}

licensee {
  allow("Apache-2.0")
}

metalava {
  filename.set("api/current.txt")
}

kotlin {
  applyDefaultHierarchyTemplate()

  android {
    namespace = "com.vanniktech.emoji.material"
    compileSdk = libs.versions.compileSdk.get().toInt()
    minSdk = libs.versions.minSdk.get().toInt()
    androidResources.resourcePrefix = "emoji"

    withHostTest {
      isIncludeAndroidResources = true
    }

    androidResources {
      enable = true
    }
  }
  jvm()
  jvmToolchain(21)

  sourceSets {
    commonMain {
      dependencies {
        api(project(":emoji"))
      }
    }

    commonTest {
      dependencies {
        implementation(libs.kotlin.test)
      }
    }

    androidMain {
      dependencies {
        api(libs.material)
      }
    }

    androidUnitTest {
      dependencies {
        implementation(libs.kotlin.test.junit)
      }
    }

    jvmTest {
      dependencies {
        implementation(libs.kotlin.test.junit)
      }
    }
  }
}
