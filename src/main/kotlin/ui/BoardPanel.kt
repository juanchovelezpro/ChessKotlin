package ui

import model.*
import java.awt.Dimension
import java.awt.Font
import java.awt.GridLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingConstants
import javax.swing.border.BevelBorder

class BoardPanel(val window: Window) : JPanel(), ChessActions {

    lateinit var board: Array<Array<JLabel>>

    init {

        layout = GridLayout(8, 8)
        size = Dimension(Window.WIDTH - Window.WIDTH / 3, Window.HEIGHT)

        drawBoard(window.chess.board)
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

                board[i][j].font = Font(board[i][j].font.name, Font.PLAIN, 48)
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

    override fun onPromotion(pawn: Pawn) {

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