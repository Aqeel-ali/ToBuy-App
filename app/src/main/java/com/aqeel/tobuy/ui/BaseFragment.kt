package com.aqeel.tobuy.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import com.aqeel.tobuy.arch.ToBuyViewModel
import com.aqeel.tobuy.database.AppDatabase

abstract class BaseFragment:Fragment() {



    protected fun navigateViaGraph(actionId:Int){
        mainActivity.navController.navigate(actionId)
    }

    protected fun navigateViaGraph(navDirections: NavDirections){
        mainActivity.navController.navigate(navDirections)
    }

    protected fun navigateUp(){
        mainActivity.navController.navigateUp()
    }

    protected val mainActivity:MainActivity get() = activity as MainActivity

    protected val appDatabase: AppDatabase get() = AppDatabase.getDatabase(requireContext())

    protected val sharedViewModel :ToBuyViewModel by activityViewModels()






}