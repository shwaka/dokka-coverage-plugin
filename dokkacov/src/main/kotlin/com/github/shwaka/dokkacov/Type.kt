package com.github.shwaka.dokkacov

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.nio.file.Path

internal class Type(path: Path) {
    private val indexHtml: Path = path.resolve("index.html")
    // private val name: String = doc.select("h1.cover span").getTheElement().text()
    private val name: String = path.fileName.toString().capitalizeHyphen()
    private val typeContentList: List<TypeContent> by lazy {
        val doc: Document = this.indexHtml.parse()
        val rows: Elements = doc.select("div.table-row")
        rows.map { row: Element -> this.parseRow(row) }.flatten()
    }

    fun showSummary(indent: Int) {
        run {
            val spaces = " ".repeat(indent)
            println(spaces + "${this.name}: ${this.typeContentList.countContent()}")
        }
        val additionalIndent = 2
        val spaces = " ".repeat(indent + additionalIndent)
        for (typeContent in this.typeContentList) {
            println(spaces + "${this.name}.${typeContent.name}, ${typeContent.hasDoc}")
        }
    }

    fun getCount(): ContentCount {
        return this.typeContentList.countContent()
    }

    private fun parseRow(row: Element): List<TypeContent> {
        val anchor = row.select("div.main-subrow span.inline-flex a").getTheElement() {
            "Type.parseRow in ${this.name} for the row:\n" + row.toString()
        }
        val name = anchor.text()
        return row.select("div.divergent-group").toList().map { div -> this.parseContent(name, div) }
    }

    private fun parseContent(name: String, div: Element): TypeContent {
        val hasDoc = div.select("div.brief").containsOneElement()
        return TypeContent(name, hasDoc)
    }

    private data class TypeContent(val name: String, override val hasDoc: Boolean) : Content
}
