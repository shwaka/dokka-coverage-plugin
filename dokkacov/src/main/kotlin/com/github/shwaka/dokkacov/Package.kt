package com.github.shwaka.dokkacov

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.nio.file.Path

internal class Package(path: Path) {
    private val indexHtml: Path = path.resolve("index.html")

    fun parseIndexHtml() {
        val doc: Document = this.indexHtml.parse()
        val rows: Elements = doc.select("div.table-row")
        for (row: Element in rows) {
            val pkgRow = this.parseRow(row)
            println("${pkgRow.name}, ${pkgRow.hasDoc}")
        }
    }

    private fun parseRow(row: Element): PackageRow {
        val anchor = row.select("div.main-subrow span.inline-flex a")
        val name = anchor.text()
        val hasDoc = row.select("div.brief").containsOneElement()
        return PackageRow(name, hasDoc)
    }

    private data class PackageRow(val name: String, val hasDoc: Boolean)
}
