package com.github.shwaka.dokkacov

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
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
    private val rootContentList: List<RootContent> by lazy {
        val doc: Document = this.indexHtml.parse()
        val rows: Elements = doc.select("div.table-row")
        rows.map { row: Element -> this.parseRow(row) }
    }

    fun showSummary() {
        for (rootContent in this.rootContentList) {
            println("${rootContent.pkgName}, ${rootContent.hasDoc}")
        }
        for (pkg in this.pkgList) {
            pkg.showSummary(0)
        }
    }

    fun getCount(): ContentCount {
        return this.rootContentList.countContent() + this.pkgList.map { it.getCount() }.sum()
    }

    fun toJson(): String {
        val mapper = jacksonObjectMapper()
        return mapper.writeValueAsString(this.getCount())
    }

    private fun parseRow(row: Element): RootContent {
        val anchor = row.select("div.main-subrow a").getTheElement()
        val pkgName = anchor.text()
        val hasDoc = row.select("span.brief-comment").containsOneElement()
        return RootContent(pkgName, hasDoc)
    }

    private data class RootContent(val pkgName: String, override val hasDoc: Boolean) : Content
}
