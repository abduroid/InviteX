package com.abduqodirov.invitex

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abduqodirov.invitex.database.MehmonDatabaseDao


class MainListsViewModelFactory(
    private val dataSource: MehmonDatabaseDao,
    private val application: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MainListsViewModel::class.java)) {
            return MainListsViewModel(dataSource, application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")

    }
}
