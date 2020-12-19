package com.poema.andreasmvvm.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CheckListForCharUtilTestTest{


 /*   @Test
    fun `empty drinkname return false`(){
        val result = RegistrationUtilTest.checkStringForChar(
        ""
        )
        assertThat(result).isFalse()
    }*/

    @Test
    fun `all strings contains sequence of chars returns true`(){
        val result = CheckListForCharUtilTest.checkStringForChar(
            "a"
        )
        assertThat(result).isTrue()
    }

}