package com.abduqodirov.invitex.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.abduqodirov.invitex.models.Mehmon

@Dao
interface MehmonDatabaseDao {

    @Insert
    fun insert(mehmon: Mehmon): Long

    @Update
    fun update(mehmon: Mehmon)

    @Query("SELECT * FROM mehmonlar WHERE mehmonId = :key")
    fun get(key: Long): Mehmon?

    @Query("SELECT * FROM mehmonlar ORDER BY ism")
    fun getAllMehmons(): List<Mehmon>

    @Query("SELECT * FROM mehmonlar WHERE toifa =  :toifa ORDER BY mehmonId DESC")
    fun getSpecificMehmons(toifa: String): LiveData<List<Mehmon>>

    @Query("SELECT * FROM mehmonlar WHERE ism = :searchQuery ORDER BY ism")
    fun getSearchResults(searchQuery: String): LiveData<List<Mehmon>>

    @Delete
    fun deleteMehmon(mehmon: Mehmon)
}