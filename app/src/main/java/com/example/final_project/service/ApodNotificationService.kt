package com.example.final_project.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.final_project.database.TodaysApodRepository
import com.example.final_project.model.TodaysApodModel
import com.example.final_project.networking.apodApi
import com.example.final_project.ui.apod.ApodFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ApodNotificationService() : JobService() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartJob(p0: JobParameters?): Boolean {
        Log.d("Here", "Job started")

        val serviceContext = this;

        val scope = CoroutineScope(Dispatchers.Main)


        scope.launch {
            withContext(Dispatchers.IO) {
                val todaysApodModel = TodaysApodModel(TodaysApodRepository(serviceContext))

                if (!todaysApodModel.haveTodaysApod()) {
                    serviceContext.let {
                        apodApi()
                            .getAPOD(it) { success, apod ->
                                if (success) {
                                    Log.d("Here", "apodNotificationService " + apod.toString())
                                    MessageQueue.Channel.postValue(apod)
                                    val notif = createNotification()
                                    NotificationManagerCompat.from(this@ApodNotificationService).notify(NOTIF_ID, notif)
                                }
                            }
                    }
                }
            }
        }

        return true
    }

    override fun onStopJob(p0: JobParameters?): Boolean {


        return true
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "apodChannel"
            val descriptionText = "Notification channel for receiving apod."
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
        val intent = Intent(this, ApodFragment::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentText("Click to view today's apod!")
            .setContentTitle("Astronomy Picture of the Day").setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentIntent(pendingIntent).setAutoCancel(true).build()
    }

    companion object {
        val CHANNEL_ID = "ApodChannelId"
        val NOTIF_ID = 1
    }
}

