package com.abduqodirov.invitex.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MehmonDatabaseDao {

    @Insert
    fun insert(mehmon: Mehmon)

    @Query("SELECT * FROM mehmonlar WHERE mehmonId = :key")
    fun get(key: Long): Mehmon?

    @Query("DELETE FROM mehmonlar")
    fun clear()

    @Query("SELECT * FROM mehmonlar ORDER BY mehmonId DESC")
    fun getAllMehmons(): LiveData<List<Mehmon>>

    @Query("SELECT * FROM mehmonlar WHERE toifa =  :toifa")
    fun getSpecificMehmons(toifa: String): LiveData<List<Mehmon>>

}