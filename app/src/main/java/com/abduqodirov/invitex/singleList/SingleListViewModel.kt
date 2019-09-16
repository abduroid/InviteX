package com.abduqodirov.invitex.singleList

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.abduqodirov.invitex.database.MehmonDatabaseDao

class SingleListViewModel(
    val database: MehmonDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    fun specificMehmons(toifa: String) = database.getSpecificMehmons(toifa = toifa)

    init {
        firstLaunchTasks()
    }

    private fun firstLaunchTasks() {
        val sharedPreferences =
            getApplication<Application>().getSharedPreferences("keyim", Context.MODE_PRIVATE)

        if (sharedPreferences.getBoolean("isFirstLaunch", true)) {

            with(sharedPreferences.edit()) {
                putBoolean("isFirstLaunch", false)
                commit()

            }
        }
    }
}
