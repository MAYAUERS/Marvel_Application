package com.example.marvelapplication.features.details.character.network

fun ByteArray.toHex() = joinToString("") { "%02x".format(it) }
