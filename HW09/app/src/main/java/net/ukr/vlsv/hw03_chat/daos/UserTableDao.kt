package net.ukr.vlsv.hw03_chat.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import net.ukr.vlsv.hw03_chat.data.NameUsers
import net.ukr.vlsv.hw03_chat.entities.UserTable


@Dao
interface UserTableDao {
    @Query("SELECT * from User ORDER BY id ASC")
    fun getAllUser(): LiveData<List<UserTable>>

    @Query("SELECT * from User ORDER BY id ASC")
    fun getAllUserList():  List<UserTable>

    @Query("SELECT * from User WHERE id = :id")
    fun getUserList(id: String):  List<UserTable>

    @Query("SELECT " +
            "MAX(CASE WHEN id=:user1_ID THEN name ELSE NULL END) AS NameUser_1, " +
            "MAX(CASE WHEN id=:user2_ID THEN name ELSE NULL END) AS NameUser_2 " +
            "FROM User")
    fun getNameUsers(user1_ID: String, user2_ID: String):  MutableList<NameUsers>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userTable: UserTable)

    @Query("DELETE FROM User")
    fun deleteAll()

    @Query("DELETE FROM User WHERE id = :id")
    fun deleteUser(id: String)
}