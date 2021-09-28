import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.32"
    application
    id("com.github.shwaka.dokkacov") version "1.0-SNAPSHOT"
    id("org.jetbrains.dokka") version "1.4.32"
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
    mainClassName = "com.github.shwaka.dokkacov.example.MainKt"
}

tasks.dokkaHtml.configure {
    dokkaSourceSets {
        configureEach {
            includes.from("packages.md")
        }
    }
}
