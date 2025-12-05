plugins {
    this.id("org.jetbrains.kotlin.multiplatform") version "2.3.0-RC2"
    this.id("com.github.ben-manes.versions") version "0.53.0"
}

kotlin {
    this.mingwX64("windows")
    this.sourceSets {
        val main by this.creating {
            this.kotlin.srcDir("src/main/kotlin")
            this.resources.srcDir("src/main/resources")
            this.dependencies {
                this.implementation(this.project(":common"))
            }
        }
        val windowsMain by getting {
            this.dependsOn(main)
        }
    }
}
