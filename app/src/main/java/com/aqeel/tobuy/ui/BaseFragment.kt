package com.aqeel.tobuy.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.aqeel.tobuy.arch.ToBuyModel
import com.aqeel.tobuy.database.AppDatabase

abstract class BaseFragment:Fragment() {



    protected fun navigateViaGraph(actionId:Int){
        mainActivity.navController.navigate(actionId)
    }

    protected val mainActivity:MainActivity get() = activity as MainActivity

    protected val appDatabase: AppDatabase get() = AppDatabase.getDatabase(requireContext())

    protected val sharedViewModel :ToBuyModel by activityViewModels()

}