package com.abduqodirov.invitex.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.abduqodirov.invitex.CollectionListViewModel
import com.abduqodirov.invitex.ListViewModelFactory
import com.abduqodirov.invitex.R
import com.abduqodirov.invitex.databinding.DialogAddmehmonBinding

class AddMehmonDialogFragment(val viewModelFactory: ListViewModelFactory) : DialogFragment() {

    private lateinit var viewModel: CollectionListViewModel
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater

            viewModel = it.run {
                ViewModelProviders.of(this, viewModelFactory).get(CollectionListViewModel::class.java)
            }

            val binding: DialogAddmehmonBinding =
                DataBindingUtil.inflate(inflater, R.layout.dialog_addmehmon, null, false)

            binding.viewModel = viewModel

            binding.lifecycleOwner = it

            viewModel.dialogState.observe(it, Observer {
                dialog?.dismiss()
            })

            builder.setView(binding.root)

            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        viewModel.closeDialog()
    }

}