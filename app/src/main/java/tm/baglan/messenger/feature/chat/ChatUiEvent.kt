package tm.baglan.messenger.feature.chat

sealed interface ChatUiEvent {
    data class OnInputChanged(val text: String) : ChatUiEvent
    data object OnSendMessage : ChatUiEvent
    data object OnBackClicked : ChatUiEvent
    data object OnAttachClicked : ChatUiEvent
}

