package com.poema.andreasmvvm.utils


import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.util.*
import android.util.Base64.encodeToString

class CheckStringForCharUtilTestTest{


 /*   @Test
    fun `empty drinkname return false`(){
        val result = RegistrationUtilTest.checkStringForChar(
        ""
        )
        assertThat(result).isFalse()
    }*/

    @Test
    fun `all strings contains sequence of chars returns true`(){
        val result = CheckStringForCharUtilTest.checkStringForChar(
            "a"
        )
        assertThat(result).isTrue()
    }


}