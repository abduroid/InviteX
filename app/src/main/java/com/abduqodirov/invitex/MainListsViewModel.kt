package com.abduqodirov.invitex

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.abduqodirov.invitex.database.Mehmon
import com.abduqodirov.invitex.database.MehmonDatabaseDao
import kotlinx.coroutines.*

class MainListsViewModel(
    val database: MehmonDatabaseDao,
    application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val mehmons = database.getAllMehmons()

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    private suspend fun update(mehmon: Mehmon) {
        withContext(Dispatchers.IO) {
            database.update(mehmon = mehmon)
        }
    }

    private suspend fun insert(mehmon: Mehmon) {
        withContext(Dispatchers.IO) {
            database.insert(mehmon = mehmon)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}