import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.72"
    `kotlin-dsl`
    `java-gradle-plugin`
    `maven-publish`
}

group = "com.github.shwaka.dokkacov"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test-junit"))
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

gradlePlugin {
    plugins {
        create("examplePlugin") {
            id = "com.github.shwaka.dokkacov"
            implementationClass = "com.github.shwaka.dokkacov.DokkaCoveragePlugin"
        }
    }
}
