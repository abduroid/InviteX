package com.abduqodirov.invitex


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.abduqodirov.invitex.databinding.FragmentCardAmoutBinding

/**
 * A simple [Fragment] subclass.
 */
class CardAmoutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding: FragmentCardAmoutBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_card_amout,
            container, false)

        binding.button.setOnClickListener {
            this.findNavController().navigate(R.id.action_global_collectionListFragment2)
        }


        //TODO verstkasini qil, logikasiniyam qil
        return binding.root
    }




}
