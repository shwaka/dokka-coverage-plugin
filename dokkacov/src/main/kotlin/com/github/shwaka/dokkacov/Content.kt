package com.github.shwaka.dokkacov

internal interface Content {
    val hasDoc: Boolean
}

data class ContentCount(val documented: Int, val total: Int) {
    val percent: Int? = if (total == 0) null else (documented * 100) / total

    operator fun plus(other: ContentCount): ContentCount {
        return ContentCount(this.documented + other.documented, this.total + other.total)
    }

    override fun toString(): String {
        return "$documented/$total"
    }

    companion object {
        val zero: ContentCount = ContentCount(0, 0)
    }
}

internal fun <C : Content> Iterable<C>.countContent(): ContentCount {
    val documented = this.filter { it.hasDoc }.size
    val total = this.count()
    return ContentCount(documented, total)
}

internal fun Iterable<ContentCount>.sum(): ContentCount {
    return this.fold(ContentCount.zero) { acc, c -> acc + c }
}
