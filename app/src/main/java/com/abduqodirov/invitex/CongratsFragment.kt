package com.abduqodirov.invitex


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.abduqodirov.invitex.databinding.FragmentCongratsBinding

/**
 * A simple [Fragment] subclass.
 */
class CongratsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentCongratsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_congrats,
            container, false
        )

        val sharedPreferences = activity!!.getSharedPreferences("keyim", Context.MODE_PRIVATE)

        if (!sharedPreferences.getBoolean("isFirstLaunch", true)) {
            this.findNavController()
                .navigate(CongratsFragmentDirections.actionGlobalCollectionListFragment())
        }

        binding.hopButton.setOnClickListener {
            this.findNavController().navigate(CongratsFragmentDirections.actionCongratsFragmentToCardAmoutFragment())
        }

        // Inflate the layout for this fragment
        return binding.root
    }

}