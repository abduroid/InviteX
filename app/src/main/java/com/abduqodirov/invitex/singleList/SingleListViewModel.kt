package com.abduqodirov.invitex.singleList

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abduqodirov.invitex.database.Mehmon
import com.abduqodirov.invitex.database.MehmonDatabaseDao
import com.abduqodirov.invitex.firebase.FireStoreManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*

class SingleListViewModel(
    val database: MehmonDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private val db = FirebaseFirestore.getInstance()

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var toifa = ""

    //Use another data structure to increase performance
    fun spesificMehmons(toifa: String): LiveData<List<Mehmon>>{
        this.toifa = toifa
        return database.getSpecificMehmons(toifa = toifa)
    }

    val ism = MutableLiveData<String>()

    fun addMehmon() {
        val mehmon = Mehmon(ism = ism.value!!, toifa = toifa)
        var tMehmon: Mehmon
        uiScope.launch {
            val tId = insertToRoom(mehmon)
            tMehmon = Mehmon(
                mehmonId = tId,
                ism = mehmon.ism,
                toifa = mehmon.toifa,
                isAytilgan = mehmon.isAytilgan
            )
            FireStoreManager.sendToFirestore(db, tMehmon)
            ism.value = ""
        }
    }

    fun onMehmonChecked(oldMehmon: Mehmon) {
        uiScope.launch {
            val newMehmon = Mehmon(
                mehmonId = oldMehmon.mehmonId,
                ism = oldMehmon.ism,
                toifa = oldMehmon.toifa,
                //Anyway changes its previous state.
                isAytilgan = !oldMehmon.isAytilgan
            )
            updateOnRoom(newMehmon)
            FireStoreManager.updateItem(db, newMehmon)
        }
    }

    private suspend fun insertToRoom(mehmon: Mehmon): Long {
        var mId = 0L
        withContext(Dispatchers.IO) {
            mId = database.insert(mehmon)
        }
        return mId
    }

    private suspend fun updateOnRoom(mehmon: Mehmon) {
        withContext(Dispatchers.IO) {
            database.update(mehmon)
        }
    }
}
