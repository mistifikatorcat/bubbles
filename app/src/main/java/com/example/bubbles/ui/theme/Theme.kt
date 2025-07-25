package com.example.bubbles.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/* ───────── Color swatches ───────── */

private object Palette {
    /* Light */
    val lightPrimary   = Color(0xFFD9D7D7)
    val lightSecondary = Color(0xFF525F70)
    val lightSurface   = Color(0xFFFFFFFF)
    val lightOnPrimary = Color(0xFF000000)

    /* Soft Light */
    val softPrimary    = Color(0xFFD4A8EC)
    val softSecondary  = Color(0xFF525355)
    val softSurface    = Color(0xFFFFE6C7) //dusty
    //val softSurface = Color(0xFFFFDCBD) //peachy
    val softOnPrimary = Color(0xFF525355)

    /* Night */
    val nightPrimary   = Color(0xFF80CFFF)
    val nightSecondary = Color(0xFFB3CADC)
    val nightSurface   = Color(0xFF0B1320)
    val nightOnPrimary = Color(0xFFB3CADC)

    /* Amoled */
    val amoledPrimary  = Color(0xFF28FF33)
    val amoledSecondary= Color(0xFFCEFC07)
    val amoledSurface  = Color(0xFF000000)
    val amoledOnPrimary = Color(0xFFCEFC07)
}

/* ───────── Public theme enum ───────── */

enum class AppTheme {
   System, Light, SoftLight, Night, Amoled;

    /** Pretty name for UI lists */
    fun displayName() = when (this) {
        System -> "System default"
        Light     -> "Light"
        SoftLight -> "Soft Light"
        Night     -> "Night"
        Amoled    -> "AMOLED"
    }
}

private fun AppTheme.toScheme(): ColorScheme = when (this) {
    AppTheme.Light -> lightColorScheme(
        primary   = Palette.lightPrimary,
        secondary = Palette.lightSecondary,
        surface   = Palette.lightSurface,
        onPrimary = Palette.lightOnPrimary
    )

    AppTheme.SoftLight -> lightColorScheme(
        primary   = Palette.softPrimary,
        secondary = Palette.softSecondary,
        surface   = Palette.softSurface,
        background= Palette.softSurface,
        onPrimary = Palette.softOnPrimary,
        onSurface = Color(0xFF1C1B18),
    )

    AppTheme.Night -> darkColorScheme(
        primary   = Palette.nightPrimary,
        secondary = Palette.nightSecondary,
        surface   = Palette.nightSurface,
        onPrimary = Palette.nightOnPrimary
    )

    AppTheme.Amoled -> darkColorScheme(
        primary    = Palette.amoledPrimary,
        secondary  = Palette.amoledSecondary,
        surface    = Palette.amoledSurface,
        background = Palette.amoledSurface,
        onSurface  = Color.White,
        onPrimary = Palette.amoledOnPrimary
    )

    // System is resolved at runtime; caller handles it.
    AppTheme.System -> error("Call AppTheme.System through resolveSystemTheme()")
}

/* ───────── Theme wrapper ───────── */

@Composable
private fun resolveSystemTheme(): ColorScheme =
    if (isSystemInDarkTheme()) AppTheme.Night.toScheme()
    else AppTheme.Light.toScheme()

@Composable
fun BubbleTheme(
    appTheme: AppTheme,
    content: @Composable () -> Unit
) {
    val colors = when (appTheme){
        AppTheme.System -> resolveSystemTheme()
        else -> appTheme.toScheme()
    }
    MaterialTheme(
        colorScheme = colors,
        typography  = Typography,
        content     = content
    )
}
