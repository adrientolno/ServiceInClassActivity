package edu.temple.myapplication

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.Menu
import android.view.MenuItem
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var timerService: TimerService
     var isServiceBound = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as TimerService.TimerBinder
            timerService = binder.getService()
            isServiceBound = true
        }
        override fun onServiceDisconnected(name: ComponentName?) {
            isServiceBound = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timerService = TimerService()

        findViewById<Button>(R.id.startButton).setOnClickListener {
            if(!isServiceBound) {
                binTimerService()
            }
            timerService.start(20)
        }

        findViewById<Button>(R.id.pauseButton).setOnClickListener {
            timerService.pause()

        }
        
        findViewById<Button>(R.id.stopButton).setOnClickListener {
            timerService.stopSelf()

        }
    }
    private fun binTimerService() {
        val intent = Intent(this, TimerService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

}