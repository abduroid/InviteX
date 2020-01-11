//package com.abduqodirov.invitex.fragments
//
//import android.content.Context
//import androidx.fragment.app.Fragment
//import androidx.navigation.fragment.findNavController
//import com.abduqodirov.invitex.R
//import kotlinx.android.synthetic.main.fragment_card_amout.*
//
//private const val EMPTY_EDIT_TEXT = 0
//
//class CardAmoutFragment : Fragment(R.layout.fragment_card_amout) {
//
//
//    override fun onStart() {
//        super.onStart()
//
//        val sharedPreferences = activity!!.getSharedPreferences("keyim", Context.MODE_PRIVATE)
//
//        amount_button.setOnClickListener {
//
//            val cardAmount: Int = if (card_amout_edit.text.toString().isNotEmpty()) {
//                card_amout_edit.text.toString().toInt()
//            } else {
//                EMPTY_EDIT_TEXT
//            }
//
//            with(sharedPreferences.edit()) {
//                putInt("cardAmount", cardAmount)
//                commit()
//            }
//
//            this.findNavController().navigate(CongratsFragmentDirections.actionCongratsFragmentToCollectionListFragment())
//        }
//    }
//
//}
