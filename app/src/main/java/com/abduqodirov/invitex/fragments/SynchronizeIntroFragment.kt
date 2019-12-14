package com.abduqodirov.invitex.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.abduqodirov.invitex.R
import kotlinx.android.synthetic.main.fragment_intro_synchronize.*

class SynchronizeIntroFragment : Fragment(R.layout.fragment_intro_synchronize) {

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        viewModel.uploadingProgress.observe(this@SynchronizeFragment, Observer {
//
//            binding.uploadGuests.max = viewModel.sizeToBeUploaded
//            binding.uploadGuests.progress = it
//
//        })
//
//
//        //TODO yangi yaratish disabled bo'lishi kerak agar wedding Id sharedprefda bor bo'lsa
//
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createButton.setOnClickListener {
            this.findNavController().navigate(SynchronizeIntroFragmentDirections.actionSynchronizeIntroFragmentToCreateWeddingFragment())
        }

        joinButton.setOnClickListener {
            this.findNavController().navigate(SynchronizeIntroFragmentDirections.actionSynchronizeIntroFragmentToJoinFragment())
        }
    }

}
