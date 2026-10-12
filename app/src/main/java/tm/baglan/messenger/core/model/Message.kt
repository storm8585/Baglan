package tm.baglan.messenger.core.model

data class Message(
    val id: Long,
    val senderId: Long,
    val text: String,
    val timestamp: Long,
    val isOutgoing: Boolean,
    val status: MessageStatus = MessageStatus.SENT,
    val isTopNear: Boolean = false,
    val isBottomNear: Boolean = false
)

