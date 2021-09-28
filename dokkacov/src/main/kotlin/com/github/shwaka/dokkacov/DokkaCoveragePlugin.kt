package com.github.shwaka.dokkacov

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

class DokkaCoveragePlugin : Plugin<Project> {
    private val dokkaHtmlDirectory: Path = Paths.get("build/dokka/html")

    override fun apply(project: Project) {
        project.tasks.register("hello") {
            println("Hello from DokkaCoveragePlugin!")
        }

        project.tasks.register("dokkacov") {
            showCoverage()
        }
    }

    private fun showCoverage() {
        val indexHtml: File = this.dokkaHtmlDirectory.resolve("index.html").toFile()
        val doc: Document = Jsoup.parse(indexHtml, "UTF-8")
        val rows: Elements = doc.select("div.table-row")
        for (row: Element in rows) {
            for (a: Element in row.select("div.main-subrow a")) {
                println(a.text())
            }
            for (span: Element in row.select("div span.brief-comment")) {
                println("  " + span.text())
            }
        }
    }
}
