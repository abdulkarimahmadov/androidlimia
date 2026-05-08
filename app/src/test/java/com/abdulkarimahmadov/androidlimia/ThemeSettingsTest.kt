package com.abdulkarimahmadov.androidlimia

import com.abdulkarimahmadov.androidlimia.domain.LumiaAccentColor
import com.abdulkarimahmadov.androidlimia.domain.ThemeSettings
import org.junit.Assert.assertEquals
import org.junit.Test

class ThemeSettingsTest {
    @Test
    fun defaultAccent_isCyan() {
        assertEquals(LumiaAccentColor.CYAN, ThemeSettings().accent)
    }
}
