package com.abduqodirov.invitex.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.abduqodirov.invitex.R
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
