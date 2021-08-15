package ui

import model.Box
import java.awt.Font
import java.awt.GridLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingConstants

class BoardPanel(val window: Window): JPanel() {

    val board = Array(8) { Array(8) { JLabel() } }

    init {

        layout = GridLayout(8,8)

        drawBoard(window.chess.board)

    }

    fun drawBoard(boardChess: Array<Array<Box>>){

        removeAll()

        updateBoard(boardChess)

        for(i in 0..board.lastIndex){
            for(j in 0..board[0].lastIndex){
                add(board[i][j])
            }
        }

        revalidate()
        repaint()
    }



    private fun updateBoard(boardChess : Array<Array<Box>>){

        for (i in 0..boardChess.lastIndex){
            for(j in 0..boardChess[0].lastIndex){

                board[i][j].font = Font(board[i][j].font.name, Font.PLAIN, 48)
                board[i][j].text = boardChess[i][j].piece?.shape
                board[i][j].background = boardChess[i][j].color
                board[i][j].horizontalAlignment = SwingConstants.CENTER


                board[i][j].isOpaque = true

            }
        }
    }



}