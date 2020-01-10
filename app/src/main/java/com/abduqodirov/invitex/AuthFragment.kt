package com.abduqodirov.invitex


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class AuthFragment : Fragment(R.layout.fragment_auth) {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            Log.i("djb", "onCreate not null")
            this.findNavController()
                .navigate(AuthFragmentDirections.actionAuthFragmentToSynchronizeIntroFragment())
        } else {

            val providers = arrayListOf(
                AuthUI.IdpConfig.PhoneBuilder().build()
            )

            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
                RC_SIGN_IN
            )
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val response = IdpResponse.fromResultIntent(data)

        if (requestCode == RC_SIGN_IN) {

            if (resultCode == Activity.RESULT_OK) {
                Log.i("djb", "result bn qaytti")
                this.findNavController()
                    .navigate(AuthFragmentDirections.actionAuthFragmentToSynchronizeIntroFragment())
            } else {
                this.findNavController().popBackStack()
            }
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
