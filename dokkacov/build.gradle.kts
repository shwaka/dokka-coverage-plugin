import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.21" // Version 1.3.72 is suggested by the plugin kotlin-dsl
    kotlin("plugin.serialization") version "1.5.21"
    `kotlin-dsl`
    `java-gradle-plugin`
    `maven-publish`
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1" // 10.2.0 didn't work (since kotlin version is old?)
}

group = "com.github.shwaka.dokkacov"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test-junit"))
    implementation("org.jsoup:jsoup:1.14.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.0")
    // implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0")
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

// Aliases for ktlint commands
tasks.register("kc") { dependsOn("ktlintCheck") }
tasks.register("kf") { dependsOn("ktlintFormat") }

tasks.withType<Wrapper> {
    gradleVersion = "7.2"
}
