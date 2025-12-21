package com.pawanjeswani.pawanjeswanitask.util

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

// Sealed class for handling both dynamic strings and string resources in ViewModels
sealed class UiText {
    data class DynamicString(val value: String) : UiText() // Plain string wrapper
    class StringResource(
        @param:StringRes val resId: Int,
        vararg val args: Any
    ) : UiText()

    // Resolves to string within a Composable context
    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> stringResource(resId, *args)
        }
    }

    // Resolves to string using Context (for non-Composable code)
    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(resId, *args)
        }
    }
}
