package tm.baglan.messenger.core.canvas

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextLayoutResult
import tm.baglan.messenger.core.model.Message

@Immutable
data class MessageDrawSpec(
    val message: Message,
    val bubbleWidth: Float,
    val bubbleHeight: Float,
    val textOffsetX: Float,
    val textOffsetY: Float,
    val timeOffsetX: Float,
    val timeOffsetY: Float,
    val timeString: String,
    val statusOffsetX: Float,
    val statusOffsetY: Float,
    val statusSize: Float,
    val textLayoutResult: TextLayoutResult
)

