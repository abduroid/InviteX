package com.abduqodirov.invitex


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.abduqodirov.invitex.databinding.FragmentSignUpBinding
import kotlinx.android.synthetic.main.fragment_sign_up.*

/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //TODO signup backendi qilinishi kerak

        val binding: FragmentSignUpBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up,
            container, false)

        binding.signedupButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_signUpFragment_to_cardAmoutFragment)
        }

        // Inflate the layout for this fragment
        return binding.root
    }


}
