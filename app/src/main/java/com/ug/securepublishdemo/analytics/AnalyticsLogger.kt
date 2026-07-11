package com.ug.securepublishdemo.analytics
import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
class AnalyticsLogger(context: Context) {
    private val analytics: FirebaseAnalytics = FirebaseAnalytics.
    getInstance(context)
    fun logLoginSuccess(method: String) {
        val params = Bundle().apply {
            putString("method", method) // "email", "google", etc.
        }
        analytics.logEvent("login_success", params)
    }
    fun logTokenStored() {
        analytics.logEvent("token_stored", null)
    }
    fun logButtonClick(buttonName: String) {
        val params = Bundle().apply {
            putString("button_name", buttonName)
        }
        analytics.logEvent("button_click", params)
    }
}