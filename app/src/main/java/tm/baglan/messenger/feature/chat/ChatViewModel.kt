package tm.baglan.messenger.feature.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tm.baglan.messenger.core.model.Message
import tm.baglan.messenger.core.model.MessageStatus
import tm.baglan.messenger.core.model.User

class ChatViewModel : ViewModel() {

    private val currentUser = User(
        id = 101L,
        name = "Ahmet Yılmaz",
        isOnline = true,
        lastSeenText = "online"
    )

    private val _uiState = MutableStateFlow(
        ChatUiState(
            user = currentUser,
            messages = createInitialMessages()
        )
    )
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    fun onEvent(event: ChatUiEvent) {
        when (event) {
            is ChatUiEvent.OnInputChanged -> {
                _uiState.update { it.copy(inputText = event.text) }
            }
            is ChatUiEvent.OnSendMessage -> {
                val currentText = _uiState.value.inputText.trim()
                if (currentText.isEmpty()) return

                _uiState.update { it.copy(inputText = "") }

                val newMessageId = System.currentTimeMillis()
                val newMessage = Message(
                    id = newMessageId,
                    senderId = 1L,
                    text = currentText,
                    timestamp = System.currentTimeMillis(),
                    isOutgoing = true,
                    status = MessageStatus.SENDING
                )

                val updatedList = updateGroupings(listOf(newMessage) + _uiState.value.messages)
                _uiState.update { it.copy(messages = updatedList) }

                viewModelScope.launch {
                    delay(400)
                    updateMessageStatus(newMessageId, MessageStatus.SENT)
                    delay(800)
                    updateMessageStatus(newMessageId, MessageStatus.READ)
                }
            }
            is ChatUiEvent.OnBackClicked -> {}
            is ChatUiEvent.OnAttachClicked -> {}
        }
    }

    private fun updateMessageStatus(messageId: Long, newStatus: MessageStatus) {
        _uiState.update { state ->
            val updatedMessages = state.messages.map { msg ->
                if (msg.id == messageId) msg.copy(status = newStatus) else msg
            }
            state.copy(messages = updatedMessages)
        }
    }

    private fun updateGroupings(messages: List<Message>): List<Message> {
        val result = mutableListOf<Message>()
        for (i in messages.indices) {
            val current = messages[i]
            val prev = messages.getOrNull(i - 1)
            val next = messages.getOrNull(i + 1)

            val isBottomNear = prev != null && prev.isOutgoing == current.isOutgoing && (prev.timestamp - current.timestamp) < 300_000L
            val isTopNear = next != null && next.isOutgoing == current.isOutgoing && (current.timestamp - next.timestamp) < 300_000L

            result.add(current.copy(isTopNear = isTopNear, isBottomNear = isBottomNear))
        }
        return result
    }

    private fun createInitialMessages(): List<Message> {
        val baseTime = System.currentTimeMillis() - 3600_000
        val raw = listOf(
            Message(
                id = 1L,
                senderId = 101L,
                text = "Selam! Yeni Baglan projesine başladık mı?",
                timestamp = baseTime,
                isOutgoing = false,
                status = MessageStatus.READ
            ),
            Message(
                id = 2L,
                senderId = 1L,
                text = "Evet, şu an 1'e 1 chat ekranını Baglan'ın orijinal canvas mimarisiyle yazıyoruz.",
                timestamp = baseTime + 60_000,
                isOutgoing = true,
                status = MessageStatus.READ
            ),
            Message(
                id = 3L,
                senderId = 1L,
                text = "Tam 120 FPS akıcılık için balonları ve metinleri tek geçişte doğrudan GPU'ya basıyoruz.",
                timestamp = baseTime + 70_000,
                isOutgoing = true,
                status = MessageStatus.READ
            ),
            Message(
                id = 4L,
                senderId = 101L,
                text = "Harika! Baglan'ın kuyruklu balonları ve okundu tikleri de çalışıyor mu?",
                timestamp = baseTime + 120_000,
                isOutgoing = false,
                status = MessageStatus.READ
            ),
            Message(
                id = 5L,
                senderId = 1L,
                text = "Aynen öyle! Kuyruk bezier eğrileri ve çift tık ikonları saf vektör olarak çiziliyor.",
                timestamp = baseTime + 180_000,
                isOutgoing = true,
                status = MessageStatus.READ
            )
        )
        return updateGroupings(raw.reversed())
    }
}

