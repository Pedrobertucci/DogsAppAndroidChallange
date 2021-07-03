package com.sword.health.utils

import java.io.InputStreamReader

class FilePath(private val path: String) {

    fun getContent() : String {
        val reader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(path))
        val content = reader.readText()
        reader.close()
        return content
    }
}