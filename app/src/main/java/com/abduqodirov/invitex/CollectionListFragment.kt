package com.abduqodirov.invitex

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.abduqodirov.invitex.database.MehmonDatabase
import com.abduqodirov.invitex.firebase.FireStoreManager
import com.abduqodirov.invitex.singleList.SingleListViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_collection_list.*

class CollectionListFragment : Fragment(R.layout.fragment_collection_list) {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sharedPreferences =
            activity!!.getSharedPreferences("keyim", Context.MODE_PRIVATE)

        if (!sharedPreferences.contains("cardAmount")) {

            this.findNavController()
                .navigate(R.id.action_collectionListFragment_to_intro_nested_navigation)

        } else {
            val tabs = resources.getStringArray(R.array.toifalar)
            val collectionListAdapter = CollectionListAdapter(childFragmentManager, tabs)
            tab_layout.setupWithViewPager(pager)
            pager.adapter = collectionListAdapter

            if (sharedPreferences.contains("weddingId")) {
                FireStoreManager.weddingId = sharedPreferences.getString("weddingId", "jjj")
            }

        }

    }
}
