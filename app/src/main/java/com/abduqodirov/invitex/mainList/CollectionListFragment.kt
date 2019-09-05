package com.abduqodirov.invitex.mainList


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager

import com.abduqodirov.invitex.R
import com.google.android.material.tabs.TabLayout

/**
 * A simple [Fragment] subclass.
 */
class CollectionListFragment : Fragment() {

    private lateinit var collectionListAdapter: CollectionListAdapter
    private lateinit var viewPager: ViewPager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val sharedPreferences = activity!!.getSharedPreferences("keyim", Context.MODE_PRIVATE)

        if (sharedPreferences.getBoolean("isFirstLaunch", true)) {

            with(sharedPreferences.edit()) {
                putBoolean("isFirstLaunch", false)
                commit()
            }
            //TODO OOPlashtir

            this.findNavController()
                .navigate(CollectionListFragmentDirections.actionCollectionListFragmentToIntroNestedNavigation())
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collection_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val tabs = listOf("sinfdoshlar", "qarindoshlar", "Ishxona", "Do'stlar")

        collectionListAdapter = CollectionListAdapter(childFragmentManager, tabs)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = collectionListAdapter
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        tabLayout.setupWithViewPager(viewPager)

    }

}
