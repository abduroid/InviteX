package com.abduqodirov.invitex

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_card_amout.*

class CardAmoutFragment : Fragment(R.layout.fragment_card_amout) {

    override fun onStart() {
        super.onStart()

        val sharedPreferences = activity!!.getSharedPreferences("keyim", Context.MODE_PRIVATE)

        amount_button.setOnClickListener {

            if (card_amout_edit.text.toString().isNotEmpty()) {

                with(sharedPreferences.edit()) {
                    putInt("cardAmount", card_amout_edit.text.toString().toInt())
                    commit()
                }
            }


            this.findNavController().navigate(R.id.action_global_collectionListFragment)
        }
    }

}
