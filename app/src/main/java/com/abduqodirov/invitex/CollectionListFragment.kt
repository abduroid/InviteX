package com.abduqodirov.invitex

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.abduqodirov.invitex.database.MehmonDatabase
import com.abduqodirov.invitex.databinding.FragmentCollectionListBinding
import com.abduqodirov.invitex.singleList.SingleListViewModel
import com.abduqodirov.invitex.dialog.AddMehmonDialogFragment
import com.google.android.material.tabs.TabLayout
import kotlin.Exception


/**
 * A simple [Fragment] subclass.
 */
class CollectionListFragment : Fragment() {

    private lateinit var viewModelFactory: ListViewModelFactory

    private val tabs = listOf("sinfdoshlar", "qarindoshlar", "ishxona", "do'stlar")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentCollectionListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_collection_list, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = MehmonDatabase.getInstance(application).mehmonDatabaseDao

        viewModelFactory = ListViewModelFactory(
            dataSource = dataSource,
            application = application
        )

        val dialog = AddMehmonDialogFragment(viewModelFactory = viewModelFactory)

        binding.mainFabNew.setOnClickListener {
            dialog.show(fragmentManager!!, "AddMehmon")
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val collectionListAdapter =
            CollectionListAdapter(childFragmentManager, tabs)
        val viewPager: ViewPager = view.findViewById(R.id.pager)
        viewPager.adapter = collectionListAdapter

        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        tabLayout.setupWithViewPager(viewPager)

        val viewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory).get(CollectionListViewModel::class.java)
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
            }

        })
    }
}
