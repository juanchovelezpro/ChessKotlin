package ui

import model.*
import java.awt.BorderLayout
import javax.swing.JFrame

class Window : JFrame() {

    var boardPanel: BoardPanel
    var chess: Chess


    init {
        title = "Chess Game"
        layout = BorderLayout()
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(650, 650)
        setLocationRelativeTo(null)

        chess = Chess()

        chess.board[2][6].piece = Rook(Coordinate(2,6), Team.BLACK)
        chess.board[2][3].piece = Rook(Coordinate(2,3), Team.WHITE)
        chess.board[5][6].piece = Pawn(Coordinate(5,6), Team.BLACK)
        chess.board[5][1].piece = Pawn(Coordinate(5,1), Team.BLACK)
        chess.board[3][3].piece = Pawn(Coordinate(3,3), Team.BLACK)
        chess.board[3][5].piece = Pawn(Coordinate(3,5), Team.BLACK)
        chess.board[4][3].piece = Queen(Coordinate(4,3), Team.WHITE)
        chess.board[4][4].piece = Queen(Coordinate(4,4), Team.BLACK)

        boardPanel = BoardPanel(this)

        add(boardPanel, BorderLayout.CENTER)

        isUndecorated = true
    }


}