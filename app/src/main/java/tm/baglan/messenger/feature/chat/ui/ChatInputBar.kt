package tm.baglan.messenger.feature.chat.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.outlined.SentimentSatisfied
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import tm.baglan.messenger.core.designsystem.BaglanPrimary
import tm.baglan.messenger.core.designsystem.BaglanTheme
import tm.baglan.messenger.core.designsystem.MessageBodyStyle

@Composable
fun ChatInputBar(
    text: String,
    onTextChanged: (String) -> Unit,
    onSendMessage: () -> Unit,
    onAttachClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = BaglanTheme.colors.inputBackground,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            HorizontalDivider(
                thickness = 0.5.dp,
                color = BaglanTheme.colors.divider
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = 4.dp, vertical = 2.dp)
                    .heightIn(min = 48.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {},
                    modifier = Modifier.size(44.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.SentimentSatisfied,
                        contentDescription = null,
                        tint = BaglanTheme.colors.inputIcons,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 6.dp, vertical = 10.dp)
                ) {
                    if (text.isEmpty()) {
                        Text(
                            text = "Message",
                            style = MessageBodyStyle,
                            color = BaglanTheme.colors.inputHint
                        )
                    }

                    BasicTextField(
                        value = text,
                        onValueChange = onTextChanged,
                        textStyle = MessageBodyStyle.copy(color = BaglanTheme.colors.inText),
                        cursorBrush = SolidColor(BaglanPrimary),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                IconButton(
                    onClick = onAttachClicked,
                    modifier = Modifier.size(44.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AttachFile,
                        contentDescription = null,
                        tint = BaglanTheme.colors.inputIcons,
                        modifier = Modifier.size(24.dp)
                    )
                }

                AnimatedContent(
                    targetState = text.isNotBlank(),
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    label = "SendOrMic"
                ) { hasText ->
                    if (hasText) {
                        IconButton(
                            onClick = onSendMessage,
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(BaglanPrimary)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    } else {
                        IconButton(
                            onClick = {},
                            modifier = Modifier.size(44.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Mic,
                                contentDescription = null,
                                tint = BaglanTheme.colors.inputIcons,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
