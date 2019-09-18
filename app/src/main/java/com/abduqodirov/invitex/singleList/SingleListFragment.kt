package com.abduqodirov.invitex.singleList

import android.os.Bundle
import android.util.Log
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

        val viewModelFactory = ListViewModelFactory(
            dataSource = dataSource,
            application = application
        )

        val viewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory).get(
                SingleListViewModel::class.java

            )
        } ?: throw Exception("Invalid Activity")

        val mAdapter = SingleListRecycleViewAdapter(MehmonClickListener { mehmonId ->
            //TODO aytilganni update qilish
            Log.i("tek", "tushvotti bo'tka")
            viewModel.onMehmonChecked(mehmonId)
        })

        binding.mainList.apply {
            adapter = mAdapter
            setHasFixedSize(true)
        }
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            viewModel.specificMehmons(getString(ARG_OBJECT))
                .observe(this@SingleListFragment, Observer {
                    mAdapter.submitList(it)
                })
        }

        //TODO why is this neccessary
        binding.lifecycleOwner = this

        return binding.root
    }

}
