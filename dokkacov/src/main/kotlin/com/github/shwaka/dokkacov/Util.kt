package com.github.shwaka.dokkacov

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.nio.file.Path

internal fun Path.parse(): Document {
    return Jsoup.parse(this.toFile(), "UTF-8")
}

internal fun <T> Iterable<T>.getTheElement(): T {
    val count = this.count()
    if (count != 1) {
        throw Exception("$this should contain exactly one element, but contains $count")
    }
    return this.first()
}

internal fun <T> Iterable<T>.containsOneElement(): Boolean {
    val count = this.count()
    if (count > 1) {
        throw Exception("$this should contain at most one element, but contains $count")
    }
    return (count == 1)
}
