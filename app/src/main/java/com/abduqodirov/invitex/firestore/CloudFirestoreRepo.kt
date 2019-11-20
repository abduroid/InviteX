package com.abduqodirov.invitex.firestore

import com.abduqodirov.invitex.database.Mehmon
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

object CloudFirestoreRepo {

    var weddingId: String = ""
    var username: String = ""

    private val weddingsCollection by lazy {
        FirebaseFirestore.getInstance()
            .collection("weddings")
    }

    fun initializeFireStore(
        cardAmount: Int,
        username: String,
        connectedClickListener: ConnectedClickListener
    ) {

        this.username = username

        val wedding = hashMapOf(
            "card_amout" to cardAmount,
            "members" to arrayListOf(
                username
            )
        )

        weddingsCollection
            .add(wedding)
            .addOnSuccessListener {
                connectedClickListener.onConnect(weddingId = it.id)
                weddingId = it.id
            }

    }

    fun joinToExistingWedding(
        username: String,
        weddingId: String,
        connetedClickListener: ConnectedClickListener
    ) {
        this.username = username

        weddingsCollection.document(weddingId).update("members", FieldValue.arrayUnion(username))
            .addOnSuccessListener {
                connetedClickListener.onConnect(weddingId)
            }
            .addOnFailureListener {
            }
    }

//    fun getGuestsOfMember(username: String, toifa: String): ArrayList<Mehmon> {
//
//        val mehmons = ArrayList<Mehmon>()
//        weddingsCollection.document(weddingId).collection("$username-$toifa")
//            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
//                Log.i("hjht", "listener triggered")
//
//                if (firebaseFirestoreException != null) {
//                    Log.w("fire error", "Listen failed.", firebaseFirestoreException)
//                    return@addSnapshotListener
//                }
//
//                for (document in querySnapshot!!) {
//                    Log.i("hjht", "loop triggered")
//                    val mehmon = document.toObject(Mehmon::class.java)
//                    mehmons.plus(mehmon)
//                    Log.i("hjht", "$mehmon")
//                }
//
//            }
//
//        return mehmons
//    }


    fun sendToFireStore(mehmon: Mehmon) {
        val db = FirebaseFirestore.getInstance()
        db.collection("weddings/$weddingId/${username}-${mehmon.toifa}")
            .document("${mehmon.mehmonId}")
            .set(
                Mehmon(
                    mehmonId = mehmon.mehmonId,
                    caller = username,
                    ism = mehmon.ism,
                    isAytilgan = mehmon.isAytilgan,
                    toifa = mehmon.toifa
                )
            )

    }

    fun updateItemChecked(mehmon: Mehmon) {

        val mehmonUsername: String = if (mehmon.caller == "local") {
            username
        } else {
            mehmon.caller
        }

        weddingsCollection.document(weddingId).collection("${mehmonUsername}-${mehmon.toifa}")
            .document("${mehmon.mehmonId}")
            .update("aytilgan", mehmon.isAytilgan)

    }

}

class ConnectedClickListener(val connectedClickListener: (weddingId: String) -> Unit) {
    fun onConnect(weddingId: String) = connectedClickListener(weddingId)
}
