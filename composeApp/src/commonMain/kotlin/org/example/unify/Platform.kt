package org.example.unify

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform