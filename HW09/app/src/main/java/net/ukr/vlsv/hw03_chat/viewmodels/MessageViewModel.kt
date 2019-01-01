package net.ukr.vlsv.hw03_chat.viewmodels

import android.app.Application
import android.text.Editable
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import net.ukr.vlsv.hw03_chat.R
import net.ukr.vlsv.hw03_chat.adapter.RecyclerAdapter
import net.ukr.vlsv.hw03_chat.daos.UserTableDao
import net.ukr.vlsv.hw03_chat.data.CountUserMessages
import net.ukr.vlsv.hw03_chat.data.NameUsers
import net.ukr.vlsv.hw03_chat.databases.ChatDatabase
import net.ukr.vlsv.hw03_chat.entities.MessageTable
import net.ukr.vlsv.hw03_chat.entities.UserTable
import net.ukr.vlsv.hw03_chat.repositories.MessageRepository
import net.ukr.vlsv.hw03_chat.repositories.UserRepository
import java.util.*
import kotlin.coroutines.CoroutineContext

class MessageViewModel(application: Application): BaseViewModel(application) {
    lateinit var chatAdapter: RecyclerAdapter

    private val messageRepository: MessageRepository
    private val userRepository: UserRepository

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)
    lateinit private var chatDB: ChatDatabase
    lateinit private var usersDao: UserTableDao

    init {
        chatDB = ChatDatabase.getDatabase(application, scope)

        val messagesDao = ChatDatabase.getDatabase(application, scope).messageDao()
        messageRepository = MessageRepository(messagesDao)

        usersDao = ChatDatabase.getDatabase(application, scope).userDao()
        userRepository = UserRepository(usersDao)

        initAdapter()
    }

    fun initAdapter() {
        chatAdapter = RecyclerAdapter(messageRepository.getAllMessageList())

        val countUserMessages: CountUserMessages
        val nameUsers :NameUsers

        val listCountUserMessages = messageRepository.getCountUserMessages("1", "2")
        var listNameUser = userRepository.getNameUsers("1", "2")

        if (listNameUser[0].NameUser_1 == null) {
            Thread.sleep(1000) //
            listNameUser = userRepository.getNameUsers("1", "2")
        }

        chatAdapter.COUNT_USER_MESSAGE = listCountUserMessages[0]

        chatAdapter.NAME_USERS = listNameUser[0]

        chatAdapter.setCountUser()
    }


    fun onOK_ButtonClicked(textEditable: Editable, itUser1: Boolean) {
        val userID: String
        val textMessage = textEditable.toString()

        if (textMessage == "") return

        textEditable.clear()

        if (itUser1) userID = "1" else userID = "2"

        addMessage(MessageTable(Calendar.getInstance().timeInMillis, userID, textMessage))
    }

    fun addMessage(message: MessageTable) = scope.launch(Dispatchers.IO) {
        chatAdapter.addMessage(message)

        messageRepository.insert(message)
    }
}