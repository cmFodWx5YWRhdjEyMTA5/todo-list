package com.gmail.sergiusz.mazan.todolist

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat

class TaskNotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val pendingResult : PendingResult =  goAsync()
        val asyncTask = NotificationTask(pendingResult, intent)
        asyncTask.execute(context)
    }

    private class NotificationTask(private val pendingResult : PendingResult, private val intent : Intent?)
        : AsyncTask<Context, Unit, Unit>() {

        override fun doInBackground(vararg params: Context) {
            val repository = TaskRepository(TaskDatabase.getDatabase(params[0]).taskDao())
            val task = repository.getTaskWithId(intent!!.getLongExtra("taskId", -1))

            val mainActivityIntent = PendingIntent.getActivity(params[0], -1,
                Intent(params[0], MainActivity::class.java), 0)

            val builder = NotificationCompat.Builder(params[0], TODOList.DAILY_REMINDER_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_check_circle_white_32dp)
                .setContentTitle("You have incoming task")
                .setContentText(task.description)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(mainActivityIntent)
                .setAutoCancel(true)
            with(NotificationManagerCompat.from(params[0])) {
                notify(task.id.toInt(), builder.build())
            }
        }

        override fun onPostExecute(result: Unit?) {
            super.onPostExecute(result)
            pendingResult.finish()
        }
    }
}