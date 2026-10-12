package tm.baglan.messenger.feature.chat

import androidx.compose.runtime.Immutable
import tm.baglan.messenger.core.model.Message
import tm.baglan.messenger.core.model.User

@Immutable
data class ChatUiState(
    val user: User,
    val messages: List<Message> = emptyList(),
    val inputText: String = "",
    val isTyping: Boolean = false
)

