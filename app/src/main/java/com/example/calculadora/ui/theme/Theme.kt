package com.example.calculadora.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun CalculadoraTheme(content: @Composable () -> Unit) {
    val colors = lightColorScheme(
        primary = primary,
        onPrimary = onPrimary,
        background = background,
        onBackground = onBackground
    )

    MaterialTheme(
        colorScheme = colors,
        typography = Typography(),
        content = content
    )
}