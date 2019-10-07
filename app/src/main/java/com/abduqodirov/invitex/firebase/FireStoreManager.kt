package com.abduqodirov.invitex.firebase

import android.util.Log
import com.abduqodirov.invitex.database.Mehmon
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class FireStoreManager {

    companion object {

        var weddingId: String = ""
        private var currentUserId: String = FirebaseAuth.getInstance().currentUser?.uid ?: "abdulazizabduqodirov" //TODO temporary fake ID

        fun initializeFirestore(
            cardAmount: Int, weddingsCollection: CollectionReference,
            addedClickListener: AddedClickListener
        )
                : String {
            val toy = hashMapOf(
                "card_amout" to cardAmount,
                "members" to arrayListOf(
                    currentUserId
                )
            )

            weddingsCollection
                .add(toy)
                .addOnSuccessListener { documentRegerence ->
                    weddingId = documentRegerence.id

                    Log.i("hpo", "${documentRegerence.id} bo'lib yangi yaratildi")
                    addedClickListener.onAdded(weddingId)
                }

            return weddingId
        }

        fun sendToFirestore(db: FirebaseFirestore, mehmon: Mehmon) {
            //TODO shu yergacha kelmasdan chaqirayotkan joyni o'zida tekshirish kk
            if (weddingId.isNotEmpty()) {
                db.collection("weddings/$weddingId/$currentUserId")
                    .document("${mehmon.mehmonId}")
                    .set(mehmon)
            }
        }

        fun updateItem(db: FirebaseFirestore, mehmon: Mehmon) {
            //TODO shu yergacha kelmasdan chaqirayotkan joyni o'zida tekshirish kk
            if (weddingId.isNotEmpty()) {
                db.collection("weddings/$weddingId/$currentUserId")
                    .document("${mehmon.mehmonId}")
                    .update("aytilgan", mehmon.isAytilgan)
            }
        }

    }

}

class AddedClickListener(val addedClickListener: (weddingId: String) -> Unit) {
    fun onAdded(weddingId: String) = addedClickListener(weddingId)
}
