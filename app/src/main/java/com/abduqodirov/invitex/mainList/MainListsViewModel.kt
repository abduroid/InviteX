package com.abduqodirov.invitex.mainList

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abduqodirov.invitex.database.Mehmon
import com.abduqodirov.invitex.database.MehmonDatabaseDao
import kotlinx.coroutines.*

class MainListsViewModel(
    val database: MehmonDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val mehmons = database.getAllMehmons()

    fun specificMehmons(toifa: String) = database.getSpecificMehmons(toifa)

    val ism_ed = MutableLiveData<String>()

    private val _showDialogEvent = MutableLiveData<Boolean>()
    val showDialogEvent: LiveData<Boolean>
        get() = _showDialogEvent

    private fun doneShowingDialog() {
        ism_ed.value = ""
        _showDialogEvent.value = false
        Log.i("dialog", "false qildim")
    }

    fun onClickAdd() {
        uiScope.launch {
            //TODO buni hali to'g'rilash kerak, hozir tekshirvolaveray
            //TODO bu yerda kobra effekti ketib qolyabdi, toifani statik kiritib emas, userdan so'rab qo'yish kerak
            val mehmon = Mehmon(ism = ism_ed.value!!, toifa = "sinfdoshlar")

            insert(mehmon)
            doneShowingDialog()
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