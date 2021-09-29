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

        project.task("dokkacovReport") {
            description = "Print coverage report to console"
            doLast {
                val root = Root(dokkaHtmlDirectory, project.name)
                println("total: ${root.getCount()}")
                root.showSummary()
            }
        }

        project.task("dokkacovWriteJson") {
            description = "Compute coverage and write it to a json file"
            doLast {
                val root = Root(dokkaHtmlDirectory, project.name)
                val json: String = root.toJson()
                dokkaHtmlDirectory.resolve("coverage.json").toFile().writeText(json)
            }
        }
    }
}
