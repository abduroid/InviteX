package com.abduqodirov.invitex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abduqodirov.invitex.database.Mehmon
import com.abduqodirov.invitex.database.MehmonDatabaseDao
import kotlinx.coroutines.*

class CollectionListViewModel(
    val database: MehmonDatabaseDao
) : ViewModel() {

    val ism_ed = MutableLiveData<String>()
    val tanlanganTab = MutableLiveData<String>("sinfdoshlar")


    private var viewModelJob = Job()
    private val scope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _dialogState = MutableLiveData<Boolean>()
    val dialogState: LiveData<Boolean>
        get() = _dialogState

    fun closeDialog() {
        _dialogState.value = false
        ism_ed.value = ""
    }

    fun onClickAdd() {
        val mehmon = Mehmon(ism = ism_ed.value!!, toifa = tanlanganTab.value!!)
        scope.launch {
            insert(mehmon)
        }
        closeDialog()
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