import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class ChatServiceTest {
    @Before
    fun clearBeforeTest() {
        ChatService.clear()
    }

    @Test
    fun addChat() {
        assertEquals(ChatService.addChat(100), 1)
    }

    @Test(expected = ChatNotFoundException::class)
    fun addChatNotFoundCompanion() {
        ChatService.addChat(0)
    }

    @Test
    fun deleteChat() {
        ChatService.addChat(100)
        ChatService.addChat(200)
        ChatService.addChat(300)
        val countStart = ChatService.getChats().count()
        ChatService.deleteChat(1)
        val countFinish = ChatService.getChats().count()
        assertEquals(true, countStart - countFinish == 1)
    }

    @Test
    fun deleteMessage() {
        ChatService.addChat(100)
        ChatService.addChat(200)
        ChatService.addChat(300)
        ChatService.addMessage(1, "Первое сообщение")
        ChatService.addMessage(1, "Второе сообщение")
        ChatService.addMessage(1, "Третье сообщение")
        val countStart = ChatService.getMessageFilter(100).count()
        ChatService.deleteMessage(2)
        val countFinish = ChatService.getMessageFilter(100).count()
        assertEquals(true, countStart - countFinish == 1)
    }

    @Test
    fun getUnreadMessage() {
        ChatService.addChat(100)
        ChatService.addChat(200)
        ChatService.addChat(300)

        ChatService.addMessage(1, "Первая запись")
        ChatService.addMessage(1, "Вторая запись")
        ChatService.addMessage(1, "Третья запись")

        assertEquals(ChatService.getUnreadChatsCount(), 1)
    }

    @Test
    fun getLastMessageChat(){
        ChatService.addChat(100)
        ChatService.addChat(200)

        ChatService.addMessage(1, "Первая запись")
        ChatService.addMessage(1, "Вторая запись")
        ChatService.addMessage(1, "Третья запись")
        println(ChatService.getLastMessageChat())
        assertEquals(ChatService.getLastMessageChat(), "чат с пользователем 100 Третья запись\nчат с пользователем 200 Нет сообщений\n")
    }
    @Test
    fun getMessageFilter(){
        ChatService.addChat(100)
        ChatService.addChat(200)

        ChatService.addMessage(1, "Первая запись")
        ChatService.addMessage(1, "Вторая запись")
        ChatService.addMessage(1, "Третья запись")

        ChatService.deleteMessage(1)
        ChatService.deleteMessage(2)
        ChatService.deleteMessage(3)

        assertEquals(ChatService.getMessageFilter(100), mutableListOf<Message>())
    }
}