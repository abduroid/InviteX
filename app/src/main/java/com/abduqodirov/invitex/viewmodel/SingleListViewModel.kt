package com.abduqodirov.invitex.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abduqodirov.invitex.database.Mehmon
import com.abduqodirov.invitex.database.MehmonDatabaseDao
import com.abduqodirov.invitex.firestore.CloudFirestoreRepo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges
import kotlinx.coroutines.*
import java.lang.IndexOutOfBoundsException

class SingleListViewModel(
    val database: MehmonDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val ism = MutableLiveData<String>()
    val toifa = MutableLiveData<String>()

    val toifaniBarchaMehmonlari = MutableLiveData<ArrayList<ArrayList<Mehmon>>>()
    var toifaniBarchaMehmonlariClassic: ArrayList<ArrayList<Mehmon>> = arrayListOf(arrayListOf())
    var localGuests: LiveData<List<Mehmon>> = MutableLiveData<List<Mehmon>>()

//    private val sharedPreferences = application.getSharedPreferences("keyim", Context.MODE_PRIVATE)

    init {

//        loadMembers()

        toifaniBarchaMehmonlari.value = arrayListOf(arrayListOf())
        toifaniBarchaMehmonlariClassic[0] = arrayListOf(Mehmon(ism = "fuck"))
        //TODO add toifa for fuck element
//        Log.i("initf", toifaniBarchaMehmonlariClassic[0][0].ism)
    }

//    fun loadMembers() {
//        val db = FirebaseFirestore.getInstance()
//        db.collection("weddings").document(CloudFirestoreRepo.weddingId)
//            .addSnapshotListener { querySnapshot, queryException ->
//                if (queryException != null) {
//                    Log.i("weddingM", "Listen failed.", queryException)
//                    return@addSnapshotListener
//                }
//
//                if (querySnapshot != null && querySnapshot.exists()) {
////                Log.i("weddingM", "Current data: ${querySnapshot.data}")
//
//                    val mapOfMembers: Map<String, Any> = querySnapshot.data!!.toSortedMap()
//                    for (member in mapOfMembers) {
//                        if (member.key == "members") {
//                            Log.i("weddingM", member.value)
//                        }
//                    }
//                } else {
//                    Log.i("weddingM", "Current data: null")
//                }
//
//            }
//    }

    fun localSpecificMehmons(toifa: String): LiveData<List<Mehmon>> {

        this.toifa.value = toifa
        localGuests = database.getSpecificMehmons(toifa)

        return database.getSpecificMehmons(toifa)

    }

    fun observeOfItsGuests(toifa: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("weddings").document(CloudFirestoreRepo.weddingId)
            .collection("${CloudFirestoreRepo.username}-$toifa")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->

                if (firebaseFirestoreException != null) {
                    Log.w("fire error", "Listen failed.", firebaseFirestoreException)
                    return@addSnapshotListener
                }
                val mehmons = ArrayList<Mehmon>()

                for (document in querySnapshot!!) {
                    val mehmon = document.toObject(Mehmon::class.java)
                    uiScope.launch {
                        updateOnRoom(mehmon)
                    }
                    Log.i("hjht", "$mehmon")
                }

            }
    }

    fun loadFirestoreMehmons(toifa: String, username: String) {
        getGuestsOfMember(username = username, toifa = toifa)
    }

    private fun getGuestsOfMember(
        username: String,
        toifa: String
    ): MutableLiveData<ArrayList<Mehmon>> {

        val mehmons = MutableLiveData<ArrayList<Mehmon>>()

        val db = FirebaseFirestore.getInstance()
        db.collection("weddings").document(CloudFirestoreRepo.weddingId)
            .collection("$username-$toifa")
            .addSnapshotListener(MetadataChanges.EXCLUDE) { querySnapshot, firebaseFirestoreException ->

                //                toifaniBarchaMehmonlariClassic.clear()

                if (firebaseFirestoreException != null) {
//                    Log.w("fire error", "Listen failed.", firebaseFirestoreException)
                    return@addSnapshotListener
                }
                val mehmonlar = ArrayList<Mehmon>()
                val mezbon = Mehmon(ism = username, toifa = "mezbon")
                mehmonlar.add(mezbon)


                for (document in querySnapshot!!) {
                    val mehmon = document.toObject(Mehmon::class.java)
                    Log.i("checkbox", "ViewModel Snapshot, $mehmon")
                    mehmonlar.add(mehmon)
                    Log.i("checkbox", "Arrayda ${mehmonlar.size} element bor")
                }

//
                //TODO bor elementlarni ham qo'shib tashlayapti. Tozalab olish kerak, yoki tekshirish kerak qo'shilayotkan element oldin yozilganmi yo'qmi

//                toifaniBarchaMehmonlariClassic[]
                for (mehmon in mehmonlar) {

                    var kerakliArray = 0
                    when (mehmon.toifa) {
                        "pixel" -> kerakliArray = 0
                        "iphone" -> kerakliArray = 1
                    }


                    val checkedVariant = Mehmon(
                        mehmonId = mehmon.mehmonId,
                        ism = mehmon.ism,
                        toifa = mehmon.toifa,
                        caller = mehmon.caller,
                        isAytilgan = true
                    )

                    val uncheckedVariant = Mehmon(
                        mehmonId = mehmon.mehmonId,
                        ism = mehmon.ism,
                        toifa = mehmon.toifa,
                        caller = mehmon.caller,
                        isAytilgan = false
                    )
                    if (toifaniBarchaMehmonlariClassic[kerakliArray].contains(checkedVariant) ||
                        toifaniBarchaMehmonlariClassic[kerakliArray].contains(uncheckedVariant)
                    ) {
                        toifaniBarchaMehmonlariClassic[kerakliArray].remove(checkedVariant)
                        toifaniBarchaMehmonlariClassic[kerakliArray].remove(uncheckedVariant)
                        toifaniBarchaMehmonlariClassic[kerakliArray].add(mehmon)
                    } else {
                        toifaniBarchaMehmonlariClassic[0].add(mehmon)
                    }

                }
                toifaniBarchaMehmonlari.value!![0] =
                    toifaniBarchaMehmonlariClassic[0]

                try {
                    toifaniBarchaMehmonlari.value!![1] = toifaniBarchaMehmonlariClassic[1]
                } catch (exception: IndexOutOfBoundsException) {
                    Log.i("checkbox", "array hasn't been initialized yet")
                }

                toifaniBarchaMehmonlari.postValue(toifaniBarchaMehmonlari.value)


                Log.i("ititi", "${toifaniBarchaMehmonlariClassic[0].size}")

            }


        return mehmons
    }

    fun observeItsGuests(toifa: String) {

    }

