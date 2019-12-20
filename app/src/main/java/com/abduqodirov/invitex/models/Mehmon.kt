package com.abduqodirov.invitex.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mehmonlar")
data class Mehmon(

    @PrimaryKey(autoGenerate = true)
    var mehmonId: Long = 0L,

    @ColumnInfo(name = "caller")
    var caller: String = "local",

    @ColumnInfo(name = "ism")
    val ism: String = "",

    @ColumnInfo(name = "toifa")
    val toifa: String = "",

    @ColumnInfo(name = "isAytilgan")
    var isAytilgan: Boolean = false,

    @ColumnInfo(name = "isCollapsed")
    var isCollapsed: Boolean = false
)