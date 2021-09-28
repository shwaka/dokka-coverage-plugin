package com.github.shwaka.dokkacov

import org.gradle.api.provider.Property

abstract class DokkaCoveragePluginExtension {
    abstract val dokkaHtmlDirectory: Property<String>

    init {
        dokkaHtmlDirectory.convention("build/dokka/html")
    }
}
