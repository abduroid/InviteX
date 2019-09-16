package com.abduqodirov.invitex

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_card_amout.*

class CardAmoutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //TODO verstkasini qil, logikasiniyam qil
        return inflater.inflate(R.layout.fragment_card_amout, container, false)
    }

    override fun onStart() {
        super.onStart()

        var cardAmount: Int
        val sharedPreferences = activity!!.getSharedPreferences("keyim", Context.MODE_PRIVATE)

        amount_button.setOnClickListener {

            cardAmount = if (card_amout_edit.text.toString().isNotEmpty()) {
                card_amout_edit.text.toString().toInt()
            } else {
                0
            }
            with(sharedPreferences.edit()) {
                putInt("cardAmount", cardAmount)
                commit()
            }

            this.findNavController().navigate(R.id.action_global_collectionListFragment)
        }
    }

}
