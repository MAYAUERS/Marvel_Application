package com.example.marvelapplication.features.character.model

import com.example.marvelapplication.extensions.convertToHttps


data class Thumbnail(
    val path: String,
    val extension: String
) {
    fun getUrl() = "$path/landscape_incredible.$extension".convertToHttps()
}
