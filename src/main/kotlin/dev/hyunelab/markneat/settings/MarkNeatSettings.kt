package dev.hyunelab.markneat.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

enum class MarkNeatTheme(
    val displayName: String,
    val wireValue: String,
) {
    LIGHT("Light", "light"),
    DARK("Dark", "dark"),
    ;

    override fun toString(): String = displayName
}

@State(
    name = "dev.hyunelab.markneat.settings.MarkNeatSettings",
    storages = [Storage("markneat.xml")],
)
class MarkNeatSettings : PersistentStateComponent<MarkNeatSettings.SettingsState> {
    data class SettingsState(
        var theme: MarkNeatTheme = MarkNeatTheme.LIGHT,
    )

    private var settingsState = SettingsState()

    val theme: MarkNeatTheme
        get() = settingsState.theme

    fun updateTheme(theme: MarkNeatTheme): Boolean {
        if (settingsState.theme == theme) {
            return false
        }
        settingsState.theme = theme
        return true
    }

    override fun getState(): SettingsState = settingsState

    override fun loadState(state: SettingsState) {
        settingsState = state
    }

    companion object {
        fun getInstance(): MarkNeatSettings =
            ApplicationManager.getApplication().getService(MarkNeatSettings::class.java)
    }
}
