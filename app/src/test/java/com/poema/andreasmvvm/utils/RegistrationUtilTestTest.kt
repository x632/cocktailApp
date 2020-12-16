package com.poema.andreasmvvm.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RegistrationUtilTestTest{


 /*   @Test
    fun `empty drinkname return false`(){
        val result = RegistrationUtilTest.checkStringForChar(
        ""
        )
        assertThat(result).isFalse()
    }*/

    @Test
    fun `all strings contains sequence of chars returns true`(){
        val result = RegistrationUtilTest.checkStringForChar(
            "a"
        )
        assertThat(result).isTrue()
    }

}