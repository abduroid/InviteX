package com.abduqodirov.invitex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import kotlinx.android.synthetic.main.fragment_collection_list.*

class CollectionListFragment : Fragment(R.layout.fragment_collection_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tabs = resources.getStringArray(R.array.toifalar)

        val collectionListAdapter = CollectionListAdapter(childFragmentManager, tabs)

        pager.adapter = collectionListAdapter

        tab_layout.setupWithViewPager(pager)
    }
}
