package tm.baglan.messenger.feature.chat.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tm.baglan.messenger.core.canvas.MessageDrawSpec
import tm.baglan.messenger.core.canvas.MessageLayoutPrecalculator
import tm.baglan.messenger.core.wallpaper.BaglanWallpaper
import tm.baglan.messenger.feature.chat.ChatUiEvent
import tm.baglan.messenger.feature.chat.ChatViewModel

@Composable
fun ChatScreen(
    viewModel: ChatViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current.density
    val navBarBottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        BaglanWallpaper()

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
        ) {
            val maxContainerWidthPx = constraints.maxWidth.toFloat()
            val statusBarTop = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

            val drawSpecs by produceState(
                initialValue = emptyList<MessageDrawSpec>(),
                key1 = uiState.messages,
                key2 = maxContainerWidthPx
            ) {
                value = withContext(Dispatchers.Default) {
                    uiState.messages.map { message ->
                        MessageLayoutPrecalculator.precalculate(
                            message = message,
                            textMeasurer = textMeasurer,
                            maxContainerWidth = maxContainerWidthPx,
                            density = density
                        )
                    }
                }
            }

            LazyColumn(
                reverseLayout = true,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    top = 76.dp + navBarBottom,
                    bottom = 64.dp + statusBarTop
                )
            ) {
                items(
                    items = drawSpecs,
                    key = { it.message.id }
                ) { spec ->
                    MessageBubbleItem(spec = spec)
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .imePadding()
                .height(96.dp + navBarBottom)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color(0x05000000),
                            Color(0x14000000),
                            Color(0x28000000),
                            Color(0x40000000)
                        )
                    )
                )
        )

        ChatInputBar(
            text = uiState.inputText,
            onTextChanged = { viewModel.onEvent(ChatUiEvent.OnInputChanged(it)) },
            onSendMessage = { viewModel.onEvent(ChatUiEvent.OnSendMessage) },
            onAttachClicked = { viewModel.onEvent(ChatUiEvent.OnAttachClicked) },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .imePadding()
        )

        ChatTopBar(
            user = uiState.user,
            onBackClicked = { viewModel.onEvent(ChatUiEvent.OnBackClicked) },
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}
