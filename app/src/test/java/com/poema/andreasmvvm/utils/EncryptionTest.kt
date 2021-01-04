package com.poema.andreasmvvm.utils

import android.util.Base64.*
import com.google.common.truth.Truth
import org.junit.Test
import java.nio.charset.Charset
import java.util.*

class EncryptionTest {
    @Test
    fun `all strings contains sequence of chars returns true`(){
        val result = CheckStringForCharUtilTest.checkStringForChar(
            "a"
        )
        Truth.assertThat(result).isTrue()
    }

    @Test
    fun encryptTest(){

        assert(encryptCheck("emil", "hemligt lösenord"))
    }


    fun encryptCheck(StringToEncode: String, password: String): Boolean {

        var map = Encryption2().encrypt(StringToEncode.toByteArray(), password.toCharArray())

// base 64 är ett filformat
        val base64Encryption2 = Base64.encodeToString(map["encrypted"], Base64.NO_WRAP)
        val base64Iv = Base64.encodeToString(map["iv"], Base64.NO_Wrap)
        val base64Salt = Base64.encodeToString(map["salt"], Base64.NO_Wrap)


        val encrypted = Base64.decode(base64Encryption2, Base64.NO_WRAP)
        val iv = Base64.decode(base64Iv, Base64.NO_WRAP)
        val salt = Base64.decode(base64Salt, Base64.NO_WRAP)

        val decrypt = Encryption2().decrypt(hashMapOf("iv" to iv, "salt" to salt, "encrypted" to encrypted)
            ,password.toCharArray())

        var decryptionValue: String? = null
        decrypt?.let {
        decryptionValue = String(it,Charsets.UTF_8)
        }

        if (decryptionValue == StringToEncode){
            return true
        }else{
            return false
        }
    }
}