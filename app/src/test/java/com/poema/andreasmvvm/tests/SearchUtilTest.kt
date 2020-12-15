package com.poema.andreasmvvm.tests


import com.google.common.truth.Truth.assertThat
import org.junit.Test

class SearchUtilTest{
    @Test
    fun emptySearchReturnsfalse(){
    val result = SearchUtil.validateSearchInput("")
    assertThat(result).isFalse()
    }
}