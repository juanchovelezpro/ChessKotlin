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

        chess.board[3][3].piece = Knight(Coordinate(3,3),Team.WHITE)

        boardPanel = BoardPanel(this)

        add(boardPanel, BorderLayout.CENTER)

        isUndecorated = true
    }


}