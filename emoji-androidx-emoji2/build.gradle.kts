plugins {
  id("org.jetbrains.dokka")
  id("org.jetbrains.kotlin.multiplatform")
  id("org.jetbrains.kotlin.native.cocoapods")
  id("com.android.library")
  id("org.jetbrains.kotlin.plugin.parcelize")
  id("me.tylerbwong.gradle.metalava")
  id("com.vanniktech.maven.publish")
  id("app.cash.licensee")
}

licensee {
  allow("Apache-2.0")
}

metalava {
  filename.set("api/current.txt")
}

kotlin {
  applyDefaultHierarchyTemplate()

  androidTarget {
    publishLibraryVariants("release")
  }
  iosX64()
  iosArm64()
  iosSimulatorArm64()
  jvm()
  jvmToolchain(11)

  sourceSets {
    val commonMain by getting {
      dependencies {
        api(project(":emoji"))
      }
    }

    val commonTest by getting {
      dependencies {
        implementation(libs.kotlin.test)
      }
    }

    val androidMain by getting {
      dependencies {
        api(libs.androidx.emoji2)
      }
    }

    val androidUnitTest by getting {
      dependencies {
        implementation(libs.kotlin.test.junit)
      }
    }

    val jvmTest by getting {
      dependencies {
        implementation(libs.kotlin.test.junit)
      }
    }
  }

  cocoapods {
    version = project.property("VERSION_NAME").toString()
    summary = "emoji-androidx-emoji2"
    homepage = "https://github.com/vanniktech/Emoji"
    name = "EmojiAndroidxEmoji2"

    framework {
      isStatic = true
    }
  }
}

android {
  namespace = "com.vanniktech.emoji.androidxemoji2"

  compileSdk = libs.versions.compileSdk.get().toInt()

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }

  defaultConfig {
    minSdk = libs.versions.minSdk.get().toInt()
  }

  resourcePrefix = "emoji_androidxemoji2_"
}

dependencies {
  api(project(":emoji"))
}
