package com.abduqodirov.invitex.viewmodel

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abduqodirov.invitex.MembersManager
import com.abduqodirov.invitex.models.Mehmon
import com.abduqodirov.invitex.database.MehmonDatabaseDao
import com.abduqodirov.invitex.firestore.CloudFirestoreRepo
import com.abduqodirov.invitex.firestore.CompletedClickListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges
import kotlinx.coroutines.*
import kotlin.collections.ArrayList

class SingleListViewModel(
    val database: MehmonDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val localFirstMembers = MutableLiveData<List<String>>()

    val ism = MutableLiveData<String>()
    val toifa = MutableLiveData<String>()

    val toifaniBarchaMehmonlari = MutableLiveData<ArrayList<List<Mehmon>>>()
    var toifaniBarchaMehmonlariClassic = arrayListOf<ArrayList<Mehmon>>(arrayListOf<Mehmon>())
    var localGuests: LiveData<List<Mehmon>> = MutableLiveData<List<Mehmon>>()

    var localSearchResults = MutableLiveData<ArrayList<Mehmon>>()

    var isLastItemLocal = MutableLiveData<Boolean>()

    init {

        toifaniBarchaMehmonlari.value = arrayListOf(arrayListOf())
//        toifaniBarchaMehmonlariClassic[0] = arrayListOf(Mehmon(ism = "fuck"))
        //TODO add toifa for fuck element
    }


    fun loadMembers() {

        if (CloudFirestoreRepo.isFirestoreConnected()) {

            val db = FirebaseFirestore.getInstance()

            db.collection("weddings").document(CloudFirestoreRepo.weddingId)
                .addSnapshotListener(MetadataChanges.EXCLUDE) { querySnapshot, queryException ->
                    if (queryException != null) {
                        Log.i("weddingM", "Listen failed.", queryException)
                        return@addSnapshotListener
                    }

                    if (querySnapshot != null && querySnapshot.exists()) {

                        val weddingDoc: Map<String, Any> = querySnapshot.data!!.toSortedMap()
                        val members: List<String> = weddingDoc["members"] as List<String>

                        val listWithoutLocalUsername =
                            members.filter { member -> !member.equals(CloudFirestoreRepo.username) }

                        //TODO duplicate code
                        val localMezbon = listOf(CloudFirestoreRepo.username)


                        val localFirstMembersClassic = localMezbon + listWithoutLocalUsername

                        localFirstMembers.postValue(localFirstMembersClassic)

                        for (membercha in localFirstMembersClassic) {
                            if (!MembersManager.members.containsKey(membercha)) {
                                MembersManager.members[membercha] =
                                    localFirstMembersClassic.indexOf(membercha)
                            }
                        }

                        Log.i("tek", "Ccompanioonga berilgani: ${MembersManager.members}")

                    } else {
                        Log.i("weddingM", "Current data: null")
                    }

                }

        }

    }

    fun loadSpecificMehmons(toifa: String): LiveData<List<Mehmon>> {

        this.toifa.value = toifa
        localGuests = database.getSpecificMehmons(toifa)

        return database.getSpecificMehmons(toifa)

    }

    fun setToifa(toifa: String) {
        this.toifa.value = toifa
    }

//    fun observeOfItsGuests(toifa: String) {
//
//        if (CloudFirestoreRepo.isFirestoreConnected()) {
//            val db = FirebaseFirestore.getInstance()
//            db.collection("weddings").document(CloudFirestoreRepo.weddingId)
//                .collection("${CloudFirestoreRepo.username}-$toifa")
//                .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
//
//                    if (firebaseFirestoreException != null) {
//                        Log.w("fire error", "Listen failed.", firebaseFirestoreException)
//                        return@addSnapshotListener
//                    }
//
//                    for (document in querySnapshot!!) {
//                        val rawMehmon = document.toObject(Mehmon::class.java)
//
//                        val collapsingAdaptedMehmon = Mehmon(
//                            ism = rawMehmon.ism,
//                            toifa = rawMehmon.toifa,
//                            caller = rawMehmon.caller,
//                            mehmonId = rawMehmon.mehmonId,
//                            isCollapsed = MembersManager.membersCollapsed.get(CloudFirestoreRepo.username)
//                                ?: false,
//                            isAytilgan = rawMehmon.isAytilgan
//                        )
//
//                        uiScope.launch {
//                            updateOnRoom(collapsingAdaptedMehmon)
//                        }
//                    }
//
//                }
//        }
//
//    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadFirestoreMehmons(toifa: String, username: String) {
        if (CloudFirestoreRepo.isFirestoreConnected()) {
            getGuestsOfMember(username = username, toifa = toifa)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getGuestsOfMember(
        username: String,
        toifa: String
    ): MutableLiveData<ArrayList<Mehmon>> {

        val mehmons = MutableLiveData<ArrayList<Mehmon>>()

        val db = FirebaseFirestore.getInstance()
        db.collection("weddings").document(CloudFirestoreRepo.weddingId)
            .collection("$username-$toifa")
            .addSnapshotListener(MetadataChanges.EXCLUDE) { querySnapshot, firebaseFirestoreException ->


                if (firebaseFirestoreException != null) {
                    Log.w("fire error", "Listen failed.", firebaseFirestoreException)
                    return@addSnapshotListener
                }
                val mehmonlar = ArrayList<Mehmon>()

                val mezbon = Mehmon(
                    ism = username,
                    toifa = "mezbon"
                )

                val kerakliArray = MembersManager.members.get(username)!! //TODO null check
                val emptyMehmon = Mehmon(
                    toifa = "empty",
                    caller = username,
                    ism = "Bo'mbo'sh"
                )

                //Initializes array to avoid IndexOutOfBoundException
                for (i in 1..kerakliArray) {
                    toifaniBarchaMehmonlariClassic.add(arrayListOf<Mehmon>())
                }

                for (document in querySnapshot!!) {
                    val rawMehmon = document.toObject(Mehmon::class.java)

                    val collapseAdaptedMehmon = Mehmon(
                        ism = rawMehmon.ism,
                        isCollapsed = MembersManager.membersCollapsed.get(username) ?: false,
                        caller = rawMehmon.caller,
                        toifa = rawMehmon.toifa,
                        isAytilgan = rawMehmon.isAytilgan,
                        mehmonId = rawMehmon.mehmonId
                    )

                    mehmonlar.add(collapseAdaptedMehmon)

                    if (collapseAdaptedMehmon.caller == CloudFirestoreRepo.username) {
                        isLastItemLocal.postValue(true)
                    } else {
                        isLastItemLocal.postValue(false)
                    }
                }


                toifaniBarchaMehmonlariClassic[kerakliArray].clear()
                toifaniBarchaMehmonlariClassic[kerakliArray].add(mezbon)
                toifaniBarchaMehmonlariClassic[kerakliArray].addAll(
                    mehmonlar.sortedWith(
                        compareByDescending(Mehmon::mehmonId)
                    )
                )

                if (toifaniBarchaMehmonlariClassic[kerakliArray].size == 1) {
                    toifaniBarchaMehmonlariClassic[kerakliArray].add(emptyMehmon)
                }

                //Initializes toifaniBarchaMehmonlari
                for (i in 1..kerakliArray) {
                    toifaniBarchaMehmonlari.value!!.add(arrayListOf())
                }

                toifaniBarchaMehmonlari.value!![kerakliArray] =
                    toifaniBarchaMehmonlariClassic[kerakliArray]

                toifaniBarchaMehmonlari.postValue(toifaniBarchaMehmonlari.value)


            }


        return mehmons
    }

//    @RequiresApi(Build.VERSION_CODES.N)
//    fun getRemoteSearchResults(searchQuery: String): LiveData<List<Mehmon>> {
//
//        var result = MutableLiveData<List<Mehmon>>()
//
//        result.value = toifaniBarchaMehmonlari.value!!.stream().filter { t: Mehmon -> t.ism == searchQuery }.collect(Collectors.toList())
//
//        return result
//    }

    fun loadLocalSearchResults(searchQuery: String) {

        database.getSearchResults(searchQuery)

    }

    //TODO firestoredan kelgan mehmonlarni ro'yxati uchun boshqa alohida mutablelivedata<list> yaratish
    //TODO ularni boshiga memberNameheader elementi qo'shish, usernameni ko'rsatish. Va recyclerviewda uni formatlab ko'rsatish va spinner qo'yish


    fun addNewMehmon() {

        //TODO odamga o'xshab norm ism yozmasa qo'shmaslik kerak.

        val isCollapsed: Boolean =
            MembersManager.membersCollapsed.get(CloudFirestoreRepo.username) ?: false

        val mehmonForLocal = Mehmon(
            ism = ism.value!!,
            toifa = toifa.value!!,
            isCollapsed = isCollapsed
        )
        var mehmonForFirestore: Mehmon
        uiScope.launch {
            val localMehmonId = insertToRoom(mehmonForLocal)

            mehmonForFirestore = Mehmon(
                mehmonId = localMehmonId,
                ism = mehmonForLocal.ism,
                toifa = mehmonForLocal.toifa,
                isAytilgan = mehmonForLocal.isAytilgan
            )
            CloudFirestoreRepo.sendToFireStore(mehmonForFirestore)

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
        if (isMehmonLocal(newMehmon)) {
            uiScope.launch {
                updateOnRoom(newMehmon)
            }
        }

        CloudFirestoreRepo.updateItemOnFirestore(
            newMehmon,
            CompletedClickListener { result, isSuccessful ->

                //TODO callback for completed updating

            })

    }

    fun onCollapseMember(member: Mehmon, isCollapsed: Boolean) {

//        if (member.ism == "local" || member.ism == CloudFirestoreRepo.username) {
//            uiScope.launch {
//                withContext(Dispatchers.IO) {
//                    for (mehmon in database.getAllMehmons()) {
//                        updateOnRoom(collapseChanger(mehmon = mehmon, isCollapsed = isCollapsed))
//                    }
//                }
//            }
//        } else {
        val kerakliArray = MembersManager.members.getValue(member.ism)

        for (mehmon in toifaniBarchaMehmonlariClassic[kerakliArray]) {

            if (mehmon.toifa != "mezbon") {

                val indexOfMehmon = toifaniBarchaMehmonlariClassic[kerakliArray].indexOf(mehmon)

                toifaniBarchaMehmonlariClassic[kerakliArray][indexOfMehmon] =
                    collapseChanger(mehmon, isCollapsed)

                Log.i("jing", "It's firestore mehmon collapsed or expanded")
                toifaniBarchaMehmonlari.value!![kerakliArray] =
                    toifaniBarchaMehmonlariClassic[kerakliArray]

                toifaniBarchaMehmonlari.value = toifaniBarchaMehmonlari.value
            }
        }
//        }
    }

    private fun collapseChanger(mehmon: Mehmon, isCollapsed: Boolean): Mehmon {

        val workerMehmon = Mehmon(
            mehmonId = mehmon.mehmonId,
            ism = mehmon.ism,
            toifa = mehmon.toifa,
            caller = mehmon.caller,
            isAytilgan = mehmon.isAytilgan,
            isCollapsed = isCollapsed
        )

        return workerMehmon
    }

    fun renameMehmon(oldMehmon: Mehmon, newIsm: String) {

        val newMehmon = Mehmon(
            ism = newIsm,
            mehmonId = oldMehmon.mehmonId,
            isCollapsed = oldMehmon.isCollapsed,
            isAytilgan = oldMehmon.isAytilgan,
            toifa = oldMehmon.toifa,
            caller = oldMehmon.caller
        )


        if (isMehmonLocal(newMehmon)) {
            uiScope.launch {
                updateOnRoom(newMehmon)
            }
        }

        CloudFirestoreRepo.updateItemOnFirestore(
            newMehmon,
            CompletedClickListener { result, isSuccessful ->

            })

        //TODO snackbar chiqarish kerak
    }

    fun deleteMehmon(mehmon: Mehmon) {
        if (isMehmonLocal(mehmon)) {
            uiScope.launch {
                deleteFromRoom(mehmon)
            }
        }

        //TODO firestoreda mehmon o'chib ketkanda Roomdan o'chmayabdi.
        CloudFirestoreRepo.deleteMehmonFromFirestore(
            mehmon,
            CompletedClickListener { result, isSuccessful ->

            })
        //TODO snackbar chiqarish kerak
    }

    private fun isMehmonLocal(mehmon: Mehmon): Boolean {
        return mehmon.caller == "local" || mehmon.caller == CloudFirestoreRepo.username
    }

    private suspend fun deleteFromRoom(mehmon: Mehmon) {
        withContext(Dispatchers.IO) {
            database.deleteMehmon(mehmon)
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
            Log.i("jinga", "updated")
        }
    }
}
