package com.abduqodirov.invitex.mainList.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.abduqodirov.invitex.R
import com.abduqodirov.invitex.databinding.DialogAddmehmonBinding
import com.abduqodirov.invitex.mainList.MainListsViewModel
import com.abduqodirov.invitex.mainList.MainListsViewModelFactory

class AddMehmonDialogFragment(val viewModelFactory: MainListsViewModelFactory) : DialogFragment() {
//TODO mehmonni ro'yhatga qo'shayotkanda toifasini aniqlashtirish kerak

    private lateinit var viewModel: MainListsViewModel
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater

            viewModel = ViewModelProviders.of(it, viewModelFactory).get(MainListsViewModel::class.java)

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