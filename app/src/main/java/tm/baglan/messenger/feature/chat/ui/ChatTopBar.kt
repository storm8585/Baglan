package tm.baglan.messenger.feature.chat.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tm.baglan.messenger.R
import tm.baglan.messenger.core.designsystem.BaglanPrimary
import tm.baglan.messenger.core.designsystem.BaglanTheme
import tm.baglan.messenger.core.designsystem.TopBarSubtitleStyle
import tm.baglan.messenger.core.designsystem.TopBarTitleStyle
import tm.baglan.messenger.core.model.User

@Composable
fun ChatTopBar(
    user: User,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = BaglanTheme.colors.topBarBackground,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .height(56.dp)
                    .padding(end = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClicked,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_ab_back),
                        contentDescription = null,
                        tint = BaglanTheme.colors.inText,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(onClick = {}),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(BaglanPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = user.name.firstOrNull()?.uppercase() ?: "",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = user.name,
                            style = TopBarTitleStyle,
                            color = BaglanTheme.colors.inText
                        )

                        val statusText = if (user.isOnline) "online" else user.lastSeenText
                        val statusColor = if (user.isOnline) BaglanPrimary else BaglanTheme.colors.topBarSubtitle

                        Text(
                            text = statusText,
                            style = TopBarSubtitleStyle,
                            color = statusColor
                        )
                    }
                }

                IconButton(
                    onClick = {},
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_profile_call_24),
                        contentDescription = null,
                        tint = BaglanTheme.colors.inText,
                        modifier = Modifier.size(24.dp)
                    )
                }

                IconButton(
                    onClick = {},
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_ab_other),
                        contentDescription = null,
                        tint = BaglanTheme.colors.inText,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            HorizontalDivider(
                thickness = 0.5.dp,
                color = BaglanTheme.colors.divider
            )
        }
    }
}
