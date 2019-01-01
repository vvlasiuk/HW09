package net.ukr.vlsv.hw03_chat.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Message")
data class MessageTable(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "userId") val userId: String,
    @ColumnInfo(name = "text") val text: String
)
