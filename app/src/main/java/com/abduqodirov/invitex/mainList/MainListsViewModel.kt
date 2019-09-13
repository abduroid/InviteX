package com.abduqodirov.invitex.mainList

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abduqodirov.invitex.database.Mehmon
import com.abduqodirov.invitex.database.MehmonDatabaseDao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*

class MainListsViewModel(
    val database: MehmonDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private val db = FirebaseFirestore.getInstance()

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val ism_ed = MutableLiveData<String>()

    //TODO fix it, I mean null-safety
    private var userId: String = FirebaseAuth.getInstance().currentUser?.uid ?: "jkhh"

    fun specificMehmons(toifa: String) = database.getSpecificMehmons(toifa = toifa)
    val tanlanganTab = MutableLiveData<String>()


    private val _dialogState = MutableLiveData<Boolean>()
    val dialogState: LiveData<Boolean>
        get() = _dialogState

    private val weddingRef = MutableLiveData<String>()

    fun closeDialog() {
        _dialogState.value = false
        ism_ed.value = ""
    }

    fun onClickAdd() {
        val mehmon = Mehmon(ism = ism_ed.value!!, toifa = tanlanganTab.value!!)
        uiScope.launch {
            insert(mehmon)
            db.collection("weddings/${weddingRef.value}/${mehmon.toifa}")
                .add(
                    hashMapOf(
                        "ism" to mehmon.ism,
                        "added_by" to userId
                    )
                )
                .addOnSuccessListener {
                    Log.i("db", "databasega mehmon jo'natildi")
                }
                .addOnFailureListener {
                    Log.i("db", "databasega mehmonni jo'natishda xatolik")
                }
            closeDialog()
        }
    }

    init {
        val sharedPreferences =
            getApplication<Application>().getSharedPreferences("keyim", Context.MODE_PRIVATE)
        Log.i("viewmodel", "init")

        if (sharedPreferences.getBoolean("isFirstLaunch", true)) {
            initializeFirestore()

            with(sharedPreferences.edit()) {
                putBoolean("isFirstLaunch", false)
                putString("weddingPath", weddingRef.value)
                commit()
            }
        } else {
            weddingRef.value = sharedPreferences.getString("weddingPath", "newId")
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

    fun initializeFirestore() {
        //TODO(don't do that)
        val toy = hashMapOf(
            "card_amout" to 1899, //I need to get this data from user input
            "owners" to arrayListOf(
                userId
            )
        )

        val weddingDocumentRef = db.collection("weddings")
            .add(toy)
            .addOnSuccessListener { documentReference ->
                weddingRef.value = documentReference.id

                Log.i("db", "yozdim bazaga")

            }
            .addOnFailureListener {
                Log.i("db", "yozilmadi bazaga")
            }

    }

}