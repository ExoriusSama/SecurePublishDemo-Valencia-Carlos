package com.ug.securepublishdemo.network
import java.net.URL
import javax.net.ssl.HttpsURLConnection

object NetworkClient {
    fun httpsGet(url: String): Int {
        val connection = URL(url).openConnection() as HttpsURLConnection
        connection.requestMethod = "GET"
        connection.connectTimeout = 8000
        connection.readTimeout = 8000
        connection.connect()
        return connection.responseCode
    }
}