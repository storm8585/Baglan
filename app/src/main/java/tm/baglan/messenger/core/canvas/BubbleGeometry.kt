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

        val rad = 16f * density
        val nearRad = 6f * density
        val tailWidth = 8f * density
        val padding = 2f * density

        val maxAllowedRad = (height - padding * 2) / 2f
        val actualRad = if (rad > maxAllowedRad) maxAllowedRad else rad
        val actualNearRad = if (nearRad > maxAllowedRad) maxAllowedRad else nearRad

        if (isOutgoing) {
            val rightTail = width - tailWidth
            val left = padding
            val top = padding
            val bottom = height - padding

            val topLeftRad = actualRad
            val topRightRad = if (isTopNear) actualNearRad else actualRad
            val bottomLeftRad = actualRad

            path.moveTo(rightTail, top + topRightRad)

            path.arcTo(
                rect = Rect(rightTail - topRightRad * 2f, top, rightTail, top + topRightRad * 2f),
                startAngleDegrees = 0f,
                sweepAngleDegrees = -90f,
                forceMoveTo = false
            )

            path.lineTo(left + topLeftRad, top)

            path.arcTo(
                rect = Rect(left, top, left + topLeftRad * 2f, top + topLeftRad * 2f),
                startAngleDegrees = 270f,
                sweepAngleDegrees = -90f,
                forceMoveTo = false
            )

            path.lineTo(left, bottom - bottomLeftRad)

            path.arcTo(
                rect = Rect(left, bottom - bottomLeftRad * 2f, left + bottomLeftRad * 2f, bottom),
                startAngleDegrees = 180f,
                sweepAngleDegrees = -90f,
                forceMoveTo = false
            )

            if (isBottomNear) {
                path.lineTo(rightTail - actualNearRad, bottom)
                path.arcTo(
                    rect = Rect(rightTail - actualNearRad * 2f, bottom - actualNearRad * 2f, rightTail, bottom),
                    startAngleDegrees = 90f,
                    sweepAngleDegrees = -90f,
                    forceMoveTo = false
                )
            } else {
                path.lineTo(rightTail - actualNearRad, bottom)
                path.quadraticBezierTo(
                    x1 = width,
                    y1 = bottom,
                    x2 = width,
                    y2 = bottom - 6f * density
                )
                path.quadraticBezierTo(
                    x1 = rightTail,
                    y1 = bottom - 2f * density,
                    x2 = rightTail,
                    y2 = bottom - 10f * density
                )
            }

            path.lineTo(rightTail, top + topRightRad)
            path.close()
        } else {
            val leftTail = tailWidth
            val right = width - padding
            val top = padding
            val bottom = height - padding

            val topLeftRad = if (isTopNear) actualNearRad else actualRad
            val topRightRad = actualRad
            val bottomRightRad = actualRad

            path.moveTo(leftTail + topLeftRad, top)

            path.lineTo(right - topRightRad, top)

            path.arcTo(
                rect = Rect(right - topRightRad * 2f, top, right, top + topRightRad * 2f),
                startAngleDegrees = 270f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )

            path.lineTo(right, bottom - bottomRightRad)

            path.arcTo(
                rect = Rect(right - bottomRightRad * 2f, bottom - bottomRightRad * 2f, right, bottom),
                startAngleDegrees = 0f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )

            if (isBottomNear) {
                path.lineTo(leftTail + actualNearRad, bottom)
                path.arcTo(
                    rect = Rect(leftTail, bottom - actualNearRad * 2f, leftTail + actualNearRad * 2f, bottom),
                    startAngleDegrees = 90f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
            } else {
                path.lineTo(leftTail + actualNearRad, bottom)
                path.quadraticBezierTo(
                    x1 = 0f,
                    y1 = bottom,
                    x2 = 0f,
                    y2 = bottom - 6f * density
                )
                path.quadraticBezierTo(
                    x1 = leftTail,
                    y1 = bottom - 2f * density,
                    x2 = leftTail,
                    y2 = bottom - 10f * density
                )
            }

            path.lineTo(leftTail, top + topLeftRad)

            path.arcTo(
                rect = Rect(leftTail, top, leftTail + topLeftRad * 2f, top + topLeftRad * 2f),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )

            path.close()
        }
    }
}

