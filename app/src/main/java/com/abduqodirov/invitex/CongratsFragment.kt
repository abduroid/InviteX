package com.abduqodirov.invitex

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_congrats.*

class CongratsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val sharedPreferences = activity!!.getSharedPreferences("keyim", Context.MODE_PRIVATE)

        if (!sharedPreferences.getBoolean("isFirstLaunch", true)) {
            this.findNavController()
                .navigate(CongratsFragmentDirections.actionGlobalCollectionListFragment())
        }
        return inflater.inflate(R.layout.fragment_congrats, container, false)
    }

    override fun onStart() {
        super.onStart()

        hop_button.setOnClickListener {
            this.findNavController()
                .navigate(CongratsFragmentDirections.actionCongratsFragmentToCardAmoutFragment())
        }
    }

}