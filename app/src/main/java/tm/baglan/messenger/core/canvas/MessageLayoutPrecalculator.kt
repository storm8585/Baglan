package tm.baglan.messenger.core.canvas

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.unit.Constraints
import tm.baglan.messenger.core.designsystem.MessageBodyStyle
import tm.baglan.messenger.core.designsystem.MessageTimeStyle
import tm.baglan.messenger.core.model.Message
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.max

object MessageLayoutPrecalculator {

    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    fun precalculate(
        message: Message,
        textMeasurer: TextMeasurer,
        maxContainerWidth: Float,
        density: Float
    ): MessageDrawSpec {
        val tailWidth = 8f * density
        val horizontalPadding = 11f * density
        val verticalPadding = 6f * density
        val maxTextWidth = maxContainerWidth * 0.78f - horizontalPadding * 2f - tailWidth

        val timeString = timeFormat.format(Date(message.timestamp))

        val textLayoutResult = textMeasurer.measure(
            text = AnnotatedString(message.text),
            style = MessageBodyStyle,
            constraints = Constraints(maxWidth = maxTextWidth.toInt())
        )

        val timeLayoutResult = textMeasurer.measure(
            text = AnnotatedString(timeString),
            style = MessageTimeStyle
        )

        val statusSize = if (message.isOutgoing) 12f * density else 0f
        val statusGap = if (message.isOutgoing) 4f * density else 0f
        val timeTotalWidth = timeLayoutResult.size.width + statusGap + statusSize

        val textWidth = textLayoutResult.size.width.toFloat()
        val textHeight = textLayoutResult.size.height.toFloat()

        val lastLineIndex = textLayoutResult.lineCount - 1
        val lastLineWidth = textLayoutResult.getLineRight(lastLineIndex) - textLayoutResult.getLineLeft(lastLineIndex)

        val minTimeSpace = 8f * density
        val fitsOnLastLine = (lastLineWidth + minTimeSpace + timeTotalWidth) <= maxTextWidth

        val bubbleWidth: Float
        val bubbleHeight: Float
        val timeOffsetX: Float
        val timeOffsetY: Float

        val textOffsetX = if (message.isOutgoing) horizontalPadding else horizontalPadding + tailWidth
        val textOffsetY = verticalPadding

        if (fitsOnLastLine) {
            val contentWidth = max(textWidth, lastLineWidth + minTimeSpace + timeTotalWidth)
            bubbleWidth = max(contentWidth + horizontalPadding * 2f + tailWidth, 58f * density)
            bubbleHeight = textHeight + verticalPadding * 2f

            timeOffsetX = if (message.isOutgoing) {
                bubbleWidth - tailWidth - horizontalPadding - timeTotalWidth
            } else {
                bubbleWidth - horizontalPadding - timeTotalWidth
            }
            timeOffsetY = bubbleHeight - verticalPadding - timeLayoutResult.size.height + 2f * density
        } else {
            val contentWidth = max(textWidth, timeTotalWidth)
            bubbleWidth = max(contentWidth + horizontalPadding * 2f + tailWidth, 58f * density)
            bubbleHeight = textHeight + verticalPadding * 2f + timeLayoutResult.size.height

            timeOffsetX = if (message.isOutgoing) {
                bubbleWidth - tailWidth - horizontalPadding - timeTotalWidth
            } else {
                bubbleWidth - horizontalPadding - timeTotalWidth
            }
            timeOffsetY = bubbleHeight - verticalPadding - timeLayoutResult.size.height
        }

        val statusOffsetX = timeOffsetX + timeLayoutResult.size.width + statusGap
        val statusOffsetY = timeOffsetY + (timeLayoutResult.size.height - statusSize) / 2f + 1f * density

        return MessageDrawSpec(
            message = message,
            bubbleWidth = bubbleWidth,
            bubbleHeight = bubbleHeight,
            textOffsetX = textOffsetX,
            textOffsetY = textOffsetY,
            timeOffsetX = timeOffsetX,
            timeOffsetY = timeOffsetY,
            timeString = timeString,
            statusOffsetX = statusOffsetX,
            statusOffsetY = statusOffsetY,
            statusSize = statusSize,
            textLayoutResult = textLayoutResult
        )
    }
}
