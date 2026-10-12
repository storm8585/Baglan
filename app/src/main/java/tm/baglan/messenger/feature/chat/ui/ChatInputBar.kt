package tm.baglan.messenger.feature.chat.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.launch
import tm.baglan.messenger.R
import tm.baglan.messenger.core.designsystem.BaglanPrimary
import tm.baglan.messenger.core.designsystem.BaglanTheme
import tm.baglan.messenger.core.designsystem.MessageBodyStyle

private val EaseOutQuint = CubicBezierEasing(0.23f, 1f, 0.32f, 1f)

@Composable
fun ChatInputBar(
    text: String,
    onTextChanged: (String) -> Unit,
    onSendMessage: () -> Unit,
    onAttachClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current.density
    val hasText = text.isNotBlank()

    val micComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.voice_and_video))
    var isVideoMode by remember { mutableStateOf(false) }
    var isAudioVideoAnimating by remember { mutableStateOf(false) }
    val lottieProgress = remember { Animatable(0.5f) }
    val coroutineScope = rememberCoroutineScope()

    val appearProgress = remember { Animatable(0f) }
    LaunchedEffect(hasText) {
        if (hasText) {
            appearProgress.snapTo(0f)
            appearProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 320, easing = EaseOutQuint)
            )
        } else {
            appearProgress.snapTo(0f)
        }
    }

    val sendButtonScale by animateFloatAsState(
        targetValue = if (hasText) 1f else 0.1f,
        animationSpec = tween(
            durationMillis = if (hasText) 220 else 150,
            easing = EaseOutQuint
        ),
        label = "sendButtonScale"
    )
    val sendButtonAlpha by animateFloatAsState(
        targetValue = if (hasText) 1f else 0f,
        animationSpec = tween(
            durationMillis = if (hasText) 220 else 150,
            easing = EaseOutQuint
        ),
        label = "sendButtonAlpha"
    )

    val micButtonScale by animateFloatAsState(
        targetValue = if (hasText) 0.1f else 1f,
        animationSpec = tween(
            durationMillis = if (hasText) 220 else 150,
            easing = EaseOutQuint
        ),
        label = "micButtonScale"
    )
    val micButtonAlpha by animateFloatAsState(
        targetValue = if (hasText) 0f else 1f,
        animationSpec = tween(
            durationMillis = if (hasText) 220 else 150,
            easing = EaseOutQuint
        ),
        label = "micButtonAlpha"
    )

    val attachButtonScale by animateFloatAsState(
        targetValue = if (hasText) 0.5f else 1f,
        animationSpec = tween(
            durationMillis = 100,
            easing = FastOutSlowInEasing
        ),
        label = "attachButtonScale"
    )
    val attachButtonAlpha by animateFloatAsState(
        targetValue = if (hasText) 0f else 1f,
        animationSpec = tween(
            durationMillis = 100,
            easing = FastOutSlowInEasing
        ),
        label = "attachButtonAlpha"
    )

    val textPaddingEnd by animateDpAsState(
        targetValue = if (hasText) 46.dp else 88.dp,
        animationSpec = tween(
            durationMillis = 150,
            easing = FastOutSlowInEasing
        ),
        label = "textPaddingEnd"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(start = 7.dp, end = 7.dp, top = 4.dp, bottom = 9.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(BaglanTheme.colors.inputBackground)
            .heightIn(min = 44.dp)
    ) {
        IconButton(
            onClick = {},
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 2.dp)
                .size(44.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.msg_emoji_smiles),
                contentDescription = null,
                tint = BaglanTheme.colors.inputIcons,
                modifier = Modifier.size(28.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(start = 52.dp, end = textPaddingEnd, top = 9.dp, bottom = 10.dp)
        ) {
            if (text.isEmpty()) {
                Text(
                    text = "Message",
                    style = MessageBodyStyle.copy(fontSize = 18.sp),
                    color = BaglanTheme.colors.inputHint
                )
            }

            BasicTextField(
                value = text,
                onValueChange = onTextChanged,
                textStyle = MessageBodyStyle.copy(
                    fontSize = 18.sp,
                    color = BaglanTheme.colors.inText
                ),
                cursorBrush = SolidColor(BaglanPrimary),
                maxLines = 6,
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (attachButtonAlpha > 0f) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 44.dp)
                    .size(44.dp)
                    .graphicsLayer {
                        scaleX = attachButtonScale
                        scaleY = attachButtonScale
                        alpha = attachButtonAlpha
                    },
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = onAttachClicked,
                    modifier = Modifier.size(44.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.msg_input_attach2),
                        contentDescription = null,
                        tint = BaglanTheme.colors.inputIcons,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(44.dp),
            contentAlignment = Alignment.Center
        ) {
            if (sendButtonAlpha > 0f) {
                val p = appearProgress.value
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .graphicsLayer {
                            scaleX = sendButtonScale
                            scaleY = sendButtonScale
                            alpha = sendButtonAlpha
                        }
                        .clip(CircleShape)
                        .background(BaglanPrimary)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = onSendMessage
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.send_plane_24),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(24.dp)
                            .graphicsLayer {
                                translationX = -24f * density * (1f - p)
                                translationY = 24f * density * (1f - p)
                                rotationZ = 60f * (1f - p)
                                val s = 0.35f + 0.65f * p
                                scaleX = s
                                scaleY = s
                            }
                    )
                }
            }

            if (micButtonAlpha > 0f) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .graphicsLayer {
                            scaleX = micButtonScale
                            scaleY = micButtonScale
                            alpha = micButtonAlpha
                        }
                        .clip(CircleShape)
                        .background(BaglanPrimary)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                if (isAudioVideoAnimating) return@clickable
                                isAudioVideoAnimating = true
                                isVideoMode = !isVideoMode
                                coroutineScope.launch {
                                    if (isVideoMode) {
                                        lottieProgress.animateTo(
                                            targetValue = 1f,
                                            animationSpec = tween(durationMillis = 500, easing = LinearEasing)
                                        )
                                    } else {
                                        lottieProgress.snapTo(0f)
                                        lottieProgress.animateTo(
                                            targetValue = 0.5f,
                                            animationSpec = tween(durationMillis = 500, easing = LinearEasing)
                                        )
                                    }
                                    isAudioVideoAnimating = false
                                }
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (micComposition != null) {
                        LottieAnimation(
                            composition = micComposition,
                            progress = { lottieProgress.value },
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_mic_filled),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}
