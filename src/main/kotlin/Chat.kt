import java.lang.RuntimeException

data class Chat(
    val id: Int,
    val idCompanion: Int,
    val date: Long
)

data class Message(
    val id: Int,
    val chatId: Int,
    var message: String,
    val incoming: Boolean,
    val date: Long
) : Comparable<Message> {
    override fun compareTo(other: Message): Int {
        return (this.date - other.date).toInt()
    }
}

class ChatNotFoundException(message: String) : RuntimeException(message)

object ChatService {
    private val chats = mutableMapOf<Int, Chat>()
    private val messagesChat = mutableMapOf<Int, MutableMap<Int, Message>>()
    private var lastId: Int = 0
    private var lastIdMessage: Int = 0

    private fun <I, V> addChatMessage(ind: I, value: V, collect: MutableMap<I, V>) {
        collect[ind] = value
    }

    fun addChat(
        idCompanion: Int): Int {
        lastId ++
        addChatMessage(lastId, Chat(lastId, idCompanion, System.currentTimeMillis()), chats)
        return lastId
    }
}