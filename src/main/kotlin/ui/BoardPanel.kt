package ui

import model.*
import java.awt.Font
import java.awt.GridLayout
import java.io.Serializable
import javax.swing.JLabel
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.SwingConstants
import javax.swing.border.BevelBorder

open class BoardPanel(val window: Window) : JPanel(), ChessActions, Serializable{

    lateinit var board: Array<Array<JLabel>>

    init {
        layout = GridLayout(8, 8)
    }

    fun drawBoard(boardChess: Array<Array<Box>>) {
        removeAll()
        updateBoard(boardChess)
        revalidate()
        repaint()
    }

    private fun updateBoard(boardChess: Array<Array<Box>>) {

        board = Array(8) { Array(8) { JLabel() } }

        for (i in 0..boardChess.lastIndex) {
            for (j in 0..boardChess[0].lastIndex) {

                board[i][j].font = Font(board[i][j].font.name, Font.PLAIN, 64)
                board[i][j].text = boardChess[i][j].piece?.shape
                board[i][j].background = boardChess[i][j].color
                board[i][j].border = BevelBorder(BevelBorder.RAISED)
                board[i][j].horizontalAlignment = SwingConstants.CENTER
                board[i][j].addMouseListener(MouseBoxAdapter(this, boardChess[i][j]))

                board[i][j].isOpaque = true

                add(board[i][j])

            }
        }
    }

    override fun onKill(murdered: Piece) {

    }

    override fun onMovement(from: Coordinate, to: Coordinate, piece: Piece) {

    }

    override fun onPromotion(pawn: Pawn, promPosition: Coordinate): Piece {

        val options = arrayOf("Queen","Knight","Bishop","Rook")
        val piece = JOptionPane.showInputDialog(window,"Which piece you want?","Promotion", JOptionPane.QUESTION_MESSAGE,null,options,options[0])

        return when(piece){
            "Rook" -> Rook(promPosition,pawn.team,pawn.observer)
            "Bishop" -> Bishop(promPosition, pawn.team, pawn.observer)
            "Knight" -> Knight(promPosition, pawn.team, pawn.observer)
            else -> Queen(promPosition,pawn.team,pawn.observer)
        }

    }

    override fun onCheck(team: Team) {

    }

    override fun onCheckMate(winner: Team, loser: Team) {

    }

    override fun onTie() {

    }

    override fun onTurnChanged() {

    }

}