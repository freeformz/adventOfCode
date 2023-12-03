plugins {
    kotlin("jvm") version "1.9.21"
}


repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

tasks {
    compileKotlin {
        compilerOptions {
            freeCompilerArgs.add("-Xassertions=always-enable")
        }
        kotlinOptions {
            jvmTarget = "19"
        }
    }

    sourceSets {
        main {
            //java.srcDirs("2022/src")
            java.srcDirs("kotlin/src")
        }
    }

    wrapper {
        gradleVersion = "7.6"
    }
}
