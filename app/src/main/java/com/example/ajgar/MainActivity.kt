package com.example.ajgar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    val left = mapOf('U' to 'L', 'L' to 'D', 'D' to 'R', 'R' to 'U')
    val right = mapOf('U' to 'R', 'R' to 'D', 'D' to 'L', 'L' to 'U')

    var x1 : Float = 0F
    var y1 : Float = 0F
    var x2 : Float = 0F
    var y2 : Float = 0F
    var dir = 'U'
    var prevdir = 'U'

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val screen : ConstraintLayout = findViewById(R.id.bg) as ConstraintLayout
        val snake : ImageView = findViewById(R.id.snake) as ImageView

        fullScreen()

        val handler = Handler()

        val runnableCode: Runnable = object : Runnable {
            override fun run() {
                if(dir == 'L')
                {
                    snake.x-=7
                }
                else if(dir == 'R')
                {
                    snake.x+=7
                }
                else if(dir == 'U')
                {
                    snake.y-=7
                }
                else
                {
                    snake.y+=7
                }
                handler.postDelayed(this, 200)
            }
        }

        handler.post(runnableCode)

        screen.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View, event: MotionEvent): Boolean {

                if(event.action == MotionEvent.ACTION_DOWN)
                {
                    x1 = event.x
                    y1 = event.y
                }

                else if(event.action == MotionEvent.ACTION_UP) {

                    x2 = event.x
                    y2 = event.y

                    getDirection()
                    moveHead(snake)
                }
                return true
            }
        })

    }

    private fun fullScreen()
    {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)

    }

    private fun getDirection()
    {
        if(abs(x2-x1)> abs(y2-y1))
        {
            if(dir == 'U' || dir == 'D') {
                if (x2 > x1) {
                    dir = 'R'
                } else if (x2 < x1) {
                    dir = 'L'
                }
            }
        }
        else if(abs(x2-x1)< abs(y2-y1))
        {
            if(dir == 'L' || dir == 'R') {
                if (y2 > y1) {
                    dir = 'D'
                } else if (y2 < y1) {
                    dir = 'U'
                }
            }
        }
    }

    private fun moveHead(snake : ImageView)
    {
        if(right[prevdir] == dir)
        {
            snake.rotation += 90
        }

        else if(left[prevdir] == dir)
        {
            snake.rotation -= 90
        }

        prevdir = dir

    }
}