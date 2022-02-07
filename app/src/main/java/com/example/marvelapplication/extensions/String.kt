package com.example.marvelapplication.features.details.character.network

import java.nio.charset.StandardCharsets
import java.security.MessageDigest

fun String.md5(): String = MessageDigest.getInstance("MD5").digest(
    this.toByteArray(
        StandardCharsets.UTF_8
    )
).toHex()

fun String.convertToHttps(): String {
    return this.replace("http", "https")
}
