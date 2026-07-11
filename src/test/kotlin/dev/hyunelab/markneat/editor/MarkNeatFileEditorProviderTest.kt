package dev.hyunelab.markneat.editor

import com.intellij.openapi.fileTypes.PlainTextLanguage
import com.intellij.testFramework.LightVirtualFile
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class MarkNeatFileEditorProviderTest : BasePlatformTestCase() {
    fun testAcceptsMarkdownFilesOnly() {
        val provider = MarkNeatFileEditorProvider()

        assertTrue(provider.accept(project, LightVirtualFile("README.md", PlainTextLanguage.INSTANCE, "# MarkNeat")))
        assertTrue(provider.accept(project, LightVirtualFile("guide.markdown", PlainTextLanguage.INSTANCE, "# Guide")))
        assertFalse(provider.accept(project, LightVirtualFile("notes.txt", PlainTextLanguage.INSTANCE, "notes")))
    }
}
