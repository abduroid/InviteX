package com.abduqodirov.invitex


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_no_internet.*

/**
 * A simple [Fragment] subclass.
 */
class NoInternet : Fragment(R.layout.fragment_no_internet) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retry_button.setOnClickListener {
            this.findNavController().popBackStack()
        }
    }

}
