package tm.baglan.messenger.core.designsystem

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val MessageBodyStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    lineHeight = 21.sp
)

val MessageTimeStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    lineHeight = 15.sp
)

val TopBarTitleStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.SemiBold,
    fontSize = 18.sp,
    lineHeight = 22.sp
)

val TopBarSubtitleStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 13.sp,
    lineHeight = 16.sp
)

val BaglanTypography = Typography(
    bodyLarge = MessageBodyStyle,
    labelSmall = MessageTimeStyle,
    titleMedium = TopBarTitleStyle,
    bodySmall = TopBarSubtitleStyle
)
