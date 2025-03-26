package com.example.homework.firebase

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.example.homework.MainActivity
import com.example.homework.R
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.Random

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val soundUri: Uri by lazy {
        Uri.parse("android.resource://${applicationContext.packageName}/raw/custom_sound")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val title: String
        val message: String
        val imageUrl: String?

        if (remoteMessage.notification != null) {
            title = remoteMessage.notification?.title ?: "Без заголовка"
            message = remoteMessage.notification?.body ?: "Без текста"
            imageUrl = remoteMessage.notification?.imageUrl?.toString()
        } else {
            title = remoteMessage.data["title"] ?: "Без заголовка"
            message = remoteMessage.data["body"] ?: "Без текста"
            imageUrl = remoteMessage.data["image"]
        }

        showNotification(title, message, imageUrl)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "New Token: $token")
        FirebaseCrashlytics.getInstance().log("New Token: $token")
    }

    private fun showNotification(title: String, message: String, imageUrl: String?) {
        val channelId = "channelOne"
        val notificationManager =
            getSystemService(NotificationManager::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()

            val channel = NotificationChannel(
                channelId,
                "Custom Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                setSound(soundUri, attributes)
                enableLights(true)
                enableVibration(true)
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            }
            notificationManager.createNotificationChannel(channel)
        }

        val fullScreenIntent = Intent(this, MainActivity::class.java)
        val fullScreenPendingIntent = PendingIntent.getActivity(
            this,
            0,
            fullScreenIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setSound(soundUri)
            .setFullScreenIntent(fullScreenPendingIntent, true)

        if (!imageUrl.isNullOrEmpty()) {
            try {
                val bitmap = Glide.with(this).asBitmap().load(imageUrl).submit().get()
                notificationBuilder.setStyle(
                    NotificationCompat.BigPictureStyle().bigPicture(bitmap)
                )
            } catch (e: Exception) {
                FirebaseCrashlytics.getInstance().log("Ошибка загрузки изображения: ${e.message}")
            }
        }

        notificationManager.notify(Random().nextInt(), notificationBuilder.build())
    }
}