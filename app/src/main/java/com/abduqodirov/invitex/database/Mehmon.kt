package com.abduqodirov.invitex.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Mehmonlar")
data class Mehmon(

    @PrimaryKey(autoGenerate = true)
    var mehmonId: Long = 0L,

    @ColumnInfo(name = "ism")
    val ism: String = "",

    @ColumnInfo(name = "toifa")
    val toifa: String = ""

)