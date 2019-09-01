package com.abduqodirov.invitex


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.abduqodirov.invitex.database.MehmonDatabase
import com.abduqodirov.invitex.databinding.FragmentMainListsBinding

/**
 * A simple [Fragment] subclass.
 */
class MainListsFragment : Fragment(){

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
                .navigate(R.id.action_mainListsFragment_to_intro_nested_navigation3)
        }

        val binding: FragmentMainListsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main_lists,
            container, false
        )


        val application = requireNotNull(this.activity).application

        val dataSource = MehmonDatabase.getInstance(application).mehmonDatabaseDao

        val viewModelFactory = MainListsViewModelFactory(dataSource = dataSource, application = application)

        val mainListsViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainListsViewModel::class.java)

        val adapter = MehmonAdapter()

        binding.mainList.adapter = adapter

        mainListsViewModel.mehmons.observe(this, Observer {
            adapter.submitList(it)
        })

        binding.lifecycleOwner = this

        // Inflate the layout for this fragment
        return binding.root
    }


}
