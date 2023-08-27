package com.stc.ahmedehabtask.utilities

object Constants {
    const val ACCEPT = "application/json"
    const val BASE_URL = "http://data.fixer.io/api/"
    const val accessKey = "09f2072c4b3a2cd29159a7be6fef39d2"


    private fun internetIsConnected(): Boolean {
        return try {
            val command = "ping -c 1 google.com"
            Runtime.getRuntime().exec(command).waitFor() == 0
        } catch (e: Exception) {
            false
        }
    }
}