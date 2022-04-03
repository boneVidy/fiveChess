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
		// 通过捕获棋盘上的点击事件获取当前所下棋子的坐标
        checkerboard.setOnCheckboardTouchListener{ col,row ->
            val currentPosType = checkerboard.getChess(col,row)
            if (currentPosType == ChessType.NONE) {
                val type = if (isBlack) ChessType.BLACK else ChessType.WHITE
                checkerboard.setChess(col, row, type)
				// 检测在此处落子是否能让玩家赢棋
                val hasWin = isWin(col, row, type, checkerboard)
                if (hasWin) {
                	// 如果确定赢棋则弹窗提示玩家重新开始游戏或者退出游戏
                    showResultDialog(type, context)
                    return@setOnCheckboardTouchListener
                }
				// 如果没有赢棋，则切换玩家下期
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

}
