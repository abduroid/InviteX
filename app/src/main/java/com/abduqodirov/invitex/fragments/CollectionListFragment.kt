package com.abduqodirov.invitex.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.abduqodirov.invitex.R
import com.abduqodirov.invitex.adapters.CollectionListAdapter
import com.abduqodirov.invitex.firestore.CloudFirestoreRepo
import kotlinx.android.synthetic.main.fragment_collection_list.*

class CollectionListFragment : Fragment(R.layout.fragment_collection_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val application = requireNotNull(activity!!.application)

        val sharedPreferences = application.getSharedPreferences("keyim", Context.MODE_PRIVATE)


        if (sharedPreferences.contains("isFirstLaunch")) {
            this.findNavController()
                .navigate(CollectionListFragmentDirections.actionCollectionListFragmentToCongratsFragment())
        } else {

            if (sharedPreferences.contains("username")) {
                CloudFirestoreRepo.username = sharedPreferences.getString("username", "jjj")
            }

            if (sharedPreferences.contains("weddingId")) {
                CloudFirestoreRepo.weddingId = sharedPreferences.getString("weddingId", "null")
            }

            val tabs = resources.getStringArray(R.array.toifalar)
            val collectionListAdapter =
                CollectionListAdapter(childFragmentManager, tabs)
            tab_layout.setupWithViewPager(pager)
            pager.adapter = collectionListAdapter



        }

    }
}
