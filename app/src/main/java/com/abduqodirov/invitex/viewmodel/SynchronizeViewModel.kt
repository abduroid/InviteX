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

class SynchronizeViewModel(val database: MehmonDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    private val JUST_FINISHED_VALUE: Int = 18
    val username = MutableLiveData<String>()
    val userEnteredWeddingId = MutableLiveData<String>()

    val uploadingProgress = MutableLiveData<Int>()
    var sizeToBeUploaded = 0

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var sharedPreferences =
        application.getSharedPreferences("keyim", Context.MODE_PRIVATE)


    fun createNewFirestoreDatabase() {

        val cardAmount = sharedPreferences.getInt("cardAmount", 0)

        //TODO username va wedding id ni trim qilib spacelarni olib tashlash kk

        CloudFirestoreRepo.initializeFireStore(cardAmount, username.value!!,
            ConnectedClickListener {
                persistString("userEnteredWeddingId", it)
                persistString("username", username.value!!)

                uploadOfflineMehmons()
            })

        //TODO userEnteredWeddingId docga listener qo'yib yangi member qo'shilganda uni sharedprefdagi membersga qo'shish
        //TODO va singlelistviewmodelda uni mehmonlariga listener qo'yish

    }

    fun joinToExistingDatabase() {

        //TODO Ulanganda WeddingIdni textViewga chiqarib qo'ymayapti faqat qayta kirganda initdan qo'yilyabdi
        CloudFirestoreRepo.joinToExistingWedding(username.value!!, userEnteredWeddingId.value!!,
            ConnectedClickListener {
                Log.i("tek", "Callback ishga tushdi")

                persistString("userEnteredWeddingId", userEnteredWeddingId.value!!)
                persistString("username", username.value!!)

                uploadOfflineMehmons()
            })

    }


    private fun uploadOfflineMehmons() {
        Log.i("tek", "Offline mehmonlarni jo'nataman hozir")
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                //TODO uyoqda band bo'lgan IDlarga bu mehmonlarni overwrite qivoryapti
                val listMehmon = database.getAllMehmons()

                sizeToBeUploaded = listMehmon.size - 1

                for (item in listMehmon) {
                    CloudFirestoreRepo.sendToFireStore(item)
                    //TODO Progressss tugamasidan back bosilsa dialog ko'rsatish kerak
                    uploadingProgress.postValue(listMehmon.indexOf(item))
                }

                sizeToBeUploaded = JUST_FINISHED_VALUE
                uploadingProgress.postValue(JUST_FINISHED_VALUE)

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