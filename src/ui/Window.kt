package ui

import model.Chess
import java.awt.BorderLayout
import javax.swing.JFrame

class Window : JFrame() {

    var boardPanel: BoardPanel
    var chess: Chess


    init {
        title = "Chess Game"
        layout = BorderLayout()
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(500, 500)
        setLocationRelativeTo(null)

        chess = Chess()

        boardPanel = BoardPanel(this)

        chess.observer = boardPanel

        add(boardPanel, BorderLayout.CENTER)

        isUndecorated = true
    }


}