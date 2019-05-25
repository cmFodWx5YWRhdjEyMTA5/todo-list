package com.gmail.sergiusz.mazan.todolist

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import java.util.*

class TODOList : Application() {

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()
        setAlarm()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Daily reminder"
            val description = "Reminder saying how much tasks you have to do today"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("daily_reminder_channel", name, importance)
            channel.description = description
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun setAlarm() {
        val intent = Intent(this, DailyNotificationReceiver::class.java)

        if (PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_NO_CREATE) == null) {
            val pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT)

            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, 8)
                set(Calendar.MINUTE, 0)
            }

            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY, pendingIntent)
        }
    }
}