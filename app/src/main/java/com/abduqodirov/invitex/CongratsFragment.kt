package com.abduqodirov.invitex


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.abduqodirov.invitex.databinding.FragmentCongratsBinding
import kotlinx.android.synthetic.main.fragment_congrats.*

/**
 * A simple [Fragment] subclass.
 */
class CongratsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentCongratsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_congrats,
            container, false)

        binding.hopButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_congratsFragment_to_signUpFragment)
        }

        // Inflate the layout for this fragment
        return binding.root
    }


}
