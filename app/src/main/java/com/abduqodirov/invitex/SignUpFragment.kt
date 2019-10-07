package com.abduqodirov.invitex

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
//TODO Firebase raqam bilan ro'yxatdan o'tishni shu yerda hal qilish kerak. Hozir Firebase AuthUI bn alohida activityda qilib turaman

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(
                    arrayListOf(
                        AuthUI.IdpConfig.PhoneBuilder()
                            .setDefaultCountryIso("uz")
                            .build()
                    )
                )
                .build(),
            RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val response = IdpResponse.fromResultIntent(data)

        if (requestCode == RC_SIGN_IN) {
            this.findNavController().navigate(CollectionListFragmentDirections.actionGlobalCollectionListFragment())
        } else {
            if (response == null) {
                Snackbar.make(view!!, "Bekor qilindi", Snackbar.LENGTH_SHORT).show()
            }

            if (response!!.error!!.errorCode == ErrorCodes.NO_NETWORK) {
                Snackbar.make(view!!, "Internet yo'q kasofat", Snackbar.LENGTH_SHORT).show()
            }

            Snackbar.make(view!!, "Noma'lum error", Snackbar.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val RC_SIGN_IN = 123
    }

}