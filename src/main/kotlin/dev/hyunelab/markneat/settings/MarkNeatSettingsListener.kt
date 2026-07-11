package dev.hyunelab.markneat.settings

import com.intellij.util.messages.Topic

fun interface MarkNeatSettingsListener {
    fun themeChanged()

    companion object {
        val TOPIC: Topic<MarkNeatSettingsListener> = Topic.create(
            "MarkNeat settings changed",
            MarkNeatSettingsListener::class.java,
        )
    }
}
