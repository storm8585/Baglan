package tm.baglan.messenger.feature.chat.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
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

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        BaglanWallpaper()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
        ) {
            ChatTopBar(
                user = uiState.user,
                onBackClicked = { viewModel.onEvent(ChatUiEvent.OnBackClicked) }
            )

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                val maxContainerWidthPx = constraints.maxWidth.toFloat()

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
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        items = drawSpecs,
                        key = { it.message.id }
                    ) { spec ->
                        MessageBubbleItem(spec = spec)
                    }
                }
            }

            ChatInputBar(
                text = uiState.inputText,
                onTextChanged = { viewModel.onEvent(ChatUiEvent.OnInputChanged(it)) },
                onSendMessage = { viewModel.onEvent(ChatUiEvent.OnSendMessage) },
                onAttachClicked = { viewModel.onEvent(ChatUiEvent.OnAttachClicked) }
            )
        }
    }
}
