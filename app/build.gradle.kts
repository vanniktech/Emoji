plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
}

kotlin {
  jvmToolchain(11)
}

android {
  val shouldSign = project.hasProperty("RISK_BATTLE_SIMULATOR_RELEASE_STORE_FILE") &&
    project.hasProperty("RISK_BATTLE_SIMULATOR_RELEASE_STORE_PASSWORD") &&
    project.hasProperty("RISK_BATTLE_SIMULATOR_RELEASE_KEY_ALIAS") &&
    project.hasProperty("RISK_BATTLE_SIMULATOR_RELEASE_KEY_PASSWORD")

  namespace = "com.vanniktech.emoji.sample"

  compileSdk = libs.versions.compileSdk.get().toInt()

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }

  defaultConfig {
    applicationId = "com.vanniktech.emoji.sample"
    minSdk = libs.versions.minSdk.get().toInt()
    targetSdk = libs.versions.targetSdk.get().toInt()
    versionCode = 1
    versionName = project.property("VERSION_NAME").toString()

    vectorDrawables.useSupportLibrary = true

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildFeatures {
    viewBinding = true
  }

  signingConfigs {
    if (shouldSign) {
      create("release") {
        storeFile = file(project.property("RISK_BATTLE_SIMULATOR_RELEASE_STORE_FILE").toString())
        storePassword = project.property("RISK_BATTLE_SIMULATOR_RELEASE_STORE_PASSWORD").toString()
        keyAlias = project.property("RISK_BATTLE_SIMULATOR_RELEASE_KEY_ALIAS").toString()
        keyPassword = project.property("RISK_BATTLE_SIMULATOR_RELEASE_KEY_PASSWORD").toString()
      }
    }
  }

  buildTypes {
    release {
      signingConfigs.findByName("release")?.let { signingConfig = it }
      isMinifyEnabled = true
      isShrinkResources = true
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
    }
  }
}

dependencies {
  implementation(project(":emoji-androidx-emoji2"))
  implementation(project(":emoji-facebook"))
  implementation(project(":emoji-google"))
  implementation(project(":emoji-google-compat"))
  implementation(project(":emoji-ios"))
  implementation(project(":emoji-material"))
  implementation(project(":emoji-twitter"))
  implementation(libs.timber)
}

dependencies {
  debugImplementation(libs.leakcanary.android)
}

dependencies {
  androidTestImplementation(libs.androidx.test.espresso)
  androidTestImplementation(libs.androidx.test.ext)
  androidTestImplementation(libs.androidx.test.rules)
  androidTestImplementation(libs.falcon)
  androidTestImplementation(libs.junitintegrationrules)
  androidTestImplementation(libs.screengrab)
  androidTestImplementation(libs.espressocoreutils)
}
