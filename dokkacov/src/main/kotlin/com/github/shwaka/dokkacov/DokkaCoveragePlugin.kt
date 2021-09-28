package com.github.shwaka.dokkacov

import org.gradle.api.Plugin
import org.gradle.api.Project

class DokkaCoveragePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("hello") {
            println("Hello from DokkaCoveragePlugin!")
        }
    }
}
