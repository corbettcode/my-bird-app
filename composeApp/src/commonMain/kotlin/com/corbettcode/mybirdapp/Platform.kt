package com.corbettcode.mybirdapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform