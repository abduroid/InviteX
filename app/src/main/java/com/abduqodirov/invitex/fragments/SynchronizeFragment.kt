package com.abduqodirov.invitex.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.abduqodirov.invitex.R
import androidx.lifecycle.Observer
import com.abduqodirov.invitex.database.MehmonDatabase
import com.abduqodirov.invitex.database.MehmonDatabaseDao
import com.abduqodirov.invitex.databinding.FragmentSynchronizeBinding
import com.abduqodirov.invitex.viewmodel.ListViewModelFactory
import com.abduqodirov.invitex.viewmodel.SynchronizeFragmentViewModel

class SynchronizeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentSynchronizeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_synchronize,
            container, false
        )

        val application = requireNotNull(activity!!.application)

        val dataSource: MehmonDatabaseDao =
            MehmonDatabase.getInstance(application).mehmonDatabaseDao

        val viewModelFactory = ListViewModelFactory(
            application = application,
            dataSource = dataSource
        )

        val viewModel =
            ViewModelProviders.of(this, viewModelFactory)
                .get(SynchronizeFragmentViewModel::class.java)

        viewModel.weddingId.observe(this@SynchronizeFragment, Observer {
            binding.weddingIdText.text = it
        })

        binding.viewModel = viewModel

        //TODO yangi yaratish disabled bo'lishi kerak agar wedding Id sharedprefda bor bo'lsa

        return binding.root
    }

}
