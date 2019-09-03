package com.abduqodirov.invitex.mainList

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.abduqodirov.invitex.R
import com.abduqodirov.invitex.databinding.DialogAddmehmonBinding

class AddMehmonDialogFragment(val viewModelFactory: MainListsViewModelFactory) : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater

            val viewModel = ViewModelProviders.of(it, viewModelFactory).get(MainListsViewModel::class.java)

            val binding: DialogAddmehmonBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_addmehmon, null, false)

            binding.viewModel = viewModel

            binding.lifecycleOwner = it

            builder.setView(binding.root)

            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }
}