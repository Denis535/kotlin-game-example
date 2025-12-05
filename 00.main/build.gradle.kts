plugins {
    this.id("org.jetbrains.kotlin.multiplatform") version "2.3.0-RC2"
}

kotlin {
    this.mingwX64 {
        this.binaries {
            this.executable {
                this.baseName = rootProject.name
                this.entryPoint = "com.denis535.kotlin_game_example.Main"
            }
        }
    }
    this.sourceSets {
        val main by this.creating {
            this.kotlin.srcDir("sources")
            this.resources.srcDir("resources")
            this.dependencies {
                this.implementation(this.project(":ui"))
                this.implementation(this.project(":app"))
                this.implementation(this.project(":game"))
                this.implementation(this.project(":common"))
            }
        }
        val mingwX64Main by getting {
            this.dependsOn(main)
        }
    }
}

tasks.register<Exec>("run") {
    this.dependsOn("linkDebugExecutableMingwX64")
    this.commandLine(layout.buildDirectory.file("bin/mingwX64/debugExecutable/${rootProject.name}.exe").get())
}
