package com.example.mygame2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import com.example.mygame2.R

class GameView(var c:Context,var gameTask: GameTask):View(c) {

    private var myPaint: Paint? = null
    private var speed = 1
    private var time = 0
    private var score = 0
    private var dogPosition = 0
    private var otherbomb = ArrayList<HashMap<String,Any>>()

    var viewWidth =0
    var viewHeight = 0

    init {
        myPaint = Paint()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        viewWidth = this.measuredWidth
        viewHeight = this.measuredHeight

        if(time % 700 < 10 +speed){
            val map = HashMap<String,Any>()
            map["lane"] = (0..2).random()
            map["startTime"]=time
            otherbomb.add(map)
        }

        time = time +10 + speed
        val bombwidth = viewWidth /5
        val bombHeight = bombwidth +10
        myPaint!!.style = Paint.Style.FILL
        val d = resources.getDrawable(R.drawable.dog,null)

        d.setBounds(
            dogPosition * viewWidth / 3 + viewWidth / 15 +25,
            viewHeight-2-bombHeight,
            dogPosition * viewWidth /3 + viewWidth / 15 + bombwidth - 25,
            viewHeight - 2
        )
        d.draw(canvas!!)
        myPaint!!.color = Color.GREEN
        var highScore = 0

        for(i in otherbomb.indices){
            try{
                val dogX = otherbomb[i]["lane"] as Int * viewWidth / 3+ viewWidth / 15
                var dogY = time-otherbomb[i]["startTime"] as Int
                val d2 = resources.getDrawable(R.drawable.bomb,null)

                d2.setBounds(
                    dogX+25 , dogY - bombHeight,dogX + bombwidth -25,dogY
                )
                d2.draw(canvas)
                if (otherbomb[i]["lane"] as Int == dogPosition){
                    if(dogY > viewHeight - 2 - bombHeight
                        && dogY < viewHeight-2){

                        gameTask.closeGame(score)
                    }

                }
                if(dogY > viewHeight + bombHeight){
                    otherbomb.removeAt(i)
                    score++
                    speed = 1 + Math.abs(score/ 8)
                    if(score > highScore){
                        highScore = score
                    }
                }
            }
            catch (e:Exception){
                e.printStackTrace()
            }
        }

        myPaint!!.color = Color.WHITE
        myPaint!!.textSize = 40f
        canvas.drawText("Score : $score",80f,80f,myPaint!!)
        canvas.drawText("Speed : $speed",380f,80f,myPaint!!)
        invalidate()

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event!!.action){
            MotionEvent.ACTION_DOWN ->{
                val x1= event.x
                if(x1 < viewWidth/2){
                    if(dogPosition > 0){
                        dogPosition--
                    }

                }
                if(x1 > viewWidth /2){
                    if(dogPosition < 2){
                        dogPosition++
                    }
                }
                invalidate()
            }
            MotionEvent.ACTION_UP -> {

            }

        }
        return true

    }

}