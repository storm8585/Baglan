package tm.baglan.messenger.core.canvas

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path

object BubbleGeometry {

    fun buildBubblePath(
        path: Path,
        width: Float,
        height: Float,
        isOutgoing: Boolean,
        isTopNear: Boolean,
        isBottomNear: Boolean,
        density: Float
    ) {
        path.reset()

        val rad = 17f * density
        val nearRad = 6f * density
        val smallRad = 6f * density
        val tailWidth = 8f * density
        val padding = 2f * density

        val heightHalf = (height - padding * 2f) / 2f
        val actualRad = if (rad > heightHalf) heightHalf else rad
        val actualNearRad = if (nearRad > heightHalf) heightHalf else nearRad

        if (isOutgoing) {
            val radToUseBottom = if (isBottomNear) actualNearRad else actualRad
            val radToUseTop = if (isTopNear) actualNearRad else actualRad

            if (isBottomNear) {
                path.moveTo(width - tailWidth - radToUseBottom, height - padding)
                path.lineTo(padding + actualRad, height - padding)
                path.arcTo(
                    rect = Rect(padding, height - padding - actualRad * 2f, padding + actualRad * 2f, height - padding),
                    startAngleDegrees = 90f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
            } else {
                path.moveTo(width - 2.6f * density, height - padding)
                path.lineTo(padding + actualRad, height - padding)
                path.arcTo(
                    rect = Rect(padding, height - padding - actualRad * 2f, padding + actualRad * 2f, height - padding),
                    startAngleDegrees = 90f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
            }

            path.lineTo(padding, padding + actualRad)
            path.arcTo(
                rect = Rect(padding, padding, padding + actualRad * 2f, padding + actualRad * 2f),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )

            path.lineTo(width - tailWidth - radToUseTop, padding)
            path.arcTo(
                rect = Rect(width - tailWidth - radToUseTop * 2f, padding, width - tailWidth, padding + radToUseTop * 2f),
                startAngleDegrees = 270f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )

            if (isBottomNear) {
                path.lineTo(width - tailWidth, height - padding - radToUseBottom)
                path.arcTo(
                    rect = Rect(width - tailWidth - radToUseBottom * 2f, height - padding - radToUseBottom * 2f, width - tailWidth, height - padding),
                    startAngleDegrees = 0f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
            } else {
                path.lineTo(width - tailWidth, height - padding - smallRad - 3f * density)
                path.arcTo(
                    rect = Rect(width - tailWidth, height - padding - smallRad * 2f - 9f * density, width - 7f * density + smallRad * 2f, height - padding - 1f * density),
                    startAngleDegrees = 180f,
                    sweepAngleDegrees = -83f,
                    forceMoveTo = false
                )
            }

            path.close()
        } else {
            val radToUseBottom = if (isBottomNear) actualNearRad else actualRad
            val radToUseTop = if (isTopNear) actualNearRad else actualRad

            if (isBottomNear) {
                path.moveTo(tailWidth + radToUseBottom, height - padding)
                path.lineTo(width - padding - actualRad, height - padding)
                path.arcTo(
                    rect = Rect(width - padding - actualRad * 2f, height - padding - actualRad * 2f, width - padding, height - padding),
                    startAngleDegrees = 90f,
                    sweepAngleDegrees = -90f,
                    forceMoveTo = false
                )
            } else {
                path.moveTo(2.6f * density, height - padding)
                path.lineTo(width - padding - actualRad, height - padding)
                path.arcTo(
                    rect = Rect(width - padding - actualRad * 2f, height - padding - actualRad * 2f, width - padding, height - padding),
                    startAngleDegrees = 90f,
                    sweepAngleDegrees = -90f,
                    forceMoveTo = false
                )
            }

            path.lineTo(width - padding, padding + actualRad)
            path.arcTo(
                rect = Rect(width - padding - actualRad * 2f, padding, width - padding, padding + actualRad * 2f),
                startAngleDegrees = 0f,
                sweepAngleDegrees = -90f,
                forceMoveTo = false
            )

            path.lineTo(tailWidth + radToUseTop, padding)
            path.arcTo(
                rect = Rect(tailWidth, padding, tailWidth + radToUseTop * 2f, padding + radToUseTop * 2f),
                startAngleDegrees = 270f,
                sweepAngleDegrees = -90f,
                forceMoveTo = false
            )

            if (isBottomNear) {
                path.lineTo(tailWidth, height - padding - radToUseBottom)
                path.arcTo(
                    rect = Rect(tailWidth, height - padding - radToUseBottom * 2f, tailWidth + radToUseBottom * 2f, height - padding),
                    startAngleDegrees = 180f,
                    sweepAngleDegrees = -90f,
                    forceMoveTo = false
                )
            } else {
                path.lineTo(tailWidth, height - padding - smallRad - 3f * density)
                path.arcTo(
                    rect = Rect(7f * density - smallRad * 2f, height - padding - smallRad * 2f - 9f * density, tailWidth, height - padding - 1f * density),
                    startAngleDegrees = 0f,
                    sweepAngleDegrees = 83f,
                    forceMoveTo = false
                )
            }

            path.close()
        }
    }
}
