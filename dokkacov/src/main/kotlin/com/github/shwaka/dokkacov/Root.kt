package com.github.shwaka.dokkacov

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.nio.file.Files
import java.nio.file.Path
import kotlin.streams.toList

internal class Root(path: Path, projectName: String) {
    private val indexHtml: Path = path.resolve("index.html")
    private val pkgList: List<Package> = run {
        val projectDirectory: Path = path.resolve(projectName)
        val pkgPathList: List<Path> = Files.list(projectDirectory).toList()
        pkgPathList.filter { it.toFile().isDirectory }
            .map { Package(it) }
    }

    fun parseIndexHtml() {
        val doc: Document = this.indexHtml.parse()
        val rows: Elements = doc.select("div.table-row")
        for (row: Element in rows) {
            val rootRow = this.parseRow(row)
            println("${rootRow.pkgName}, ${rootRow.hasDoc}")
        }
    }

    private fun parseRow(row: Element): RootRow {
        val anchor = row.select("div.main-subrow a").getTheElement()
        val pkgName = anchor.text()
        val hasDoc = row.select("span.brief-comment").containsOneElement()
        return RootRow(pkgName, hasDoc)
    }

    fun checkPackages() {
        for (pkg in this.pkgList) {
            pkg.parseIndexHtml()
        }
    }

    private data class RootRow(val pkgName: String, val hasDoc: Boolean)
}
