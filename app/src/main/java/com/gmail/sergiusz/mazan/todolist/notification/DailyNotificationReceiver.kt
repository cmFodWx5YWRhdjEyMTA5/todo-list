package com.gmail.sergiusz.mazan.todolist.notification

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.gmail.sergiusz.mazan.todolist.R
import com.gmail.sergiusz.mazan.todolist.application.TODOList
import com.gmail.sergiusz.mazan.todolist.activity.MainActivity
import com.gmail.sergiusz.mazan.todolist.dao.TaskDatabase
import com.gmail.sergiusz.mazan.todolist.dao.TaskRepository
import java.time.LocalDate

class DailyNotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val pendingResult : PendingResult =  goAsync()
        val asyncTask = NotificationTask(
            pendingResult,
            intent
        )
        asyncTask.execute(context)

    }

    private class NotificationTask(private val pendingResult : PendingResult, private val intent : Intent?)
        : AsyncTask<Context, Unit, Unit>() {

        override fun doInBackground(vararg params: Context) {
            val repository = TaskRepository(
                TaskDatabase.getDatabase(params[0])
            )
            val todayTasksAmount = repository.getUndoneTasksFromADaySync(LocalDate.now()).size
            val overdueTasksAmount = repository.getUndoneTasksEarlierThanSync(LocalDate.now()).size

            val mainActivityIntent = PendingIntent.getActivity(params[0], 0,
                Intent(params[0], MainActivity::class.java), 0)

            val builder = NotificationCompat.Builder(params[0],
                TODOList.DAILY_REMINDER_CHANNEL_ID
            )
                .setSmallIcon(R.drawable.ic_check_circle_white_32dp)
                .setContentTitle("Daily reminder")
                .setContentText("You have today $todayTasksAmount tasks and $overdueTasksAmount overdue")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(mainActivityIntent)
                .setAutoCancel(true)
            with(NotificationManagerCompat.from(params[0])) {
                notify(0, builder.build())
            }
        }

        override fun onPostExecute(result: Unit?) {
            super.onPostExecute(result)
            pendingResult.finish()
        }
    }
}