//    operator fun <T> MutableLiveData<MutableList<T>>.plusAssign(item: T) {
//        val value = this.value ?: mutableListOf()
//        value.add(item)
//        this.value = value
//    }

    //TODO firestoredan kelgan mehmonlarni ro'yxati uchun boshqa alohida mutablelivedata<list> yaratish
    //TODO ularni boshiga memberNameheader elementi qo'shish, usernameni ko'rsatish. Va recyclerviewda uni formatlab ko'rsatish va spinner qo'yish


    fun addLocalMehmon() {
        val mehmonForLocal = Mehmon(ism = ism.value!!, toifa = toifa.value!!)
        var mehmonForFirestore: Mehmon
        uiScope.launch {
            val localMehmonId = insertToRoom(mehmonForLocal)

            mehmonForFirestore = Mehmon(
                mehmonId = localMehmonId,
                ism = mehmonForLocal.ism,
                toifa = mehmonForLocal.toifa,
                isAytilgan = mehmonForLocal.isAytilgan
            )
            if (CloudFirestoreRepo.weddingId.isNotEmpty()) {
                CloudFirestoreRepo.sendToFireStore(mehmonForFirestore)
            }

            //clears edittext
            ism.value = ""
        }
    }

    fun onMehmonChecked(oldMehmon: Mehmon) {

        val newMehmon = Mehmon(
            mehmonId = oldMehmon.mehmonId,
            ism = oldMehmon.ism,
            toifa = oldMehmon.toifa,
            caller = oldMehmon.caller,
            //Anyway changes its previous state.
            isAytilgan = !oldMehmon.isAytilgan
        )
        if (oldMehmon.caller == "local") {
            uiScope.launch {
                updateOnRoom(newMehmon)
            }
        }
        if (CloudFirestoreRepo.weddingId.isNotEmpty()) {
            CloudFirestoreRepo.updateItemChecked(newMehmon)
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
