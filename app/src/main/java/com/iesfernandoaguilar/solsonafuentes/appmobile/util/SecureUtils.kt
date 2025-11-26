package com.iesfernandoaguilar.solsonafuentes.appmobile.util

import java.security.MessageDigest
import java.nio.charset.StandardCharsets
import java.security.SecureRandom

object SecureUtils {

    private fun bytesToHex(bytes: ByteArray): String {
        val sb = StringBuilder()
        for (b in bytes) {
            sb.append(Integer.toString((b.toInt() and 0xff) + 0x100, 16).substring(1))
        }
        return sb.toString()
    }

    fun generarHashSHA512(passwordToHash: String, salt: ByteArray): String {
        val md = MessageDigest.getInstance("SHA-512")
        md.update(salt)
        val byteOfTextToHash = passwordToHash.toByteArray(StandardCharsets.UTF_8)
        val hashedByteArray = md.digest(byteOfTextToHash)
        return bytesToHex(hashedByteArray)
    }

    fun getSalt(): ByteArray {
        val random = SecureRandom()
        val salt = ByteArray(16)
        random.nextBytes(salt)
        return salt
    }
}