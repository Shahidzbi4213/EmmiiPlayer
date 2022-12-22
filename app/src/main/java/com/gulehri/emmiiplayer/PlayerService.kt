package com.gulehri.emmiiplayer

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder


// Created by Shahid Iqbal on 12/22/2022.

class PlayerService : Service() {

    private val localBinder = LocalBinder()
    lateinit var mediaPlayer: MediaPlayer

    override fun onBind(p0: Intent?): IBinder = localBinder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.ACTION_START -> {

                mediaPlayer = MediaPlayer().apply {
                    setAudioAttributes(
                        AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
                    )
                    setDataSource(Songs.list[0])
                    prepareAsync()
                }
                startForeground(
                    11, NotificationViewer.notification(0, this@PlayerService)
                )
            }
            Actions.ACTION_PLAY -> {
                if (!mediaPlayer.isPlaying) mediaPlayer.start()
                else mediaPlayer.pause()

                if (mediaPlayer.isPlaying) {
                    NotificationViewer.updateNotification(
                        mediaPlayer.duration, this, R.drawable.ic_pause
                    )
                } else {
                    NotificationViewer.updateNotification(
                        mediaPlayer.duration, this, R.drawable.ic_play
                    )
                }


            }
            Actions.ACTION_FF -> {
                mediaPlayer.apply {
                    val ff = currentPosition + 5000
                    if (ff < duration) seekTo(ff)
                    NotificationViewer.updateNotification(
                        ff,
                        this@PlayerService,
                        defaultIcon = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play
                    )
                }
            }
            Actions.ACTION_RR -> {
                mediaPlayer.apply {
                    val rr = if (currentPosition > 5000) currentPosition - 5000 else 0

                    seekTo(rr)
                    NotificationViewer.updateNotification(
                        rr,
                        this@PlayerService,
                        defaultIcon = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play
                    )
                }
            }
            Actions.ACTION_CANCEL -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    stopForeground(Service.STOP_FOREGROUND_REMOVE)
                } else stopForeground(true)
                stopSelf()
            }
            else -> stopSelf()
        }
        return START_STICKY
    }


    inner class LocalBinder : Binder() {
        fun getEmmiPlayer(): PlayerService = this@PlayerService
    }

}