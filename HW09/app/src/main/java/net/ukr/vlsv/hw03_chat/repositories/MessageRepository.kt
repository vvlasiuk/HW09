package net.ukr.vlsv.hw03_chat.repositories

import androidx.annotation.WorkerThread
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import net.ukr.vlsv.hw03_chat.daos.MessageTableDao
import net.ukr.vlsv.hw03_chat.data.CountUserMessages
import net.ukr.vlsv.hw03_chat.data.NameUsers
import net.ukr.vlsv.hw03_chat.entities.MessageTable
import kotlin.coroutines.CoroutineContext


class MessageRepository(private val messageDao: MessageTableDao): CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(message: MessageTable) {
        messageDao.insert(message)
    }

    fun getAllMessageList(): MutableList<MessageTable> = runBlocking (coroutineContext){
        messageDao.getAllMessageList()
    }

    fun getCountUserMessages(user1_ID: String, user2_ID: String): MutableList<CountUserMessages> = runBlocking (coroutineContext){
        messageDao.getCountUserMessages(user1_ID, user2_ID)
    }
}
