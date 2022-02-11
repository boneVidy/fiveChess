package com.example.fivechess

import android.app.Activity
import android.content.Context
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

/**
 * 五子棋视图，负责棋盘的游戏逻辑
 */
class GameView(context: Context) : FrameLayout(context) {
    private val checkerboard:Checkerboard = Checkerboard(context)
    private var isBlack:Boolean = true;
    init {
        addView(checkerboard)
        checkerboard.setOnCheckboardTouchListener{ col,row ->
            val currentPosType = checkerboard.getChess(col,row)
            if (currentPosType == ChessType.NONE) {
                val type = if (isBlack) ChessType.BLACK else ChessType.WHITE
                checkerboard.setChess(col, row, type)
                val hasWin = isWin(col, row, type)
                if (hasWin) {
                    showResultDialog(type, context)
                    return@setOnCheckboardTouchListener
                }
                isBlack = !isBlack
            } else {
                Toast.makeText(
                    context,
                    context.getString(R.string.has_chess_there),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun showResultDialog(
        type: ChessType,
        context: Context
    ) {
        val player =
            if (type == ChessType.WHITE) context.getString(R.string.whiteChess) else context.getString(
                R.string.blackChess
            )
        AlertDialog.Builder(context)
            .setMessage("$player 赢了 \n" + context.getString(R.string.restart))
            .setNegativeButton(context.getString(R.string.back_to_menu)) { _, _ ->
                back(context)
            }
            .setPositiveButton(context.getString(R.string.ok)) { _, _ ->
                restart()
            }
            .create()
            .show()
    }

    private fun back(context: Context) {
        if (context is Activity) {
            context.finish()
        }
    }

    private fun restart() {
        checkerboard.resetChess()
    }

    private fun isWin (col: Int, row: Int, type: ChessType):Boolean {
        if (check5ChessInVerticalLine(col, row, type)) {
            return true
        }
        if (check5ChessInHorizonLine (col,row,type)) {
            return true
        }
        if (check5ChessInObliqueLine(col, row, type)) {
            return true
        }
        if (check5ChessInReverseObliqueLine(col, row, type)) {
            return true
        }
        return false
    }

    /**
     * 纵向判断输赢
     */
    private fun check5ChessInVerticalLine(col: Int, row: Int, chessType: ChessType):Boolean {
        var count = 0
        for (i in row + 1 until row + 5) {
            if (i > checkerboard.rows) {
                break;
            }
            if (checkerboard.getChess(col, i) == chessType) {
                count ++;
            } else {
                break

            }
        }
        for (i in row downTo row-5) {
            if (i < 0) {
                break
            }
            if (checkerboard.getChess(col, i) == chessType) {
                count ++
            } else {
                break
            }
        }

        return count >= 5
    }

    /**
     * 横向判断输赢
     */
    private fun check5ChessInHorizonLine(col: Int, row: Int, chessType: ChessType):Boolean {
        var count = 0
        for (i in col + 1 until col + 5) {
            if (i > checkerboard.rows) {
                break;
            }
            if (checkerboard.getChess(i, row) == chessType) {
                count ++;
            } else {
                break

            }
        }
        for (i in col downTo col-5) {
            if (i < 0) {
                break
            }
            if (checkerboard.getChess(i, row) == chessType) {
                count ++
            } else {
                break
            }
        }

        return count >= 5
    }

    /**
     * 斜线判断输赢
     */
    private fun check5ChessInObliqueLine(col: Int, row: Int, chessType: ChessType):Boolean {
        var count = 1
        // 向上搜索
        var c = col - 1
        var r = row - 1
        while (c >= 0 && r  >= 0) {
            if (checkerboard.getChess(c, r) == chessType) {
                count ++
                c --
                r --
            } else {
                break
            }
        }
        // 向下搜哟
        c = col + 1
        r = row + 1
        while (c < checkerboard.cols && r < checkerboard.rows) {
            if (checkerboard.getChess(c, r) == chessType) {
                count ++
                c ++
                r ++
            } else{
                break
            }
        }
        return count >= 5
    }

    /**
     * 反斜线判断输赢
     */
    private fun check5ChessInReverseObliqueLine (col: Int, row: Int, chessType: ChessType):Boolean {
        var count = 1
        // 向上搜索
        var c = col + 1
        var r = row - 1
        while (c < checkerboard.cols && r  >= 0) {
            if (checkerboard.getChess(c, r) == chessType) {
                count ++
                c ++
                r --
            } else {
                break
            }
        }
        // 正向搜索
        c = col -1
        r = row + 1
        while (c >= 0 && r < checkerboard.rows) {
            if (checkerboard.getChess(c, r) == chessType) {
                count ++
                c --
                r ++
            } else{
                break
            }
        }
        return count >= 5
    }

}