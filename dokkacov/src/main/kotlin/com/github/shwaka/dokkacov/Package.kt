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
            for (span: Element in row.select("div.brief")) {
                println("  " + span.text())
            }
        }
    }
}
