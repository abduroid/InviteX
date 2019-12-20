package com.abduqodirov.invitex.firestore

import com.abduqodirov.invitex.models.Mehmon
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
        weddingIdArg: String,
        connetedClickListener: ConnectedClickListener
    ) {
        this.username = username

        weddingsCollection.document(weddingIdArg)
            .update("members", FieldValue.arrayUnion(username))
            .addOnSuccessListener {
                connetedClickListener.onConnect(weddingId = weddingIdArg)
                weddingId = weddingIdArg
            }
            .addOnFailureListener {
            }
    }

//    fun getGuestsOfMember(username: String, toifa: String): ArrayList<Mehmon> {
//
//        val mehmons = ArrayList<Mehmon>()
//        weddingsCollection.document(userEnteredWeddingId).collection("$username-$toifa")
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
        if (isFirestoreConnected()) {
            weddingsCollection.document(weddingId).collection("${username}-${mehmon.toifa}")
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

    }

    fun updateItemChecked(mehmon: Mehmon) {

        if (isFirestoreConnected()) {
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

    fun isFirestoreConnected() = weddingId.isNotEmpty() && username.isNotEmpty()

}

class ConnectedClickListener(val connectedClickListener: (weddingId: String) -> Unit) {
    fun onConnect(weddingId: String) = connectedClickListener(weddingId)
}
