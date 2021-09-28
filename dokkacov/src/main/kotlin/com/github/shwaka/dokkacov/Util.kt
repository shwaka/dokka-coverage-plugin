package com.github.shwaka.dokkacov

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.nio.file.Path

internal fun Path.parse(): Document {
    return Jsoup.parse(this.toFile(), "UTF-8")
}

fun <T> Iterable<T>.getTheElement(): T {
    val count = this.count()
    if (count != 1) {
        throw Exception("$this must contain exactly one element, but contains ${this.count()}")
    }
    return this.first()
}
