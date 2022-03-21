plugins {
    id("com.android.library")
    id("maven-publish")
}
group = "com.github.sergeshustoff"
version = "1.0.0"

android {
    compileSdk = 31
    defaultConfig {
        minSdk = 23
        targetSdk = 31
    }
}

dependencies {
    api(project(":ktsqlite"))
}