package com.example.messageuithread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import kotlinx.android.synthetic.main.activity_main.*
import java.util.zip.CheckedOutputStream

class MainActivity : AppCompatActivity() {

    lateinit var mHandler: Handler
    lateinit var mThread: Thread
    private val START = 100
    private val COUNT = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar.max = 100

        start_progress.setOnClickListener {
            if (!mThread.isAlive) {
                mHandler.sendEmptyMessage(START)
            }
        }

        mThread = Thread(Runnable {
            for (i in 0..100) {
                Thread.sleep(100)

                val message = Message()
                message.what = COUNT
                message.arg1 = i

                mHandler.sendMessage(message)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        mHandler = MyHandler(this)
    }

    companion object {
        class MyHandler(private val activity: MainActivity) : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)

                if (msg.what == activity.START) {

                    if (activity.mThread.state == Thread.State.NEW)
                        activity.mThread.start()
                }else if (msg.what == activity.COUNT) {

                    activity.progressBar.progress = msg.arg1
                    activity.tv_count.text = "Count" + msg.arg1

                }
            }
        }

    }
}