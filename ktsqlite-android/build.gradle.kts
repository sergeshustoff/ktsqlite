plugins {
    id("com.android.library")
}

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