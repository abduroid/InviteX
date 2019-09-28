package com.abduqodirov.invitex.singleList

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abduqodirov.invitex.database.Mehmon
import com.abduqodirov.invitex.database.MehmonDatabaseDao
import kotlinx.coroutines.*

class SingleListViewModel(
    val database: MehmonDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var toifa: String = ""

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    //Use another data structure to increase performance
    fun specificMehmons(toifa: String): LiveData<List<Mehmon>> {
        this.toifa = toifa
        return database.getSpecificMehmons(toifa = toifa)
    }

    val ism = MutableLiveData<String>()

    private suspend fun insert(mehmon: Mehmon) {
        withContext(Dispatchers.IO) {
            database.insert(mehmon)
        }
    }

    private suspend fun update(mehmon: Mehmon) {
        withContext(Dispatchers.IO) {
            database.update(mehmon)
        }
    }

    fun onMehmonChecked(mehmon: Mehmon) {
        uiScope.launch {
            //Anyway changes its previous state.
            update(Mehmon(mehmon.mehmonId, mehmon.ism, mehmon.toifa, !mehmon.isAytilgan))
        }
    }

    fun addMehmon() {
        val mehmon = Mehmon(ism = ism.value!!, toifa = toifa)
        uiScope.launch {
            insert(mehmon)
            ism.value = ""
        }
    }

}
