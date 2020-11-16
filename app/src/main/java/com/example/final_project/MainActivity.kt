package com.example.final_project

import android.os.Bundle
import android.view.Menu
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.final_project.database.StreakRepository
import com.example.final_project.model.Streak
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val scope = CoroutineScope(Dispatchers.Main)
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var streakRepository: StreakRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_notifications, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Streak updates
        scope.launch(Dispatchers.Default) {
            streakRepository = StreakRepository(this@MainActivity)
            updateDailyStreak()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun updateDailyStreak() {
        when (val streak = streakRepository.getRecentStreak()) {
            null -> { // Create a new streak if none exists
                streakRepository.addStreak(Streak(UUID.randomUUID(), Date(), 1))
            }
            else -> { // Update streak/create new one if not exists
                val fmt = SimpleDateFormat("yyyyMMdd")
                val nextDay = fmt.parse(fmt.format(streak.date)).time + 1000 * 60 * 60 * 24
                val today = fmt.parse(fmt.format(Date())).time

                if (nextDay == today) {
                    streak.streak++
                    streakRepository.updateStreak(streak)
                } else if (nextDay - 1000 * 60 * 60 * 24 != today) {
                    streakRepository.addStreak(Streak(UUID.randomUUID(), Date(), 1))
                }
            }
        }
    }
}