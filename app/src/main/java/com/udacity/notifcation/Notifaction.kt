package com.udacity.notifcation

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.udacity.ui.activities.DetailActivity
import com.udacity.R
import com.udacity.utli.CONSTANTS

private val NOTIFICATION_ID = 0


@SuppressLint("UnspecifiedImmutableFlag")
fun NotificationManager.sendNotification(applicationContext: Context, notificationText:String,fileName:String,status:String){

    val contentIntent = Intent(applicationContext, DetailActivity::class.java)

    contentIntent.putExtra(CONSTANTS.FILENAME,fileName)
    contentIntent.putExtra(CONSTANTS.STATUS,status)
    contentIntent.putExtra(CONSTANTS.DESC,status)

    val contentPendingIntent = PendingIntent.getActivity(applicationContext
    ,NOTIFICATION_ID,
      contentIntent,
      PendingIntent.FLAG_UPDATE_CURRENT
    )

    val notificationImage = BitmapFactory.decodeResource(applicationContext.resources
        ,R.drawable.notification)

    val bigPictureStyle = NotificationCompat.BigPictureStyle()
        .bigPicture(notificationImage)
        .bigLargeIcon(null)


    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.channel_id)

    ).setContentText("file $fileName downloaded")
        .setSmallIcon(R.drawable.notification)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)
        .setStyle(bigPictureStyle)
        .setLargeIcon(notificationImage)

 notify(NOTIFICATION_ID,builder.build())

}


fun NotificationManager.cancelNotifications() {
    cancelAll()
}



@RequiresApi(Build.VERSION_CODES.O)
fun NotificationManager.downloadStatusChannel(context: Context) {
    Build.VERSION.SDK_INT.takeIf { it >= Build.VERSION_CODES.O }?.run {
        NotificationChannel(
            context.getString(R.string.channel_id),
            context.getString(R.string.channel_name),
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = context.getString(R.string.notification_description)
            setShowBadge(true)
        }.run {
            createNotificationChannel(this)
        }
    }


}

 fun NotificationManager.createChannel(channelId: String, channelName: String,desc:String) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        )
            .apply {
                setShowBadge(false)
            }

        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        notificationChannel.enableVibration(true)
        notificationChannel.description = desc

        this.createNotificationChannel(notificationChannel)
    }
}