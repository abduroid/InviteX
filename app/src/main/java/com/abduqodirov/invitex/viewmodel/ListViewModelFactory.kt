package com.abduqodirov.invitex.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abduqodirov.invitex.database.MehmonDatabaseDao

class ListViewModelFactory(
    private val dataSource: MehmonDatabaseDao,
    private val application: Application): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(SingleListViewModel::class.java)) {
            return SingleListViewModel(dataSource, application) as T
        }

        if (modelClass.isAssignableFrom(SynchronizeFragmentViewModel::class.java)) {
            return SynchronizeFragmentViewModel(dataSource, application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
