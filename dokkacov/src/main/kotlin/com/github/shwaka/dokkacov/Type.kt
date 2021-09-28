package com.github.shwaka.dokkacov

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.nio.file.Path

internal class Type(path: Path) {
    private val indexHtml: Path = path.resolve("index.html")

    fun parseIndexHtml() {
        val doc: Document = this.indexHtml.parse()
        val rows: Elements = doc.select("div.table-row")
        val typeContentList = rows.map { row: Element -> this.parseRow(row) }.flatten()
        for (typeContent in typeContentList) {
            println("${typeContent.name}, ${typeContent.hasDoc}")
        }
    }

    private fun parseRow(row: Element): List<TypeContent> {
        val anchor = row.select("div.main-subrow span.inline-flex a").getTheElement()
        val name = anchor.text()
        return row.select("div.divergent-group").toList().map { div -> this.parseContent(name, div) }
    }

    private fun parseContent(name: String, div: Element): TypeContent {
        val hasDoc = div.select("div.brief").containsOneElement()
        return TypeContent(name, hasDoc)
    }

    private data class TypeContent(val name: String, val hasDoc: Boolean)
}
