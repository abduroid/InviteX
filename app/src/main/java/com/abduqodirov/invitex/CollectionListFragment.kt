package com.abduqodirov.invitex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_collection_list.*

class CollectionListFragment : Fragment() {

    private val tabs = listOf("sinfdoshlar", "qarindoshlar", "ishxona", "do'stlar")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_collection_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val collectionListAdapter = CollectionListAdapter(childFragmentManager, tabs)

        pager.adapter = collectionListAdapter

        tab_layout.setupWithViewPager(pager)
    }
}
