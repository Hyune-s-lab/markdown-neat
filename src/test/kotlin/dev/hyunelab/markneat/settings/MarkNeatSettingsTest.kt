package dev.hyunelab.markneat.settings

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MarkNeatSettingsTest {
    @Test
    fun `uses light by default and reports actual theme changes`() {
        val settings = MarkNeatSettings()

        assertEquals(MarkNeatTheme.LIGHT, settings.theme)
        assertTrue(settings.updateTheme(MarkNeatTheme.DARK))
        assertEquals(MarkNeatTheme.DARK, settings.theme)
        assertFalse(settings.updateTheme(MarkNeatTheme.DARK))
    }

    @Test
    fun `loads the persisted theme`() {
        val settings = MarkNeatSettings()

        settings.loadState(MarkNeatSettings.SettingsState(theme = MarkNeatTheme.DARK))

        assertEquals(MarkNeatTheme.DARK, settings.theme)
    }
}
