package com.abduqodirov.invitex.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.abduqodirov.invitex.database.MehmonDatabaseDao
import com.abduqodirov.invitex.firestore.ConnectedClickListener
import com.abduqodirov.invitex.firestore.CloudFirestoreRepo
import kotlinx.coroutines.*

class SynchronizeFragmentViewModel(val database: MehmonDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    val username = MutableLiveData<String>()
    val weddingId = MutableLiveData<String>()

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var sharedPreferences =
        application.getSharedPreferences("keyim", Context.MODE_PRIVATE)

    fun createNewFirestoreDatabase() {

        val cardAmount = sharedPreferences.getInt("cardAmount", 0)

        CloudFirestoreRepo.initializeFireStore(cardAmount, username.value!!,
            ConnectedClickListener {
                persistString("weddingId", it)
                persistString("username", username.value!!)

                uploadOfflineMehmons()
            })

        with(sharedPreferences.edit()) {
            putStringSet("members", mutableSetOf(username.value!!))
            commit()
        }
        //TODO weddingId docga listener qo'yib yangi member qo'shilganda uni sharedprefdagi membersga qo'shish
        //TODO va singlelistviewmodelda uni mehmonlariga listener qo'yish

    }

    fun joinToExistingDatabase() {

        CloudFirestoreRepo.joinToExistingWedding(username.value!!, weddingId.value!!,
            ConnectedClickListener {
                uploadOfflineMehmons()
            })
        persistString("weddingId", weddingId.value!!)
        persistString("username", username.value!!)

    }

    private fun uploadOfflineMehmons() {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                //TODO uyoqda band bo'lgan IDlarga bu mehmonlarni overwrite qivoryapti
                val listMehmon = database.getAllMehmons()
                for (item in listMehmon) {
                    CloudFirestoreRepo.sendToFireStore(item)
                }
            }
        }

    }

    private fun persistString(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }
}