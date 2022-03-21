plugins {
    id("com.android.library")
    id("maven-publish")
}

android {
    compileSdk = 31
    defaultConfig {
        minSdk = 23
        targetSdk = 31
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}
publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.github.sergeshustoff"
            artifactId = "ktsqlite-android"
            version = "1.0.0"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}

dependencies {
    api(project(":ktsqlite"))
}