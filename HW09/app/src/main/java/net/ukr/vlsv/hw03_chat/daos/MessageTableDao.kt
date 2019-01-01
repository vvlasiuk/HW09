package net.ukr.vlsv.hw03_chat.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import net.ukr.vlsv.hw03_chat.data.CountUserMessages
import net.ukr.vlsv.hw03_chat.entities.MessageTable


@Dao
interface MessageTableDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(messageTable: MessageTable)

    @Query("SELECT * from Message ORDER BY id ASC")
    fun getAllMessage():  LiveData<List<MessageTable>>

    @Query("SELECT 0 AS id, '0' AS userId, 'count' AS text UNION ALL SELECT * FROM Message ORDER BY id ASC")
    fun getAllMessageList():  MutableList<MessageTable>

    @Query("SELECT " +
            "SUM(CASE WHEN userId=:user1_ID THEN 1 ELSE 0 END) AS CountUser_1, " +
            "SUM(CASE WHEN userId=:user2_ID THEN 1 ELSE 0 END) AS CountUser_2 " +
            "FROM Message")
    fun getCountUserMessages(user1_ID: String, user2_ID: String):  MutableList<CountUserMessages>


    @Query("DELETE FROM Message")
    fun deleteAll()
}

