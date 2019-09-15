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

//    //TODO fix it, I mean null-safety
//    private var userId: String = FirebaseAuth.getInstance().currentUser?.uid ?: "offlineUser"
//    private val mDdoc = FirebaseFirestore.getInstance().collection("weddings")
//    private val weddingRef = MutableLiveData<String>()


        fun specificMehmons(toifa: String) = database.getSpecificMehmons(toifa = toifa)
//    fun specificMehmons(toifa: String): LiveData<List<Mehmon>> {
//        mDdoc.document(weddingRef.value!!).collection(toifa).addSnapshotListener { querySnapshot, firebaseFirestoreException ->
//
//        }
//    }

    init {
        firstLaunchTasks()
    }

//    private fun initializeFirestore() {
//
//
//        //TODO(don't do that)
//        val toy = hashMapOf(
//            "card_amout" to 1899, //I need to get this data from user input
//            "owners" to arrayListOf(
//                userId
//            )
//        )
//
//        mDdoc
//            .add(toy)
//            .addOnSuccessListener { documentReference ->
//                weddingRef.value = documentReference.id
//                Log.i("db", "yozdim bazaga")
//                firstLaunchTasks()
//
//            }
//            .addOnFailureListener {
//                Log.i("db", "yozilmadi bazaga")
//            }
//
//
//    }

    private fun firstLaunchTasks() {
        val sharedPreferences =
            getApplication<Application>().getSharedPreferences("keyim", Context.MODE_PRIVATE)

        if (sharedPreferences.getBoolean("isFirstLaunch", true)) {

            with(sharedPreferences.edit()) {
                putBoolean("isFirstLaunch", false)
//                putString("weddingId", weddingRef.value)
                commit()

            }


        } else {
//            weddingRef.value = sharedPreferences.getString("weddingId", "newId")
        }
    }
}
