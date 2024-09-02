import org.gradle.internal.impldep.org.apache.http.client.ResponseHandler

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

