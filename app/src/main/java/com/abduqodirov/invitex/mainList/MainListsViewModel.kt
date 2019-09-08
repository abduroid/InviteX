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

//    val mehmons = database.getAllMehmons()

    val ism_ed = MutableLiveData<String>()

    fun specificMehmons(toifa: String) = database.getSpecificMehmons(toifa)

    val tanlanganTab = MutableLiveData<String>()

    private val _dialogState = MutableLiveData<Boolean>()
    val dialogState: LiveData<Boolean>
        get() = _dialogState

    fun closeDialog() {
        _dialogState.value = false
        ism_ed.value = ""
        Log.i("dialog", "Dialog is now ${_dialogState.value.toString()}")
    }

    fun onClickAdd() {

//        Log.d("TESTAG","ism_ed.value->${ism_ed.value}  tanlanganTab.value->${tanlanganTab.value}")
        val mehmon = Mehmon(ism = ism_ed.value!!, toifa = tanlanganTab.value!!)
        uiScope.launch {

            //TODO kobra effekti
            insert(mehmon)
            closeDialog()
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