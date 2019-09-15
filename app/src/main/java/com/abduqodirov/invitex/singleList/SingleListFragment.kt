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
import com.abduqodirov.invitex.databinding.FragmentMainListsBinding

/**
 * A simple [Fragment] subclass.
 */

private const val ARG_OBJECT = "object"

class SingleListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentMainListsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main_lists,
            container, false
        )

        val adapter = MehmonAdapter()

        binding.mainList.adapter = adapter

        val application = requireNotNull(this.activity).application

        val dataSource = MehmonDatabase.getInstance(application).mehmonDatabaseDao

        val viewModelFactory = ListViewModelFactory(
            dataSource = dataSource,
            application = application
        )

        val mainListsViewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory).get(
                SingleListViewModel::class.java
            )
        } ?: throw Exception ("Invalid Activity")

        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            val toifa = getString(ARG_OBJECT)
            mainListsViewModel.specificMehmons(toifa)
                .observe(this@SingleListFragment, Observer {
                    adapter.submitList(it)
                })
            //TODO return them
        }

        binding.lifecycleOwner = this

        // Inflate the layout for this fragment
        return binding.root
    }

}
