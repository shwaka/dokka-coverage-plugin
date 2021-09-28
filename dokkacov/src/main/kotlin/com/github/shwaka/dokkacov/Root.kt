package com.github.shwaka.dokkacov

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.nio.file.Path

internal class Root(private val path: Path, private val projectName: String) {
    private val indexHtml: Path = this.path.resolve("index.html")

    fun parseIndexHtml() {
        val doc: Document = indexHtml.parse()
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
