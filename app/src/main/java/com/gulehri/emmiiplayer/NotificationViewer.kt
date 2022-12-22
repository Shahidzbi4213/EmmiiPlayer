package com.gulehri.emmiiplayer

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.media.session.MediaSessionCompat
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.core.graphics.drawable.toBitmap
import java.text.SimpleDateFormat
import java.util.*


// Created by Shahid Iqbal on 12/21/2022.

object NotificationViewer {

    @SuppressLint("NewApi")
    fun updateNotification(
        duration: Int,
        context: Context,
        defaultIcon: Int = 0,
    ) {
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).apply {
            notify(11, notification(duration, context, defaultIcon))
        }
    }

    @SuppressLint("NewApi")
    fun notification(
        duration: Int,
        context: Context,
        @DrawableRes defaultIcon: Int = R.drawable.ic_pause,
    ): Notification {
        return NotificationCompat.Builder(context, "12").setContentTitle("Media Player")
            .setContentText(
                "Player is Start  ${
                    SimpleDateFormat("mm:ss", Locale.ENGLISH).format(
                        duration
                    )
                }"
            ).setSmallIcon(R.drawable.ic_play).setLargeIcon(
                ContextCompat.getDrawable(
                    context, R.drawable.songer
                )!!.toBitmap()
            ).setOnlyAlertOnce(true).addAction(
                NotificationCompat.Action.Builder(
                    IconCompat.createWithResource(context, R.drawable.ic_backward),
                    "Back",
                    rewindAction(context)
                ).build()
            )

            .addAction(
                NotificationCompat.Action.Builder(
                    IconCompat.createWithResource(context, defaultIcon),
                    "Pause",
                    playPauseAction(context)
                ).build()
            )

            .addAction(
                NotificationCompat.Action.Builder(
                    IconCompat.createWithResource(context, R.drawable.ic_forward),
                    "Forward",
                    forwardAction(context)
                ).build()
            ).addAction(
                NotificationCompat.Action.Builder(
                    IconCompat.createWithResource(context, R.drawable.ic_cancel),
                    "cancel",
                    cancelAction(context)
                ).build()
            )
            .setOngoing(true)
            .setAutoCancel(false)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(0, 1, 2)
                    .setMediaSession(MediaSessionCompat(context, "ABC").sessionToken)
            ).build()
    }


    @SuppressLint("InlinedApi")
    fun forwardAction(context: Context): PendingIntent = PendingIntent.getService(
        context,
        1,
        Intent(context, PlayerService::class.java).setAction(Actions.ACTION_FF),
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    @SuppressLint("InlinedApi")
    fun rewindAction(context: Context): PendingIntent = PendingIntent.getService(
        context,
        1,
        Intent(context, PlayerService::class.java).setAction(Actions.ACTION_RR),
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    @SuppressLint("InlinedApi")
    fun playPauseAction(context: Context): PendingIntent = PendingIntent.getService(
        context,
        1,
        Intent(context, PlayerService::class.java).setAction(Actions.ACTION_PLAY),
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    @SuppressLint("InlinedApi")
    fun cancelAction(context: Context): PendingIntent =
        PendingIntent.getService(
            context,
            1,
            Intent(context, PlayerService::class.java).setAction(Actions.ACTION_CANCEL),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
}