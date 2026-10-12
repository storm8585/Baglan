package tm.baglan.messenger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import tm.baglan.messenger.core.designsystem.BaglanTheme
import tm.baglan.messenger.feature.chat.ui.ChatScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BaglanTheme {
                ChatScreen()
            }
        }
    }
}

