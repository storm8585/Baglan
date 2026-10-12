package tm.baglan.messenger.core.canvas

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import tm.baglan.messenger.core.model.MessageStatus

object StatusIconDrawer {

    private val tickPath = Path()

    fun drawStatus(
        drawScope: DrawScope,
        status: MessageStatus,
        offset: Offset,
        color: Color,
        size: Float = 12f
    ) {
        when (status) {
            MessageStatus.SENDING -> drawClock(drawScope, offset, color, size)
            MessageStatus.SENT -> drawSingleTick(drawScope, offset, color, size)
            MessageStatus.READ -> drawDoubleTick(drawScope, offset, color, size)
            MessageStatus.FAILED -> drawError(drawScope, offset, color, size)
        }
    }

    private fun drawClock(
        drawScope: DrawScope,
        offset: Offset,
        color: Color,
        size: Float
    ) {
        val radius = size / 2f
        val center = Offset(offset.x + radius, offset.y + radius)
        val stroke = Stroke(width = size * 0.1f)

        drawScope.drawCircle(
            color = color,
            radius = radius * 0.85f,
            center = center,
            style = stroke
        )

        drawScope.drawLine(
            color = color,
            start = center,
            end = Offset(center.x, center.y - radius * 0.5f),
            strokeWidth = size * 0.1f,
            cap = StrokeCap.Round
        )

        drawScope.drawLine(
            color = color,
            start = center,
            end = Offset(center.x + radius * 0.4f, center.y),
            strokeWidth = size * 0.1f,
            cap = StrokeCap.Round
        )
    }

    private fun drawSingleTick(
        drawScope: DrawScope,
        offset: Offset,
        color: Color,
        size: Float
    ) {
        val stroke = Stroke(
            width = size * 0.13f,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )

        tickPath.reset()
        tickPath.moveTo(offset.x + size * 0.08f, offset.y + size * 0.52f)
        tickPath.lineTo(offset.x + size * 0.38f, offset.y + size * 0.82f)
        tickPath.lineTo(offset.x + size * 0.92f, offset.y + size * 0.22f)

        drawScope.drawPath(
            path = tickPath,
            color = color,
            style = stroke
        )
    }

    private fun drawDoubleTick(
        drawScope: DrawScope,
        offset: Offset,
        color: Color,
        size: Float
    ) {
        val stroke = Stroke(
            width = size * 0.13f,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )

        tickPath.reset()
        tickPath.moveTo(offset.x, offset.y + size * 0.52f)
        tickPath.lineTo(offset.x + size * 0.30f, offset.y + size * 0.82f)
        tickPath.lineTo(offset.x + size * 0.84f, offset.y + size * 0.22f)

        drawScope.drawPath(
            path = tickPath,
            color = color,
            style = stroke
        )

        tickPath.reset()
        tickPath.moveTo(offset.x + size * 0.44f, offset.y + size * 0.56f)
        tickPath.lineTo(offset.x + size * 0.65f, offset.y + size * 0.82f)
        tickPath.lineTo(offset.x + size * 1.18f, offset.y + size * 0.22f)

        drawScope.drawPath(
            path = tickPath,
            color = color,
            style = stroke
        )
    }

    private fun drawError(
        drawScope: DrawScope,
        offset: Offset,
        color: Color,
        size: Float
    ) {
        val radius = size / 2f
        val center = Offset(offset.x + radius, offset.y + radius)

        drawScope.drawCircle(
            color = color,
            radius = radius,
            center = center
        )

        drawScope.drawLine(
            color = Color.White,
            start = Offset(center.x, center.y - radius * 0.5f),
            end = Offset(center.x, center.y + radius * 0.1f),
            strokeWidth = size * 0.14f,
            cap = StrokeCap.Round
        )

        drawScope.drawCircle(
            color = Color.White,
            radius = size * 0.08f,
            center = Offset(center.x, center.y + radius * 0.5f)
        )
    }
}
