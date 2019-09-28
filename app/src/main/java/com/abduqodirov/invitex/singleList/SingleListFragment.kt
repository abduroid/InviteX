package com.abduqodirov.invitex.singleList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.abduqodirov.invitex.ListViewModelFactory
import com.abduqodirov.invitex.R
import com.abduqodirov.invitex.database.MehmonDatabase
import com.abduqodirov.invitex.databinding.FragmentSingleListBinding

private const val ARG_OBJECT = "object"

class SingleListFragment : Fragment() {

    private var toifa: String = "sinfdoshlar"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentSingleListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_single_list,
            container, false
        )

        val application = requireNotNull(this.activity).application

        val dataSource = MehmonDatabase.getInstance(application).mehmonDatabaseDao

        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            toifa = getString(ARG_OBJECT)
        }

        val viewModelFactory = ListViewModelFactory(
            dataSource = dataSource,
            application = application
        )

        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(
                SingleListViewModel::class.java
            )

        binding.viewModel = viewModel

        val mAdapter = SingleListRecycleViewAdapter(AytilganClickListener { mehmon ->
            viewModel.onMehmonChecked(mehmon)
        })

        binding.mainList.apply {
            adapter = mAdapter
            setHasFixedSize(true)
        }

        viewModel.specificMehmons(toifa)
            .observe(this@SingleListFragment, Observer {
                mAdapter.submitList(it)
            })

        binding.lifecycleOwner = this

        return binding.root
    }

}
