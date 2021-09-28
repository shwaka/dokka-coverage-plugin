package com.github.shwaka.dokkacov

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.nio.file.Files
import java.nio.file.Path
import kotlin.streams.toList

internal class Package(path: Path) {
    private val indexHtml: Path = path.resolve("index.html")
    private val name: String = path.fileName.toString()
    private val typeList: List<Type> = run {
        val typePathList: List<Path> = Files.list(path).toList()
        typePathList.filter { it.toFile().isDirectory }
            .map { Type(it) }
    }

    fun parseIndexHtml() {
        val doc: Document = this.indexHtml.parse()
        val rows: Elements = doc.select("div.table-row")
        val pkgContentList = rows.map { row: Element -> this.parseRow(row) }.flatten()
        for (pkgContent in pkgContentList) {
            println("${this.name}.${pkgContent.name}, ${pkgContent.hasDoc}")
        }
    }

    private fun parseRow(row: Element): List<PackageContent> {
        val anchor = row.select("div.main-subrow span.inline-flex a").getTheElement()
        val name = anchor.text()
        return row.select("div.divergent-group").toList().map { div -> this.parseContent(name, div) }
    }

    private fun parseContent(name: String, div: Element): PackageContent {
        val hasDoc = div.select("div.brief").containsOneElement()
        return PackageContent(name, hasDoc)
    }

    fun checkTypes() {
        for (type in this.typeList) {
            type.parseIndexHtml()
        }
    }

    private data class PackageContent(val name: String, val hasDoc: Boolean)
}
