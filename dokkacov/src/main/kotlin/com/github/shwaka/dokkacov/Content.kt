package com.github.shwaka.dokkacov

interface Content {
    val hasDoc: Boolean
}

data class ContentCount(val documented: Int, val total: Int) {
    override fun toString(): String {
        return "$documented/$total"
    }
}

fun <C : Content> Iterable<C>.countContent(): ContentCount {
    val documented = this.filter { it.hasDoc }.size
    val total = this.count()
    return ContentCount(documented, total)
}
