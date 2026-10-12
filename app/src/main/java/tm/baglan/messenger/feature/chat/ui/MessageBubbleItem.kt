package tm.baglan.messenger.feature.chat.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import tm.baglan.messenger.core.canvas.BubbleGeometry
import tm.baglan.messenger.core.canvas.MessageDrawSpec
import tm.baglan.messenger.core.canvas.StatusIconDrawer
import tm.baglan.messenger.core.designsystem.BaglanTheme
import tm.baglan.messenger.core.designsystem.MessageTimeStyle

@Composable
fun MessageBubbleItem(
    spec: MessageDrawSpec,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current.density
    val textMeasurer = rememberTextMeasurer()
    val bubblePath = remember { Path() }

    val chatColors = BaglanTheme.colors
    val isOutgoing = spec.message.isOutgoing
    val bubbleColor = if (isOutgoing) chatColors.outBubble else chatColors.inBubble
    val shadowColor = if (isOutgoing) chatColors.outBubbleShadow else chatColors.inBubbleShadow
    val timeColor = if (isOutgoing) chatColors.outTime else chatColors.inTime
    val checkColor = if (isOutgoing) chatColors.outCheck else chatColors.inTime

    val bubbleWidthDp = with(LocalDensity.current) { spec.bubbleWidth.toDp() }
    val bubbleHeightDp = with(LocalDensity.current) { spec.bubbleHeight.toDp() }

    val verticalPadding = when {
        spec.message.isBottomNear -> 2.dp
        else -> 6.dp
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = verticalPadding),
        contentAlignment = if (isOutgoing) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .size(width = bubbleWidthDp, height = bubbleHeightDp)
                .drawWithCache {
                    onDrawBehind {
                        BubbleGeometry.buildBubblePath(
                            path = bubblePath,
                            width = spec.bubbleWidth,
                            height = spec.bubbleHeight,
                            isOutgoing = isOutgoing,
                            isTopNear = spec.message.isTopNear,
                            isBottomNear = spec.message.isBottomNear,
                            density = density
                        )

                        translate(left = 0f, top = 1f * density) {
                            drawPath(
                                path = bubblePath,
                                color = shadowColor
                            )
                        }

                        drawPath(
                            path = bubblePath,
                            color = bubbleColor
                        )

                        drawText(
                            textLayoutResult = spec.textLayoutResult,
                            topLeft = Offset(spec.textOffsetX, spec.textOffsetY)
                        )

                        drawText(
                            textMeasurer = textMeasurer,
                            text = spec.timeString,
                            topLeft = Offset(spec.timeOffsetX, spec.timeOffsetY),
                            style = MessageTimeStyle.copy(color = timeColor)
                        )

                        if (isOutgoing) {
                            StatusIconDrawer.drawStatus(
                                drawScope = this,
                                status = spec.message.status,
                                offset = Offset(spec.statusOffsetX, spec.statusOffsetY),
                                color = checkColor,
                                size = spec.statusSize
                            )
                        }
                    }
                }
        )
    }
}
