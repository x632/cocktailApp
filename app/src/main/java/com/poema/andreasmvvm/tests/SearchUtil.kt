package com.poema.andreasmvvm.tests

object SearchUtil {

    /*
    * tillsammans med searchutilTest - skalbart testsystem (hajat principen..) - i MainActivity används motsvarande funktion i searchfunktionen
    * kollar att searchinput inte är tomt.
    * */

    fun validateSearchInput(searchInput : String): Boolean
    {
        if (searchInput.isEmpty()){
            return false
        }
        return true
    }
}