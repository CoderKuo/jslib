
plugins {
    kotlin("jvm") version "2.0.0"
    id("maven-publish")
}

group = "cn.souts"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(fileTree("libs"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(8)
}

tasks.register("printVersion") {
    doLast {
        print(version)
    }
}

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("release") {
                val component = components.find {
                    it.name == "java" || it.name == "release" || it.name == "kotlin"
                }
                from(component)
            }
        }
    }
}