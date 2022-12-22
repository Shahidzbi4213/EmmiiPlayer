package com.gulehri.emmiiplayer

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.gulehri.emmiiplayer.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var emmiPlayer: PlayerService
    private var isBind = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            startPlayerService(Actions.ACTION_PLAY)
        }


        if (isBind && emmiPlayer.mediaPlayer.isPlaying)
            binding.textView.text = SimpleDateFormat(
                "mm:ss",
                Locale.ENGLISH
            ).format(emmiPlayer.mediaPlayer.currentPosition)

    }


    override fun onStart() {
        startPlayerService(Actions.ACTION_START)
        super.onStart()
    }

    private fun startPlayerService(operation: String) {
        Intent(this, PlayerService::class.java).apply {
            action = operation

            if (operation == Actions.ACTION_START) bindService(
                this,
                connection,
                Service.BIND_AUTO_CREATE
            )

            startService(this)
        }
    }


    private val connection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            emmiPlayer = (p1 as PlayerService.LocalBinder).getEmmiPlayer()
            isBind = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isBind = false
        }

    }
}