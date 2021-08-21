package ui

import model.Chess
import java.awt.BorderLayout
import java.awt.Font
import java.awt.Toolkit
import javax.swing.JFrame

class Window : JFrame() {

    companion object {
        val WIDTH = Toolkit.getDefaultToolkit().screenSize.width
        val HEIGHT = Toolkit.getDefaultToolkit().screenSize.height
        val FONT = Font("Garamond", Font.BOLD, 52)
    }

    lateinit var boardPanel: BoardPanel
    lateinit var chess: Chess
    lateinit var initialPanel: InitialPanel

    init {
        title = "Chess Game"
        layout = BorderLayout()
        defaultCloseOperation = EXIT_ON_CLOSE
        //extendedState = MAXIMIZED_BOTH
        setSize(500, 500)
        setLocationRelativeTo(null)

        //initialPanel = InitialPanel(this)
        //add(initialPanel)

        chess = Chess()

        boardPanel = BoardPanel(this)

        chess.observer = boardPanel

        add(boardPanel, BorderLayout.CENTER)

        isUndecorated = true
    }

}