package com.abduqodirov.invitex.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

import com.abduqodirov.invitex.R
import com.abduqodirov.invitex.database.MehmonDatabase
import com.abduqodirov.invitex.databinding.FragmentJoinBinding
import com.abduqodirov.invitex.util.isInternetAvailable
import com.abduqodirov.invitex.viewmodel.ListViewModelFactory
import com.abduqodirov.invitex.viewmodel.SynchronizeViewModel

class JoinFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isInternetAvailable(activity!!)) {
            this.findNavController().navigate(R.id.action_global_noInternet)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentJoinBinding>(
            inflater,
            R.layout.fragment_join,
            container,
            false
        )

        val application = requireNotNull(activity!!.application)

        val dataSource = MehmonDatabase.getInstance(application).mehmonDatabaseDao

        val viewModelFactory =
            ListViewModelFactory(application = application, dataSource = dataSource)

        val viewModel = activity!!.run {
            ViewModelProviders.of(this, viewModelFactory).get(SynchronizeViewModel::class.java)
        }

        binding.viewModel = viewModel

        binding.joinButton.setOnClickListener {

            viewModel.joinToExistingDatabase()

            this.findNavController()
                .navigate(JoinFragmentDirections.actionJoinFragmentToUploadingProgressFragment())

        }

        val fragment = HelpFragment()

        binding.getQrHelpTextLink.setOnClickListener {
            fragment.show(activity!!.supportFragmentManager, getString(R.string.where_i_can_get_code_text))
        }
        return binding.root
    }


}
