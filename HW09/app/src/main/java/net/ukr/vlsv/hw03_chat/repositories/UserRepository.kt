package net.ukr.vlsv.hw03_chat.repositories

import androidx.annotation.WorkerThread
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import net.ukr.vlsv.hw03_chat.daos.UserTableDao
import net.ukr.vlsv.hw03_chat.data.NameUsers
import net.ukr.vlsv.hw03_chat.entities.UserTable
import kotlin.coroutines.CoroutineContext

class UserRepository(private val userDao: UserTableDao): CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO
//    val allUser: LiveData<List<UsersTable>> = userDao.getAllUser()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(user: UserTable) {
        userDao.deleteUser(user.id)
        userDao.insert(user)
    }

    fun getUserList(id: String): List<UserTable> = runBlocking(coroutineContext){
        userDao.getUserList(id)
    }

    fun getAllUserList(): List<UserTable> = runBlocking(coroutineContext){
        userDao.getAllUserList()
    }

    fun getNameUsers(user1_ID: String, user2_ID: String): MutableList<NameUsers> = runBlocking (coroutineContext){
        userDao.getNameUsers(user1_ID, user2_ID)
    }
}
