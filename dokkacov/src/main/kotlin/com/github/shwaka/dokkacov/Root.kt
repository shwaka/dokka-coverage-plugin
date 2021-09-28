package com.github.shwaka.dokkacov

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.nio.file.Files
import java.nio.file.Path
import kotlin.streams.toList

internal class Root(path: Path, projectName: String) {
    private val indexHtml: Path = path.resolve("index.html")
    private val packages: List<Package> = run {
        val projectDirectory: Path = path.resolve(projectName)
        val packagePaths: List<Path> = Files.list(projectDirectory).toList()
        packagePaths.filter { it.toFile().isDirectory }
            .map { Package(it) }
    }

    fun parseIndexHtml() {
        val doc: Document = this.indexHtml.parse()
        val rows: Elements = doc.select("div.table-row")
        for (row: Element in rows) {
            for (a: Element in row.select("div.main-subrow a")) {
                println(a.text())
            }
            for (span: Element in row.select("span.brief-comment")) {
                println("  " + span.text())
            }
        }
    }

    fun checkPackages() {
        for (pack in this.packages) {
            pack.parseIndexHtml()
        }
    }
}
