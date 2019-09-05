package com.abduqodirov.invitex.mainList


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.abduqodirov.invitex.R
import com.abduqodirov.invitex.database.Mehmon
import com.abduqodirov.invitex.database.MehmonDatabase
import com.abduqodirov.invitex.databinding.FragmentMainListsBinding
import com.abduqodirov.invitex.mainList.dialog.AddMehmonDialogFragment
import org.w3c.dom.ls.LSInput

/**
 * A simple [Fragment] subclass.
 */

private const val ARG_OBJECT = "object"

class MainListsFragment : Fragment() {

    private lateinit var binding: FragmentMainListsBinding
    private lateinit var mainListsViewModel: MainListsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main_lists,
            container, false
        )

//        mainListsViewModel.mehmons.observe(this, Observer {
//            adapter.submitList(it)
//        })


        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MehmonAdapter()

        binding.mainList.adapter = adapter

        val application = requireNotNull(this.activity).application

        val dataSource = MehmonDatabase.getInstance(application).mehmonDatabaseDao

        val viewModelFactory = MainListsViewModelFactory(
            dataSource = dataSource,
            application = application
        )

        mainListsViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            MainListsViewModel::class.java
        )

        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            mainListsViewModel.specificMehmons(getString(ARG_OBJECT)).observe(this@MainListsFragment, Observer {
                adapter.submitList(it)
            })
        }

        val dialog = AddMehmonDialogFragment(viewModelFactory)

        binding.mainFab.setOnClickListener {
            dialog.show(fragmentManager!!, "AddMehmon")
        }

        mainListsViewModel.showDialogEvent.observe(this, Observer {
            Log.i("dialog", it.toString())
            if (!it) {
                dialog.dismiss()
            }
        })

        binding.lifecycleOwner = this

    }

}
