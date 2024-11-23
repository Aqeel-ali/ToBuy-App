package com.aqeel.tobuy.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.aqeel.tobuy.R
import com.aqeel.tobuy.arch.ToBuyViewModel
import com.aqeel.tobuy.database.AppDatabase
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var navController:NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        val viewModel:ToBuyViewModel by viewModels()
        viewModel.init(AppDatabase.getDatabase(this))



        val navHostFragment=supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController=navHostFragment.navController

         appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.ProfileFragment,
            )
        )

        //setup top app bar
        setupActionBarWithNavController(navController,appBarConfiguration)

        //setup bottom app bar
        val bottomNavigationView=findViewById<BottomNavigationView>(R.id.bottomNavigation)
        setupWithNavController(
         bottomNavigationView   ,
        navController
        )

        // اخفاء واضهار الشريط السفلي
        navController.addOnDestinationChangedListener{controller,destination,argement ->
            if(appBarConfiguration.topLevelDestinations.contains(destination.id)){
            bottomNavigationView.isVisible=true
            }else{
                bottomNavigationView.isGone=true
            }
        }

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