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

    /* Soft Light */
    val softPrimary    = Color(0xFFD4A8EC)
    val softSecondary  = Color(0xFF525355)
    val softSurface    = Color(0xFFFFE6C7) //dusty
    //val softSurface = Color(0xFFFFDCBD) //peachy

    /* Night */
    val nightPrimary   = Color(0xFF80CFFF)
    val nightSecondary = Color(0xFFB3CADC)
    val nightSurface   = Color(0xFF0B1320)

    /* Amoled */
    val amoledPrimary  = Color(0xFF28FF33)
    val amoledSecondary= Color(0xFFCEFC07)
    val amoledSurface  = Color(0xFF000000)
}

/* ───────── Public theme enum ───────── */

enum class AppTheme {
   System, Light, SoftLight, Night, Amoled;

    /** Material-3 ColorScheme for this theme */
    val scheme: ColorScheme
        get() = when (this) {
            Light -> lightColorScheme(
                primary   = Palette.lightPrimary,
                secondary = Palette.lightSecondary,
                surface   = Palette.lightSurface,
            )
            SoftLight -> lightColorScheme(
                primary   = Palette.softPrimary,
                secondary = Palette.softSecondary,
                surface   = Palette.softSurface,
            )
            Night -> darkColorScheme(
                primary   = Palette.nightPrimary,
                secondary = Palette.nightSecondary,
                surface   = Palette.nightSurface,
            )
            Amoled -> darkColorScheme(
                primary   = Palette.amoledPrimary,
                secondary = Palette.amoledSecondary,
                surface   = Palette.amoledSurface,
                onSurface = Color.White            // legible on #000
            )
            else      -> error("System theme is resolved at runtime")
        }

    /** Pretty name for UI lists */
    fun displayName() = when (this) {
        System -> "System default"
        Light     -> "Light"
        SoftLight -> "Soft Light"
        Night     -> "Night"
        Amoled    -> "AMOLED"
    }
}

/* ───────── Theme wrapper ───────── */

@Composable
fun BubbleTheme(
    appTheme: AppTheme,
    content: @Composable () -> Unit
) {
    val colors = when (appTheme){
        AppTheme.System -> {
            if (isSystemInDarkTheme()) AppTheme.Night.scheme
            else AppTheme.Light.scheme
        }
        else -> appTheme.scheme
    }
    MaterialTheme(
        colorScheme = appTheme.scheme,
        typography  = Typography,
//        shapes      = Shapes,
        content     = content
    )
}
