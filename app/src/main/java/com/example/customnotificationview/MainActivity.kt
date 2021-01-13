package com.example.customnotificationview

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private var notificationManager: NotificationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e(TAG, "onCreate: ")

        setNotification()
    }

    override fun onStart() {
        super.onStart()
        Log.e(TAG, "onStart: ")
    }

    fun setNotification() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannelForIncomingCall()
        }
        // Get the layouts to use in the custom notification
        val notificationLayout = RemoteViews(packageName, R.layout.simple_music_status_bar_large)
        val notificationLayoutExpanded = RemoteViews(packageName, R.layout.simple_music_status_bar_expanded)

        // Apply the layouts to the notification
        val customNotification = NotificationCompat.Builder(this, "TEST_CHANNEL_ID")
            .setSmallIcon(android.R.color.transparent)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)
            .setCustomBigContentView(notificationLayoutExpanded)
            .setAutoCancel(true)
            .setShowWhen(false)
            .build()

        notificationManager?.notify(123, customNotification)
    }


    fun getNotificationManager(): NotificationManager? {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        return notificationManager
    }

    @TargetApi(Build.VERSION_CODES.O)
    fun createChannelForIncomingCall() {
        getNotificationManager()
        val channel = NotificationChannel("TEST_CHANNEL_ID", "Test Notification", NotificationManager.IMPORTANCE_DEFAULT)
        channel.enableLights(true)
        channel.enableVibration(false)
        channel.lightColor = R.color.purple_200
        channel.setSound(null, null)
        channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        notificationManager?.createNotificationChannel(channel)
    }
}