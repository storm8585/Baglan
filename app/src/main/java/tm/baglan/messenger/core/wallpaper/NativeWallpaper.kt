package tm.baglan.messenger.core.wallpaper

import android.graphics.Bitmap

object NativeWallpaper {
    init {
        System.loadLibrary("baglan")
    }

    external fun generateGradient(
        bitmap: Bitmap,
        phase: Int,
        progress: Float,
        colors: IntArray
    )
}

