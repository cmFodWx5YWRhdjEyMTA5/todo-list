package com.gmail.sergiusz.mazan.todolist.application

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import com.gmail.sergiusz.mazan.todolist.notification.DailyNotificationReceiver
import java.util.*

class TODOList : Application() {


    companion object {
        const val DAILY_REMINDER_CHANNEL_ID = "daily_reminder_channel"
        const val TASK_REMINDER_CHANNEL_ID = "task_reminder_channel"
    }

    override fun onCreate() {
        super.onCreate()

        createNotificationChannels()
        setAlarm()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createDailyNotificationChannel()
            createTaskNotificationChannel()
        }
    }

    private fun createDailyNotificationChannel() {
        val name = "Daily reminder"
        val description = "Reminder saying how much tasks you have to do today"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(DAILY_REMINDER_CHANNEL_ID, name, importance)
        channel.description = description
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    private fun createTaskNotificationChannel() {
        val name = "Task reminder"
        val description = "Reminder informing about incoming task"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(TASK_REMINDER_CHANNEL_ID, name, importance)
        channel.description = description
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
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