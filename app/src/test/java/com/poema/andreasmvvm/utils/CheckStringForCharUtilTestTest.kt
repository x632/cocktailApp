package com.poema.andreasmvvm.utils

import com.google.common.truth.Truth.assertThat
import com.poema.andreasmvvm.activities.MainActivity
import org.junit.Test

class CheckStringForCharUtilTestTest{




    @Test
    fun `all strings contains sequence of chars returns true`(){
        val result = CheckStringForCharUtilTest.checkStringForChar(
            "a"
        )
        assertThat(result).isTrue()
    }

    @Test
    fun `check the alphabetic order of drinks`(){
        val result = MainActivity().checkAlphabeticOrder()
        assertThat(result).isTrue()
    }
}