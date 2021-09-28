package com.github.shwaka.dokkacov

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.nio.file.Path

internal fun Path.parse(): Document {
    return Jsoup.parse(this.toFile(), "UTF-8")
}
