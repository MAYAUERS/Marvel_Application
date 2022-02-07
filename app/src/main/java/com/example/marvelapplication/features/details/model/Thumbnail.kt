package com.rafaelfelipeac.marvelapp.features.details.domain.model

import com.example.marvelapplication.features.details.character.network.convertToHttps


data class Thumbnail(
    val path: String,
    val extension: String
) {
    fun getUrl(imageType: ImageType) = when (imageType) {
        ImageType.STANDARD -> "$path/standard_amazing.$extension".convertToHttps()
        ImageType.LANDSCAPE -> "$path/landscape_incredible.$extension".convertToHttps()
        ImageType.PORTRAIT -> "$path/portrait_incredible.$extension".convertToHttps()
    }

    enum class ImageType {
        STANDARD, LANDSCAPE, PORTRAIT
    }
}
