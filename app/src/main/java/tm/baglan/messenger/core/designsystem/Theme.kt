package tm.baglan.messenger.core.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class BaglanChatColors(
    val chatBackground: Color,
    val outBubble: Color,
    val inBubble: Color,
    val outText: Color,
    val inText: Color,
    val outTime: Color,
    val inTime: Color,
    val outCheck: Color,
    val topBarBackground: Color,
    val topBarSubtitle: Color,
    val inputBackground: Color,
    val inputIcons: Color,
    val inputHint: Color,
    val divider: Color
)

private val LightChatColors = BaglanChatColors(
    chatBackground = ChatBackgroundLight,
    outBubble = OutBubbleLight,
    inBubble = InBubbleLight,
    outText = TextPrimaryLight,
    inText = TextPrimaryLight,
    outTime = OutTimeLight,
    inTime = InTimeLight,
    outCheck = OutSentCheckLight,
    topBarBackground = TopBarBackgroundLight,
    topBarSubtitle = TopBarSubtitleLight,
    inputBackground = InputBackgroundLight,
    inputIcons = InputIconsLight,
    inputHint = InputHintLight,
    divider = DividerLight
)

private val DarkChatColors = BaglanChatColors(
    chatBackground = ChatBackgroundDark,
    outBubble = OutBubbleDark,
    inBubble = InBubbleDark,
    outText = TextPrimaryDark,
    inText = TextPrimaryDark,
    outTime = OutTimeDark,
    inTime = InTimeDark,
    outCheck = OutSentCheckDark,
    topBarBackground = TopBarBackgroundDark,
    topBarSubtitle = TopBarSubtitleDark,
    inputBackground = InputBackgroundDark,
    inputIcons = InputIconsDark,
    inputHint = InputHintDark,
    divider = DividerDark
)

val LocalBaglanChatColors = staticCompositionLocalOf { LightChatColors }

private val LightMaterialColorScheme = lightColorScheme(
    primary = BaglanPrimary,
    background = ChatBackgroundLight,
    surface = TopBarBackgroundLight
)

private val DarkMaterialColorScheme = darkColorScheme(
    primary = BaglanPrimaryDark,
    background = ChatBackgroundDark,
    surface = TopBarBackgroundDark
)

object BaglanTheme {
    val colors: BaglanChatColors
        @Composable
        get() = LocalBaglanChatColors.current
}

@Composable
fun BaglanTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val chatColors = if (darkTheme) DarkChatColors else LightChatColors
    val materialColors = if (darkTheme) DarkMaterialColorScheme else LightMaterialColorScheme

    CompositionLocalProvider(LocalBaglanChatColors provides chatColors) {
        MaterialTheme(
            colorScheme = materialColors,
            typography = BaglanTypography,
            content = content
        )
    }
}
