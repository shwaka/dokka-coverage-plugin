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
    private val pkgContentList: List<PackageContent> by lazy {
        val doc: Document = this.indexHtml.parse()
        val rows: Elements = doc.select("div.table-row")
        rows.map { row: Element -> this.parseRow(row) }.flatten()
    }

    fun showSummary(indent: Int) {
        run {
            val spaces = " ".repeat(indent)
            println(spaces + "${this.name}: ${this.pkgContentList.countContent()}")
        }
        val additionalIndent = 2
        val spaces = " ".repeat(indent + additionalIndent)
        for (pkgContent in this.pkgContentList) {
            println(spaces + "${this.name}.${pkgContent.name}, ${pkgContent.hasDoc}")
        }
        for (type in this.typeList) {
            type.showSummary(indent + additionalIndent)
        }
    }

    fun getCount(): ContentCount {
        return this.pkgContentList.countContent() + this.typeList.map { it.getCount() }.sum()
    }

    private fun parseRow(row: Element): List<PackageContent> {
        val anchor = row.select("div.main-subrow span.inline-flex a").getTheElement() {
            "Package.parseRow:anchor in ${this.name} for the row:\n" + row.toString()
        }
        val name = anchor.text()
        // Multiple "div.content"s can be contained if expect/actual is used in multiplatform project.
        // val contentDiv = row.select("div.content").getTheElement() {
        //     "Package.parseRow:contentDiv in ${this.name} for the row:\n" + row.toString()
        // }
        val contentDivList = row.select("div.content").toList()
        // return row.select("div.content").toList().map { div -> this.parseContent(name, div) }
        return contentDivList.flatMap { this.parseContentDiv(name, it) }
    }

    // private fun parseContent(name: String, div: Element): PackageContent {
    //     val hasDoc = div.select("div.brief").containsOneElement()
    //     return PackageContent(name, hasDoc)
    // }

    private fun parseContentDiv(name: String, div: Element): List<PackageContent> {
        val symbolCount = div.select("div.symbol").size
        val briefCount = div.select("div.brief").size
        val hasDocList = List(briefCount) { true } + List(symbolCount - briefCount) { false }
        return hasDocList.map { PackageContent(name, it) }
    }

    private data class PackageContent(val name: String, override val hasDoc: Boolean) : Content
}
