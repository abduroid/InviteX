package com.abduqodirov.invitex


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.abduqodirov.invitex.databinding.FragmentCongratsBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

/**
 * A simple [Fragment] subclass.
 */
class CongratsFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentCongratsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_congrats,
            container, false
        )

        val sharedPreferences = activity!!.getSharedPreferences("keyim", Context.MODE_PRIVATE)

        if (!sharedPreferences.getBoolean("isFirstLaunch", true)) {
            this.findNavController()
                .navigate(CongratsFragmentDirections.actionGlobalCollectionListFragment2())
        }

        auth = FirebaseAuth.getInstance()

        binding.hopButton.setOnClickListener {

            if (auth.currentUser != null) {

            } else {
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
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val response = IdpResponse.fromResultIntent(data)

        if (requestCode == RC_SIGN_IN) {
            this.findNavController().navigate(CongratsFragmentDirections.actionCongratsFragmentToCardAmoutFragment())
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