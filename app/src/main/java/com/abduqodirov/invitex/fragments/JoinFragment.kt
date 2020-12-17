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
import com.abduqodirov.invitex.databinding.FragmentJoinBinding
import com.abduqodirov.invitex.firestore.CloudFirestoreRepo
import com.abduqodirov.invitex.firestore.CompletedClickListener
import com.abduqodirov.invitex.util.isInternetAvailable
import com.abduqodirov.invitex.viewmodel.ListViewModelFactory
import com.abduqodirov.invitex.viewmodel.SynchronizeViewModel
import com.google.android.material.snackbar.Snackbar

class JoinFragment : Fragment() {

    private var _binding: FragmentJoinBinding? = null
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

        _binding = FragmentJoinBinding.inflate(inflater, container, false)

        val application = requireNotNull(activity!!.application)

        val dataSource = MehmonDatabase.getInstance(application).mehmonDatabaseDao

        val viewModelFactory =
            ListViewModelFactory(application = application, dataSource = dataSource)

        val viewModel = activity!!.run {
            ViewModelProviders.of(this, viewModelFactory).get(SynchronizeViewModel::class.java)
        }


        binding.joinButton.setOnClickListener {

            if (viewModel.username.value.isNullOrBlank() || viewModel.userEnteredWeddingId.value.isNullOrBlank()) {
                Snackbar.make(view!!, "Barcha fieldlar to'ldirilishi shart", Snackbar.LENGTH_SHORT)
                    .show()
            }


            if (!viewModel.userEnteredWeddingId.value.isNullOrEmpty() && !viewModel.username.value.isNullOrEmpty()) {

                CloudFirestoreRepo.isThereWeddingWithPassedId(
                    viewModel.userEnteredWeddingId.value!!,
                    CompletedClickListener { _, isSuccessful ->
                        if (isSuccessful) {

                            CloudFirestoreRepo.isUsernameAvailable(viewModel.userEnteredWeddingId.value!!,
                                viewModel.username.value!!,
                                CompletedClickListener { resultUsername, isSuccessfulUsername ->
                                    if (isSuccessfulUsername) {
                                        viewModel.joinToExistingDatabase()
                                        this.findNavController()
                                            .navigate(JoinFragmentDirections.actionJoinFragmentToUploadingProgressFragment())
                                    } else {
                                        Snackbar.make(view!!, resultUsername, Snackbar.LENGTH_SHORT)
                                            .show()
                                    }
                                })


                        } else {
                            Snackbar.make(view!!, "Bunday weddingId yo'q", Snackbar.LENGTH_SHORT)
                                .show()
                        }
                    })
            }


        }

        val fragment = HelpFragment()

        binding.getQrHelpTextLink.setOnClickListener {
            fragment.show(
                activity!!.supportFragmentManager,
                getString(R.string.where_i_can_get_code_text)
            )
        }
        return binding.root
    }


}
