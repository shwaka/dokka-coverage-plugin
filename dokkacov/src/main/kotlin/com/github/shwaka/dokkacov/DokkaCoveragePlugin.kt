package com.github.shwaka.dokkacov

// import kotlinx.serialization.ImplicitReflectionSerializer
// import kotlinx.serialization.UnstableDefault
// import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import java.nio.file.Path
import java.nio.file.Paths

// @UnstableDefault
// @ImplicitReflectionSerializer
class DokkaCoveragePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension: DokkaCoveragePluginExtension = project.extensions.create("dokkaCoverage")
        val dokkaHtmlDirectory: Path = Paths.get(extension.dokkaHtmlDirectory.get())

        project.task("dokkacov") {
            doLast {
                val root = Root(dokkaHtmlDirectory, project.name)
                println("total: ${root.getCount()}")
                root.showSummary()
                println(Json.encodeToString(root.getCount()))
                // println(Json.toJson(root.getCount()))
            }
        }
    }
}
