package com.github.shwaka.dokkacov

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import java.nio.file.Path
import java.nio.file.Paths

class DokkaCoveragePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension: DokkaCoveragePluginExtension = project.extensions.create("dokkaCoverage")
        val dokkaHtmlDirectory: Path = Paths.get(extension.dokkaHtmlDirectory.get())

        project.tasks.register("hello") {
            println("Hello from DokkaCoveragePlugin!")
        }

        project.tasks.register("dokkacov") {
            val root = Root(dokkaHtmlDirectory, project.name)
            root.parseIndexHtml()
        }
    }
}
