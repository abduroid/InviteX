package com.abduqodirov.invitex.fragments

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.abduqodirov.invitex.R
import com.abduqodirov.invitex.firestore.CloudFirestoreRepo
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_show_code.*

class ShowCodeFragment : Fragment(R.layout.fragment_show_code) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weddingId_text.text = CloudFirestoreRepo.weddingId

        copy_button.setOnClickListener {
            val clipBoard = activity!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

            val clip: ClipData = ClipData.newPlainText("weddingId", CloudFirestoreRepo.weddingId)
            clipBoard.primaryClip = clip

            Snackbar.make(view, "Clipboardga nusxalandi", Snackbar.LENGTH_SHORT).show()
        }
    }

}
