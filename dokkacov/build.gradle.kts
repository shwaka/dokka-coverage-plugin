import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.10"
    `kotlin-dsl`
    `java-gradle-plugin`
    `maven-publish`
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1" // 10.2.0 didn't work (since kotlin version is old?)
}

group = "com.github.shwaka.dokkacov"
version = "0.5-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test-junit"))
    implementation("org.jsoup:jsoup:1.14.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.8")
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

tasks.withType<ProcessResources> {
    // https://discuss.gradle.org/t/gradle-7-x-and-duplicatesstrategy/41045
    // DuplicatesStrategy.INHERIT did not work
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

// Aliases for ktlint commands
tasks.register("kc") { dependsOn("ktlintCheck") }
tasks.register("kf") { dependsOn("ktlintFormat") }

publishing {
    repositories {
        maven {
            url = uri("../../maven/repository")
            name = "MyMaven"
        }
    }
}
