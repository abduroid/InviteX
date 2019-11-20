package com.abduqodirov.invitex.fragments

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.abduqodirov.invitex.R
import kotlinx.android.synthetic.main.fragment_congrats.*

class CongratsFragment : Fragment(R.layout.fragment_congrats) {

    override fun onStart() {
        super.onStart()
        hop_button.setOnClickListener {
            this.findNavController()
                .navigate(CongratsFragmentDirections.actionCongratsFragmentToCardAmoutFragment())
        }
    }

}