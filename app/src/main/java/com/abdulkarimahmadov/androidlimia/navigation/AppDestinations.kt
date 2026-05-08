package com.abdulkarimahmadov.androidlimia.navigation

enum class AppDestination(val route: String, val title: String) {
    DIALER("dialer", "phone"),
    CONTACTS("contacts", "people"),
    RECENTS("recents", "history"),
    SETTINGS("settings", "settings")
}
