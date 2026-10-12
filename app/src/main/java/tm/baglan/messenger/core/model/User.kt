package tm.baglan.messenger.core.model

data class User(
    val id: Long,
    val name: String,
    val avatarUrl: String? = null,
    val isOnline: Boolean = false,
    val lastSeenText: String = ""
)

