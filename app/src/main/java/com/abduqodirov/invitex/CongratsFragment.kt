package com.abduqodirov.invitex

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_congrats.*

class CongratsFragment : Fragment(R.layout.fragment_congrats) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = activity!!.getSharedPreferences("keyim", Context.MODE_PRIVATE)

        if (!sharedPreferences.getBoolean("isFirstLaunch", true)) {
            this.findNavController()
                .navigate(CongratsFragmentDirections.actionGlobalCollectionListFragment())
        }

    }

    override fun onStart() {
        super.onStart()

        hop_button.setOnClickListener {
            this.findNavController()
                .navigate(CongratsFragmentDirections.actionCongratsFragmentToCardAmoutFragment())
        }
    }

}