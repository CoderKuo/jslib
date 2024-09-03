import groovy.util.Node
import groovy.util.NodeList

plugins {
    kotlin("jvm") version "2.0.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("maven-publish")
}

group = "cn.souts"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    shadow(fileTree("libs"))
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

                pom.withXml {
                    asNode().apply {
                        // 找到 dependencies 节点
                        val dependenciesNode = (get("dependencies") as NodeList).firstOrNull() as Node
                        dependenciesNode.children().removeIf {
                            val dependencyNode = it as Node
                            (dependencyNode.get("groupId") as NodeList).text() == "org.jetbrains.kotlin" &&
                                    (dependencyNode.get("artifactId") as NodeList).text() == "kotlin-stdlib"
                        }
                    }
                }
            }
        }
    }
}