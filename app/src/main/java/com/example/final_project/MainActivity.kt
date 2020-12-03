package com.example.final_project

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Observer
import com.example.final_project.database.StreakRepository
import com.example.final_project.database.TodaysApodRepository
import com.example.final_project.model.Streak
import com.example.final_project.model.TodaysApod
import com.example.final_project.model.TodaysApodModel
import com.example.final_project.service.ApodNotificationService
import com.example.final_project.service.MessageQueue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

        // Set up nav controller
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        // This sets each fragment in the nav as a root component, which means it has access to the
        // hamburger menu bar
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_notifications,
                R.id.nav_apod,
                R.id.nav_slideshow,
                R.id.nav_earth,
                R.id.nav_stats
            ),
            drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Streak updates
        scope.launch(Dispatchers.Default) {
            streakRepository = StreakRepository(this@MainActivity)
            updateDailyStreak()
        }

        // Apod Notification
        scope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                val scheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
                val jobInfo = JobInfo.Builder(
                    JOB_ID,
                    ComponentName(this@MainActivity, ApodNotificationService::class.java)
                )
                jobInfo.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                jobInfo.setMinimumLatency(15 * 1000)
                scheduler.schedule(jobInfo.build())
            }
        }

        MessageQueue.Channel.observe(this@MainActivity,
            Observer<TodaysApod> { apod ->
                Log.d("Here", "mainActivityMessageQueue $apod")

                scope.launch(Dispatchers.Default) {
                    withContext(Dispatchers.IO) {
                        val todaysApodModel = TodaysApodModel(TodaysApodRepository(this@MainActivity))
                        todaysApodModel.setTodaysApod(apod)
                    }
                }
            })

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

    /**
     * On application startup, check and update the user's daily streak count
     */
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

    companion object {
        const val JOB_ID = 1
    }
}