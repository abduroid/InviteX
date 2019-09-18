package com.abduqodirov.invitex.singleList

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.abduqodirov.invitex.database.Mehmon
import com.abduqodirov.invitex.database.MehmonDatabaseDao
import kotlinx.coroutines.*

class SingleListViewModel(
    val database: MehmonDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    //Use another data structure to increase performance
    fun specificMehmons(toifa: String) = database.getSpecificMehmons(toifa = toifa)

    init {
        firstLaunchTasks()
    }

    private suspend fun insert(mehmon: Mehmon) {
        withContext(Dispatchers.IO) {
            database.insert(mehmon)
        }
    }

    private suspend fun update(mehmon: Mehmon) {
        withContext(Dispatchers.IO) {
            database.update(mehmon)
        }
    }

    private suspend fun get(mehmonId: Long): Mehmon? {
        return withContext(Dispatchers.IO) {
            val mehmon = database.get(mehmonId)
            mehmon
        }
    }

    fun onMehmonChecked(mehmonId: Long) {
        uiScope.launch {
            val mehmon = get(mehmonId)
            //TODO null safety
            Log.i("tek", mehmon!!.ism)
            //Anyway changes its previous state.
            mehmon.isAytilgan = !mehmon.isAytilgan
            Log.i("tek", "${mehmon.isAytilgan}")
            update(mehmon)
        }
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
