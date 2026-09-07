plugins {
  id("org.jetbrains.dokka")
  id("org.jetbrains.kotlin.multiplatform")
  id("org.jetbrains.kotlin.native.cocoapods")
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
    namespace = "com.vanniktech.emoji.google"
    compileSdk = libs.versions.compileSdk.get().toInt()
    minSdk = libs.versions.minSdk.get().toInt()
    androidResources.resourcePrefix = "emoji_google_"

    withHostTest {
      isIncludeAndroidResources = true
    }

    androidResources {
      enable = true
    }
  }
  iosX64()
  iosArm64()
  iosSimulatorArm64()
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

  cocoapods {
    version = project.property("VERSION_NAME").toString()
    summary = "emoji-google"
    homepage = "https://github.com/vanniktech/Emoji"
    name = "EmojiGoogle"

    framework {
      isStatic = true
    }
  }
}
