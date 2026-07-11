package dev.hyunelab.markneat.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.options.Configurable
import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel

class MarkNeatSettingsConfigurable internal constructor(
    private val settings: MarkNeatSettings,
    private val notifyThemeChanged: () -> Unit,
) : Configurable {
    constructor() : this(MarkNeatSettings.getInstance(), ::publishThemeChanged)

    private var themeField: ComboBox<MarkNeatTheme>? = null

    override fun getDisplayName(): String = "MarkNeat"

    override fun createComponent(): JComponent {
        themeField = ComboBox(MarkNeatTheme.entries.toTypedArray())
        return FormBuilder.createFormBuilder()
            .addLabeledComponent(JBLabel("Theme:"), requireNotNull(themeField), 1, false)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    override fun isModified(): Boolean =
        themeField?.selectedItem != settings.theme

    override fun apply() {
        val theme = themeField?.selectedItem as? MarkNeatTheme ?: return
        if (settings.updateTheme(theme)) {
            notifyThemeChanged()
        }
    }

    override fun reset() {
        themeField?.selectedItem = settings.theme
    }

    override fun disposeUIResources() {
        themeField = null
    }

    private companion object {
        fun publishThemeChanged() {
            ApplicationManager.getApplication().messageBus
                .syncPublisher(MarkNeatSettingsListener.TOPIC)
                .themeChanged()
        }
    }
}
