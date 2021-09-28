package com.github.shwaka.dokkacov

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import java.nio.file.Path
import java.nio.file.Paths

class DokkaCoveragePlugin : Plugin<Project> {
    private val dokkaHtmlDirectory: Path by lazy {
        Paths.get(this.extension.dokkaHtmlDirectory.get())
    }
    private lateinit var extension: DokkaCoveragePluginExtension

    override fun apply(project: Project) {
        this.extension = project.extensions.create<DokkaCoveragePluginExtension>("dokkaCoverage")
        project.tasks.register("hello") {
            println("Hello from DokkaCoveragePlugin!")
        }

        project.tasks.register("dokkacov") {
            val root = Root(dokkaHtmlDirectory, project.name)
            root.parseIndexHtml()
        }
    }
}
