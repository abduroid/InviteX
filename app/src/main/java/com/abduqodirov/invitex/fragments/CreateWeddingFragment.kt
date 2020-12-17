package com.abduqodirov.invitex.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.abduqodirov.invitex.R
import com.abduqodirov.invitex.database.MehmonDatabase
import com.abduqodirov.invitex.databinding.FragmentCreateWeddingBinding
import com.abduqodirov.invitex.util.isInternetAvailable
import com.abduqodirov.invitex.viewmodel.ListViewModelFactory
import com.abduqodirov.invitex.viewmodel.SynchronizeViewModel
import com.google.android.material.snackbar.Snackbar

class CreateWeddingFragment : Fragment() {

    private var _binding: FragmentCreateWeddingBinding? = null

    private val binding get() = _binding!!

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

        _binding = FragmentCreateWeddingBinding.inflate(inflater, container, false)

        val application = requireNotNull(activity!!.application)

        val dataSource = MehmonDatabase.getInstance(application).mehmonDatabaseDao

        val viewModelFactory = ListViewModelFactory(dataSource, application)

        val viewModel = activity!!.run {
            ViewModelProviders.of(this, viewModelFactory).get(SynchronizeViewModel::class.java)
        }

        binding.createButton.setOnClickListener {

            if (viewModel.username.value.isNullOrEmpty()) {
                Snackbar.make(view!!, "Username kiritishingiz shart", Snackbar.LENGTH_SHORT).show()
            } else {
                viewModel.createNewFirestoreDatabase()

                this.findNavController()
                    .navigate(CreateWeddingFragmentDirections.actionCreateWeddingFragmentToUploadingProgressFragment())
            }
        }


        return binding.root
    }


}
