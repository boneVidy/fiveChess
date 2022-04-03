/**
 * 输赢判断管理模块
 */
package com.example.fivechess

/**
 * 判断当前落子的位置是否可以让玩家赢棋
 */
fun isWin (col: Int, row: Int, type: ChessType, checkerboard:Checkerboard):Boolean {
	// 总线判断是否五子连线
	if (check5ChessInVerticalLine(col, row, type, checkerboard)) {
		return true
	}
	// 横向判断是否五子连线
	if (check5ChessInHorizonLine (col,row,type, checkerboard)) {
		return true
	}
	// 正斜向判断是否五子连线
	if (check5ChessInObliqueLine(col, row, type, checkerboard)) {
		return true
	}
	// 反斜向判断是否五子连线
	if (check5ChessInReverseObliqueLine(col, row, type, checkerboard)) {
		return true
	}
	return false
}

/**
 * 纵向判断输赢
 */
private fun check5ChessInVerticalLine(col: Int, row: Int, chessType: ChessType, checkerboard:Checkerboard):Boolean {
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
private fun check5ChessInHorizonLine(col: Int, row: Int, chessType: ChessType, checkerboard:Checkerboard):Boolean {
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
private fun check5ChessInObliqueLine(col: Int, row: Int, chessType: ChessType, checkerboard:Checkerboard):Boolean {
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
	// 向下搜索
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
private fun check5ChessInReverseObliqueLine (col: Int, row: Int, chessType: ChessType, checkerboard:Checkerboard):Boolean {
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
