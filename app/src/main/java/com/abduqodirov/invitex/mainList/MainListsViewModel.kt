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

    val ism_ed = MutableLiveData<String>()

    private val _showDialogEvent = MutableLiveData<Boolean>()
    val showDialogEvent: LiveData<Boolean>
        get() = _showDialogEvent

    fun doneShowingDialog() {
        ism_ed.value = ""
        _showDialogEvent.value = false
        Log.i("dialog", "false qildim")
    }

    fun onClickAdd() {
//        _showDialogEvent.value = true
        uiScope.launch {
            //TODO buni hali to'g'rilash kerak, hozir tekshirvolaveray
            val mehmon = Mehmon(ism = ism_ed.value!!)

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