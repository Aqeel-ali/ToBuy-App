package com.aqeel.tobuy.ui

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aqeel.tobuy.R
import com.aqeel.tobuy.arch.ToBuyModel
import com.aqeel.tobuy.database.AppDatabase

class MainActivity : AppCompatActivity() {

    lateinit var navController:NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        val viewModel:ToBuyModel by viewModels()
        viewModel.init(AppDatabase.getDatabase(this))



        var navHostFragment=supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController=navHostFragment.navController

        appBarConfiguration=AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController,appBarConfiguration)



    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)|| super.onSupportNavigateUp()
    }


//    fun hideKeyboard(view: View){
//       val imm:InputMethodManager=application.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
//       imm.hideSoftInputFromWindow(view.windowToken,0)
//    }
//
//    fun showKeyboard(){
//
//            val imm:InputMethodManager=application.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
//            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0)
//
//    }







}