package tm.baglan.messenger.core.wallpaper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Path
import android.util.Xml
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.PathParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import tm.baglan.messenger.R

object BaglanPatternCache {
    private var cachedPath: Path? = null

    fun getPath(context: Context): Path {
        cachedPath?.let { return it }
        val path = Path()
        try {
            context.resources.openRawResource(R.raw.baglan_pattern).use { stream ->
                val parser = Xml.newPullParser()
                parser.setInput(stream, "UTF-8")
                var eventType = parser.eventType
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG && parser.name == "path") {
                        val d = parser.getAttributeValue(null, "d")
                        if (!d.isNullOrEmpty()) {
                            val subPath = PathParser.createPathFromPathData(d)
                            path.addPath(subPath)
                        }
                    }
                    eventType = parser.next()
                }
            }
        } catch (_: Exception) {}
        cachedPath = path
        return path
    }
}

@Composable
fun BaglanWallpaper(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var wallpaperBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    val gradientBitmap = remember {
        val bmp = Bitmap.createBitmap(60, 80, Bitmap.Config.ARGB_8888)
        val colors = intArrayOf(
            0xffdbddbb.toInt(),
            0xff6ba587.toInt(),
            0xffd5d88d.toInt(),
            0xff88b884.toInt()
        )
        try {
            NativeWallpaper.generateGradient(bmp, 0, 0f, colors)
        } catch (_: UnsatisfiedLinkError) {}
        bmp.asImageBitmap()
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        drawImage(
            image = gradientBitmap,
            dstSize = androidx.compose.ui.unit.IntSize(size.width.toInt(), size.height.toInt()),
            filterQuality = FilterQuality.Medium
        )

        wallpaperBitmap?.let { wp ->
            drawImage(
                image = wp,
                dstSize = androidx.compose.ui.unit.IntSize(size.width.toInt(), size.height.toInt()),
                filterQuality = FilterQuality.Low
            )
        }
    }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.Default) {
            val path = BaglanPatternCache.getPath(context)
            val width = context.resources.displayMetrics.widthPixels
            val height = context.resources.displayMetrics.heightPixels

            if (width > 0 && height > 0) {
                val renderBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                val canvas = android.graphics.Canvas(renderBitmap)

                val scale = maxOf(width / 1440f, height / 2960f)
                val scaledW = 1440f * scale
                val scaledH = 2960f * scale
                val dx = (width - scaledW) / 2f
                val dy = (height - scaledH) / 2f

                canvas.save()
                canvas.translate(dx, dy)
                canvas.scale(scale, scale)

                val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = android.graphics.Color.BLACK
                    alpha = (255 * 0.13f).toInt()
                    style = Paint.Style.FILL
                }
                canvas.drawPath(path, paint)
                canvas.restore()

                wallpaperBitmap = renderBitmap.asImageBitmap()
            }
        }
    }
}
