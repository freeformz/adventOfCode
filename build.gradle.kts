plugins {
    kotlin("jvm") version "1.9.21"
}


repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "19"
        }
    }

    sourceSets {
        main {
            java.srcDirs("2022/src")
            java.srcDirs("2023/src")
        }
    }

    wrapper {
        gradleVersion = "7.6"
    }
}
