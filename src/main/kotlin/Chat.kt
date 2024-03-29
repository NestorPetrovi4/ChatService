data class Chat(
    val id: Int,
    val idCompanion: Int,
    val date: Long
) : Comparable<Chat> {
    override fun compareTo(other: Chat) = this.id - other.id
}

data class Message(
    val id: Int,
    val chatId: Int,
    var message: String,
    val incoming: Boolean,
    val date: Long,
    var read: Boolean = false
) : Comparable<Message> {
    override fun compareTo(other: Message) = (this.date - other.date).toInt()
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

    fun clear() {
        lastId = 0
        lastIdMessage = 0
        chats.clear()
        messagesChat.clear()
    }

    fun addChat(
        idCompanion: Int
    ): Int {
        if (idCompanion == 0) throw ChatNotFoundException("Не указан id собеседника")
        lastId++
        addChatMessage(lastId, Chat(lastId, idCompanion, System.currentTimeMillis()), chats)
        addChatMessage(lastId, messagesChat.getOrDefault(lastId, mutableMapOf<Int, Message>()), messagesChat)
        return lastId
    }

    fun deleteChat(idChat: Int): Boolean {
        messagesChat.remove(idChat)
        return chats.remove(idChat) != null
    }

    private fun <T, E> MutableMap<T, E>.getList(): MutableList<E> {
        val list = mutableListOf<E>()
        for (chat in this) {
            list += chat.value
        }
        return list
    }

    fun getChats() = chats.getList()

    fun addMessage(idChat: Int = 0, text: String, incoming: Boolean = false, idCompanion: Int = -1): Int {
        if (chats.get(idChat) == null && idCompanion == -1) {
            throw ChatNotFoundException("не найден чат с id= $idChat")
        }
        lastIdMessage++
        val idChatSave = if (idChat == 0) addChat(idCompanion) else idChat
        val saveMessage = messagesChat.getOrDefault(idChatSave, mutableMapOf<Int, Message>())
        addChatMessage(
            lastIdMessage,
            Message(lastIdMessage, idChatSave, text, incoming, System.currentTimeMillis()),
            saveMessage
        )
        addChatMessage(idChatSave, saveMessage, messagesChat)
        return lastIdMessage
    }

    fun editMessage(idMessage: Int, text: String): Boolean {
        messagesChat.forEach { (_, messChats) ->
            messChats.forEach { (k, message) ->
                if (k == idMessage) {
                    message.message = text
                    return true
                }
            }
        }
        return false
    }

    fun deleteMessage(idMessage: Int): Boolean {
        messagesChat.forEach { (_, messChats) ->
            messChats.forEach { (k, message) ->
                if (k == idMessage) {
                    messChats.remove(idMessage)
                    return true
                }
            }
        }
        return false
    }

    fun readMessage(idMessage: Int) {
        messagesChat.toList().toList()
        messagesChat.forEach { (_, messChats) ->
            messChats.forEach { (k, message) ->
                if (k == idMessage) {
                    message.read = true
                }
            }
        }
    }

    fun getUnreadChatsCount() = messagesChat.count() { it.value.filter { !it.value.read }.isNotEmpty() }

    fun getLastMessageChat(): String {
        val stringBuilder = StringBuilder()
        messagesChat.forEach { (k, v) ->
            stringBuilder.append(
                "чат с пользователем " + (chats[k]?.idCompanion ?: "Пустой чат") + " " + if (v.isNotEmpty()) v.getList()
                    .sorted()
                    .last().message + "\n" else "Нет сообщений\n"
            )
        }
        return stringBuilder.toString()
    }

    fun getMessageFilter(idCompanion: Int, countMessage: Int = 0): List<Message> {
        var list = mutableListOf<Message>()
        messagesChat.filter { (k, _) -> chats[k]?.idCompanion ?: 0 == idCompanion }
            .forEach { (k, v) -> list.addAll(v.getList()) }
        list = if (countMessage != 0) list.subList(0, countMessage) else list
        list.forEach { it.read = true }
        return list
    }
}