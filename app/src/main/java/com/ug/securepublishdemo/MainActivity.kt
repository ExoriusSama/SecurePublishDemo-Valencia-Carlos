package com.ug.securepublishdemo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.TextView
import com.ug.securepublishdemo.analytics.AnalyticsLogger
import com.ug.securepublishdemo.security.SecureStorage
import com.ug.securepublishdemo.network.NetworkClient
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private lateinit var storage: SecureStorage
    private lateinit var analytics: AnalyticsLogger
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// UI mínima sin XML (para que sea rápido y didáctico)
        setContentView(R.layout.activity_main)
        storage = SecureStorage(this)
        analytics = AnalyticsLogger(this)
        val txtStatus = findViewById<TextView>(R.id.txtStatus)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnReadToken = findViewById<Button>(R.id.btnReadToken)
        val btnHttpsTest = findViewById<Button>(R.id.btnHttpsTest)
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        btnLogin.setOnClickListener {
// 1) Simulación: "login" exitoso
            val fakeToken = "TOKEN_DEMO_12345"
// 2) Guardar token de forma CIFRADA
            storage.saveToken(fakeToken)
// 3) Registrar eventos de Analytics
            analytics.logLoginSuccess("email")
            analytics.logTokenStored()
            analytics.logButtonClick("btnLogin")
            txtStatus.text = "Login OK. Token guardado cifrado."
        }
        btnReadToken.setOnClickListener {
            analytics.logButtonClick("btnReadToken")
            val token = storage.getToken()
            txtStatus.text = "Token leído: ${token ?: "NO HAY TOKEN"}"
        }
        btnLogout.setOnClickListener {
            analytics.logButtonClick("btnLogout")
            storage.clearToken()
            txtStatus.text = "Sesión cerrada. Token eliminado."
        }
        btnHttpsTest.setOnClickListener {
            analytics.logButtonClick("btnHttpsTest")
            txtStatus.text = "Probando HTTPS..."
            // Llamada en hilo para no bloquear la UI
            thread {
                try {
// Usamos un endpoint HTTPS público (solo para demo)
                    val code = NetworkClient.httpsGet("https://www.google.com"
                    )
                    runOnUiThread {
                        txtStatus.text = "HTTPS OK. ResponseCode: $code"
                    }
                } catch (ex: Exception) {
                    runOnUiThread {
                        txtStatus.text = "Error HTTPS: ${ex.message}"
                    }
                }
            }
        }
    }
}
