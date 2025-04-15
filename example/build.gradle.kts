import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.10"
    application
    id("com.github.shwaka.dokkacov") version "0.5-SNAPSHOT"
    id("org.jetbrains.dokka") version "1.9.10"
}

group = "me.shun"
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

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("com.github.shwaka.dokkacov.example.MainKt")
}

tasks.dokkaHtml.configure {
    dokkaSourceSets {
        configureEach {
            includes.from("packages.md")
        }
    }
}

configure<com.github.shwaka.dokkacov.DokkaCoveragePluginExtension> {
    dokkaHtmlDirectory.set("build/dokka/html")
}
