package com.abduqodirov.invitex.mainList

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.abduqodirov.invitex.R
import com.abduqodirov.invitex.database.MehmonDatabase
import com.abduqodirov.invitex.databinding.FragmentCollectionListBinding
import com.abduqodirov.invitex.mainList.dialog.AddMehmonDialogFragment
import com.google.android.material.tabs.TabLayout
import kotlin.Exception


/**
 * A simple [Fragment] subclass.
 */
class CollectionListFragment : Fragment() {

    private lateinit var collectionListAdapter: CollectionListAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var binding: FragmentCollectionListBinding
    private lateinit var viewModelFactory: MainListsViewModelFactory

    private lateinit var viewModel: MainListsViewModel

    private val tabs = listOf("sinfdoshlar", "qarindoshlar", "Ishxona", "Do'stlar")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_collection_list, container, false)

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

        val application = requireNotNull(this.activity).application

        val dataSource = MehmonDatabase.getInstance(application).mehmonDatabaseDao

        viewModelFactory = MainListsViewModelFactory(
            dataSource = dataSource,
            application = application
        )

        val dialog = AddMehmonDialogFragment(viewModelFactory = viewModelFactory)

        binding.mainFabNew.setOnClickListener {
            dialog.show(fragmentManager!!, "AddMehmon")
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        collectionListAdapter = CollectionListAdapter(childFragmentManager, tabs)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = collectionListAdapter

        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        tabLayout.setupWithViewPager(viewPager)

        viewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory).get(MainListsViewModel::class.java)
        } ?: throw Exception ("Invalid activity")

        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                Log.i("tab", state.toString())
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                Log.i("tab", "onPageScrolled")
            }

            override fun onPageSelected(position: Int) {
                viewModel.tanlanganTab.value = tabs[position]
                Log.i("tab", "${viewModel.tanlanganTab.value} is now selected")
            }

        })
    }
}
