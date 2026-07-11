package dev.hyunelab.markneat.settings

import com.intellij.openapi.ui.ComboBox
import java.awt.Component
import java.awt.Container
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MarkNeatSettingsConfigurableTest {
    @Test
    fun testAppliesThemeAndNotifiesOpenViewers() {
        val settings = MarkNeatSettings()
        var notifications = 0

        val configurable = MarkNeatSettingsConfigurable(settings) { notifications += 1 }
        val component = configurable.createComponent()
        val themeField = findComboBox(component)
        themeField.selectedItem = MarkNeatTheme.DARK

        assertTrue(configurable.isModified)
        configurable.apply()
        assertEquals(MarkNeatTheme.DARK, settings.theme)
        assertEquals(1, notifications)
        assertFalse(configurable.isModified)

        configurable.disposeUIResources()
    }

    @Suppress("UNCHECKED_CAST")
    private fun findComboBox(component: Component): ComboBox<MarkNeatTheme> {
        if (component is ComboBox<*>) {
            return component as ComboBox<MarkNeatTheme>
        }
        if (component is Container) {
            for (child in component.components) {
                runCatching { return findComboBox(child) }
            }
        }
        error("Theme selector not found")
    }
}
