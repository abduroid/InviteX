package com.abduqodirov.invitex.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.abduqodirov.invitex.R
import kotlinx.android.synthetic.main.fragment_congrats.*

class CongratsFragment : Fragment(R.layout.fragment_congrats) {

    val sharedPreferences = activity!!.getSharedPreferences("keyim", Context.MODE_PRIVATE)

    override fun onStart() {
        super.onStart()
        hop_button.setOnClickListener {
            this.findNavController()
                .navigate(CongratsFragmentDirections.actionCongratsFragmentToCollectionListFragment())

            with(sharedPreferences.edit()) {
                putBoolean("isFirstLaunch", false)
                apply()
            }
        }
    }

}