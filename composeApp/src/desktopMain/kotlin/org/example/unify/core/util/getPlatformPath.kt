package org.example.unify.core.util

import kotlin.text.startsWith

fun getPlatformPath(): String {
    val userHome = System.getProperty("user.home")
    val appName = "erp"
    return when {
        System.getProperty("os.name").startsWith("Win") -> {
            "$userHome\\AppData\\Local\\$appName\\"
        }

        System.getProperty("os.name").startsWith("Mac") -> {
            "$userHome/Library/Application Support/$appName"
        }

        else -> { // Linux and others
            "$userHome/.local/share/$appName"
        }
    }
}