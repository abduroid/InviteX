package com.abduqodirov.invitex

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abduqodirov.invitex.database.MehmonDatabaseDao
import com.abduqodirov.invitex.singleList.SingleListViewModel


class ListViewModelFactory(
    private val dataSource: MehmonDatabaseDao,
    private val application: Application): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(CollectionListViewModel::class.java)) {
            return CollectionListViewModel(dataSource) as T
        } else if (modelClass.isAssignableFrom(SingleListViewModel::class.java)) {
            return SingleListViewModel(dataSource, application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
