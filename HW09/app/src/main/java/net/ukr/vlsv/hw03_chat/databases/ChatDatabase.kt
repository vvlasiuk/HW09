package net.ukr.vlsv.hw03_chat.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.ukr.vlsv.hw03_chat.daos.MessageTableDao
import net.ukr.vlsv.hw03_chat.daos.UserTableDao
import net.ukr.vlsv.hw03_chat.entities.MessageTable
import net.ukr.vlsv.hw03_chat.entities.UserTable

@Database(entities = [MessageTable::class, UserTable::class], version = 11)
abstract class ChatDatabase : RoomDatabase() {

    abstract fun messageDao(): MessageTableDao
    abstract fun userDao(): UserTableDao

    companion object {
        private var INSTANCE: ChatDatabase? = null

        fun getDatabase(
            context: Context, scope: CoroutineScope
        ): ChatDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ChatDatabase::class.java,
                    "chat_db"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(ChatDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class ChatDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        createUsers(database.userDao())
                    }
                }
            }
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
//                        populateUsers(database.userDao())
                    }
                }
            }
        }
        fun createUsers(userDao: UserTableDao) {
            userDao.deleteAll()
            userDao.insert(UserTable("1", "Petya"))
            userDao.insert(UserTable("2", "Vasya"))
        }
    }
}
