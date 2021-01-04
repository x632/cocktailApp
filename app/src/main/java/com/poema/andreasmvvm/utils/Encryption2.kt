package com.poema.andreasmvvm.utils

import android.util.Log
import java.security.KeyStore
import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

internal class Encryption2 {

    fun encrypt(dataToEncrypt: ByteArray, password: CharArray): HashMap<String, ByteArray> {
        //förbereder en map string som key
        val map = HashMap<String, ByteArray>()

        try {
            // 1
            //Random salt for next step
            val random = SecureRandom()
            val salt = ByteArray(256)
            random.nextBytes(salt)

            // 2
            //PBKDF2 - derive the key from the password, don't use passwords directly

            //tar löenordet tillsamans med random värdet
            val pbKeySpec = PBEKeySpec(password, salt, 1324, 256)
            // fullföljer skapandet av nycklen
            val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            // genererar en secretkey
            val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
            // nyckel specification baserad på aes algoritmhm
            val keySpec = SecretKeySpec(keyBytes, "AES")

            // 3
            //Create initialization vector for AES
            val ivRandom = SecureRandom()
            val iv = ByteArray(16)
            ivRandom.nextBytes(iv)
            // skapar ivSpec från slumpade ovan
            val ivSpec = IvParameterSpec(iv)

            // 4
            //Encrypt
            //skappar en instanse som sedan initilazeras
            val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
            // gör själva encryption
            val encrypted = cipher.doFinal(dataToEncrypt)

            // 5
            // keyvalue = "salt" och värdet på den key är salt som skapades ovan
            map["salt"] = salt
            // keyvalue = "iv" och värdet på den key är iv (initialization vector) som skapades ovan
            map["iv"] = iv
            // keyvalue = encrypted och sätts till själva encrypted värdet
            map["encrypted"] = encrypted
        } catch (e: Exception) {
            Log.e("MYAPP", "encryption exception", e)
        }

        return map

    }

    fun decrypt(map: HashMap<String, ByteArray>, password: CharArray): ByteArray? {
        var decrypted: ByteArray? = null
        try {
            // 1
                // plockar ut ifrån mapen
            val salt = map["salt"]
            val iv = map["iv"]
            val encrypted = map["encrypted"]

            // 2
            //regenerate key from password
            val pbKeySpec = PBEKeySpec(password, salt, 1324, 256)
            val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
            val keySpec = SecretKeySpec(keyBytes, "AES")

            // 3
            //Decrypt
            val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
            // kan köra direkt behöver inte slumpas
            val ivSpec = IvParameterSpec(iv)
            // keyspec o ivspec är samma värde som innan för de är inte slumpade
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
            decrypted = cipher.doFinal(encrypted)
        } catch (e: Exception) {
            Log.e("MYAPP", "decryption exception", e)
        }
// retunerar en bytearray
        return decrypted
    }

    fun keystoreEncrypt(dataToEncrypt: ByteArray): HashMap<String, ByteArray> {
        val map = HashMap<String, ByteArray>()
        try {

            // 1
            //Get the key
            val keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)

            val secretKeyEntry =
                keyStore.getEntry("MyKeyAlias", null) as KeyStore.SecretKeyEntry
            val secretKey = secretKeyEntry.secretKey

            // 2
            //Encrypt data
            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
            val ivBytes = cipher.iv
            val encryptedBytes = cipher.doFinal(dataToEncrypt)

            // 3
            map["iv"] = ivBytes
            map["encrypted"] = encryptedBytes
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        return map
    }

    fun keystoreDecrypt(map: HashMap<String, ByteArray>): ByteArray? {
        var decrypted: ByteArray? = null
        try {
            // 1
            //Get the key
            val keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)

            val secretKeyEntry =
                keyStore.getEntry("MyKeyAlias", null) as KeyStore.SecretKeyEntry
            val secretKey = secretKeyEntry.secretKey

            // 2
            //Extract info from map
            val encryptedBytes = map["encrypted"]
            val ivBytes = map["iv"]

            // 3
            //Decrypt data
            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            val spec = GCMParameterSpec(128, ivBytes)
            cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)
            decrypted = cipher.doFinal(encryptedBytes)
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        return decrypted
    }
}
