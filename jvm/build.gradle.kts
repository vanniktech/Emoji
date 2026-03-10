plugins {
  application
  id("org.jetbrains.kotlin.jvm")
}

application {
  mainClass.set("com.vanniktech.emoji.jvm.MainKt")
}

kotlin {
  jvmToolchain(21)
}

dependencies {
  implementation(project(":emoji-ios"))
}
