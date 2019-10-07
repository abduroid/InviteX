package com.abduqodirov.invitex

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.abduqodirov.invitex.database.MehmonDatabase
import com.abduqodirov.invitex.database.MehmonDatabaseDao
import com.abduqodirov.invitex.firebase.AddedClickListener
import com.abduqodirov.invitex.firebase.FireStoreManager
import com.abduqodirov.invitex.singleList.SingleListViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_synchronize.*
import kotlinx.coroutines.*

class SynchronizeFragment : Fragment(R.layout.fragment_synchronize) {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var dataSource: MehmonDatabaseDao

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val weddingsCollection by lazy {
        FirebaseFirestore.getInstance()
            .collection("weddings")
    }
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences =
            activity!!.getSharedPreferences("keyim", Context.MODE_PRIVATE)

        val application = requireNotNull(activity!!.application)

        dataSource = MehmonDatabase.getInstance(application).mehmonDatabaseDao

    }

    override fun onStart() {
        super.onStart()
        yangi_button.setOnClickListener {
            initializeFireStore()
        }

        ulanish_button.setOnClickListener {
            val weddingIdT = id_edit.text.toString()
            persistWeddingId(weddingIdT)
            FireStoreManager.weddingId = weddingIdT
        }
    }

    private fun uploadOfflineMehmons() {
        Log.i("hpo","chaqirildi eskilarni jo'natish")
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val listMehmon = dataSource.getAllMehmons()
                for (item in listMehmon) {
                    FireStoreManager.sendToFirestore(db = db, mehmon = item)
                }
            }
        }
    }

    private fun initializeFireStore() {

        if (!sharedPreferences.contains("weddingId")) {
            Log.i("hpo", "Ho'p mana wedding id tayinlanmagan ekan, shuni harakatimadiz")

            val cardAmount = sharedPreferences.getInt("cardAmount", 0)

            FireStoreManager.initializeFirestore(cardAmount, weddingsCollection,
                AddedClickListener { weddingId ->
                    persistWeddingId(weddingId)
                    //TODO mana shu joyda callback o'rniga Kotlin coroutines await ishlatish kerak
                })
        } else {
            FireStoreManager.weddingId = sharedPreferences.getString("weddingId", "jj")
            Log.i("hpo", "bor ekan")
        }
    }

    private fun persistWeddingId(weddingId: String) {
        with(sharedPreferences.edit()) {
            Log.i("hpo", "Sharedprefga yozmoqchiman")
            putString("weddingId", weddingId)
            Log.i("hpo", "yozib bo'ldim $weddingId ni")
            commit()
            uploadOfflineMehmons()
        }
    }
}
