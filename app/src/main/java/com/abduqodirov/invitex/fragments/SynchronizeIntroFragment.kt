package com.abduqodirov.invitex.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.abduqodirov.invitex.R
import kotlinx.android.synthetic.main.fragment_intro_synchronize.*

class SynchronizeIntroFragment : Fragment(R.layout.fragment_intro_synchronize) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragment = HelpFragment()

        createButton.setOnClickListener {
            this.findNavController()
                .navigate(SynchronizeIntroFragmentDirections.actionSynchronizeIntroFragmentToCreateWeddingFragment())
        }

        joinButton.setOnClickListener {
            this.findNavController()
                .navigate(SynchronizeIntroFragmentDirections.actionSynchronizeIntroFragmentToJoinFragment())
        }

        whatAreTheseTextLink.setOnClickListener {
            fragment.show(activity!!.supportFragmentManager, getString(R.string.what_are_these_even_text))
        }
    }

}
