package lk.printxpress.utils

import java.security.MessageDigest

object SecurityUtils {
    /**
     * Hashes password using SHA-256 as required by Task D.9
     * (Do not store plain-text passwords in database)
     */
    fun hashPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray(Charsets.UTF_8))
        return bytes.joinToString("") { "%02x".format(it) }
    }

    fun verifyPassword(plainPassword: String, storedHash: String): Boolean {
        return hashPassword(plainPassword) == storedHash
    }
}
