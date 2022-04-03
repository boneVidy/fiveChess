package com.example.fivechess

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.math.roundToInt

/**
 * 棋盘布局视图，负责渲染棋盘和棋子s
 */
class Checkerboard(context: Context?) : View(context) {
    private var chessMargin: Int = 0
    private var PADDING_LEFT: Int = 0
    private var PADDING_TOP: Int = 0
    private var chessVector = mutableListOf<MutableList<ChessType>>()
    val cols = 15;
    val rows = 15;
    private var onCheckboardTouch: OnCheckboardTouch? = null
    fun setOnCheckboardTouchListener(value: OnCheckboardTouch) {
        onCheckboardTouch = value
    }
    private val startX: Float
        get() = PADDING_LEFT.toFloat()
    private val startY: Float
        get() = PADDING_TOP.toFloat()

    private val endX: Float
        get() = ((cols - 1) * chessMargin + PADDING_LEFT).toFloat()

    private val endY: Float
        get() = (chessMargin * (rows - 1) + PADDING_TOP).toFloat()

    fun getChess (col:Int, row: Int): ChessType {
        return chessVector[col][row]
    }
    fun setChess(col: Int, row: Int, type: ChessType) {
        chessVector[col][row] = type
        invalidate()
    }

    init {
        setBackgroundColor(Color.YELLOW)
        chessVector = MutableList(rows) { initRow() }
    }

     fun resetChess () {
        chessVector = MutableList(rows) { initRow() }
         invalidate()
    }
    private fun initRow(): MutableList<ChessType> {
        return MutableList(cols) {
            ChessType.NONE
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            val x = event.x
            val y = event.y
            Log.d("touch", """$x , $y""")
            if (x in (startX - chessMargin/2)..(endX + chessMargin/2) && y in (startY-chessMargin/2)..(endY + chessMargin/2)) {
                Log.d("checkboard touch", """$x , $y""")
                val col = ((x - startX) / chessMargin).roundToInt()
                val row = ((y- startY) / chessMargin).roundToInt()
                onCheckboardTouch?.accept( col, row)
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas) {
        initBoard()
        drawLine(canvas)
        drawChess(canvas)

    }

    /**
     * 画线条
     */
    private fun drawLine(canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.BLACK
        // 画横线
        for (i in 0 until rows) {
            canvas.drawLine(
                PADDING_LEFT.toFloat(),
                (i * chessMargin + PADDING_TOP).toFloat(),
                ((cols - 1) * chessMargin + PADDING_LEFT).toFloat(),
                (i * chessMargin + PADDING_TOP).toFloat(), paint
            )
        }
        // 画竖线
        for (i in 0 until cols) {
            canvas.drawLine(
                (PADDING_LEFT + i * chessMargin).toFloat(),
                PADDING_TOP.toFloat(),
                (PADDING_LEFT + i * chessMargin).toFloat(),
                (chessMargin * (rows - 1) + PADDING_TOP).toFloat(), paint
            )
        }
    }

    private fun initBoard() {
        val dm = resources.displayMetrics
        val screenWidth = dm.widthPixels // 屏幕宽度
        val screenHeight = dm.heightPixels //屏幕高度
        PADDING_LEFT = (screenWidth / cols - 1) / 2
        PADDING_TOP = (screenHeight / rows - 1) / 2
        val PADDING = PADDING_LEFT.coerceAtMost(PADDING_TOP)
        val ROW_MARGIN = ((screenHeight - PADDING * 2)) / (rows - 1)
        val COL_MARGIN = (screenWidth - PADDING * 2) / (cols - 1)
        chessMargin = ROW_MARGIN.coerceAtMost(COL_MARGIN)
        PADDING_LEFT = (screenWidth - (cols - 1) * chessMargin) / 2
        PADDING_TOP = (screenHeight - (rows - 1) * chessMargin) / 2
    }

    private fun getChessSize(): Float {
        return (chessMargin / 2).toFloat()
    }

    /**
     * 画棋子
     */
    private fun drawChess(canvas: Canvas) {
        val paint = Paint()
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                val pos = chessVector[r][c]
                if (pos == ChessType.NONE) {
                    continue
                }
                val startX = r * chessMargin + PADDING_LEFT
                val startY = c * chessMargin + PADDING_TOP
                if (pos == ChessType.BLACK) {
                    paint.color = Color.BLACK
                } else {
                    paint.color = Color.WHITE
                }
                canvas.drawCircle(startX.toFloat(), startY.toFloat(), getChessSize(), paint)
            }
        }

    }
    fun interface OnCheckboardTouch {
        fun accept(c: Int, r: Int);
    }

}
