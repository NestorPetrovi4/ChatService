fun main() {
    ChatService.addChat(700)
    ChatService.addChat(100)
    ChatService.addChat(800)

    //println(ChatService.deleteChat(4))

    ChatService.addMessage(2, "Привет")
    ChatService.addMessage(2, "Hi", true)
    ChatService.addMessage(2, "Как дела?")
    ChatService.addMessage(1, "Hello", true)

    ChatService.editMessage(2, "было Hi стало Привет")
    ChatService.addMessage(2, "Все хорошо")
    println(ChatService.deleteMessage(5))

    ChatService.readMessage(4)
    println("Количество непрочитанных чатов: " + ChatService.getUnreadChatsCount())
    println(ChatService.getChats())
    println("Последние сообщения в чатах: \n" + ChatService.getLastMessageChat())

    println(ChatService.getMessageFilter(100))
    println("Количество непрочитанных чатов: " + ChatService.getUnreadChatsCount())

    ChatService.addMessage(text = "Сообщение без чата", idCompanion = 900)
    println("Последние сообщения в чатах: \n" + ChatService.getLastMessageChat())

    val chat: Chat? = Chat(1, 2,3)
    val title = chat!!.id


}