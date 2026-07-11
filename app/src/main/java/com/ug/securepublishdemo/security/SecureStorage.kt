package com.ug.securepublishdemo.security
import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
/**
 * SecureStorage:
 * - Guarda datos sensibles (ej: token) cifrados en el dispositivo.
 * - Usa una MasterKey almacenada en Android Keystore.
 */
class SecureStorage(context: Context) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    private val prefs = EncryptedSharedPreferences.create(
        context,
        "secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    fun saveToken(token: String) {
// Guardar token de forma cifrada
        prefs.edit().putString("session_token", token).apply()
    }
    fun getToken(): String? {
// Obtener token (se descifra automáticamente)
        return prefs.getString("session_token", null)
    }
    fun clearToken() {
// Eliminar token
        prefs.edit().remove("session_token").apply()
    }
}
