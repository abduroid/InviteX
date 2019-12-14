package com.abduqodirov.invitex


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.abduqodirov.invitex.firestore.CloudFirestoreRepo

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!CloudFirestoreRepo.isFirestoreConnected()) {
            this.findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToSynchronizeIntroFragment())
        }
    }

}
