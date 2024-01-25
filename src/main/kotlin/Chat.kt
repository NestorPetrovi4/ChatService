data class Chat(
    val id: Int,
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

object ChatService {

}