package com.abduqodirov.invitex.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.abduqodirov.invitex.R
import com.abduqodirov.invitex.database.MehmonDatabase
import com.abduqodirov.invitex.viewmodel.ListViewModelFactory
import com.abduqodirov.invitex.viewmodel.SynchronizeViewModel
import kotlinx.android.synthetic.main.fragment_progress_uploading.*

class UploadingProgressFragment : Fragment(R.layout.fragment_progress_uploading) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(activity!!.application)

        val dataSource = MehmonDatabase.getInstance(application).mehmonDatabaseDao

        val viewModelFactory =
            ListViewModelFactory(application = application, dataSource = dataSource)

        val viewModel = activity!!.run {
            ViewModelProviders.of(this, viewModelFactory).get(SynchronizeViewModel::class.java)
        }

        viewModel.uploadingProgress.observe(this@UploadingProgressFragment, Observer {
            uploadingProgress.max = viewModel.sizeToBeUploaded
            uploadingProgress.progress = it

            if (uploadingProgress.max == it) {
                this.findNavController()
                    .navigate(UploadingProgressFragmentDirections.actionUploadingProgressFragmentToDashboardFragment())
            }
        })

    }

}
