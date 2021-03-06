package com.jraska.github.client.push

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.app.NotificationCompat
import com.jraska.github.client.NotificationSetup
import com.jraska.github.client.R
import com.jraska.github.client.ui.UriHandlerActivity

class ShowNotificationPushCommand constructor(private val context: Context,
                                              private val notificationManager: NotificationManager) : PushActionCommand {
  override fun execute(action: PushAction): Boolean {
    val title = action.parameters["title"] ?: return false
    val message = action.parameters["message"] ?: return false
    val deepLink = action.parameters["clickDeepLink"] ?: return false

    val intent = Intent(context, UriHandlerActivity::class.java)
    intent.data = Uri.parse(deepLink)

    val linkContentIntent = PendingIntent.getActivity(context, 0, intent, 0)

    val notification = NotificationCompat.Builder(context, NotificationSetup.PUSH_CHANNEL_ID)
      .setSmallIcon(R.mipmap.ic_launcher)
      .setContentTitle(title)
      .setContentText(message)
      .setContentIntent(linkContentIntent)
      .setAutoCancel(true)
      .build()

    notificationManager.notify(1, notification)
    return true
  }
}
