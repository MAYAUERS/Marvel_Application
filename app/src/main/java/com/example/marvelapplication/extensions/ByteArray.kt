package com.example.marvelapplication.extensions

fun ByteArray.toHex() = joinToString("") { "%02x".format(it) }
