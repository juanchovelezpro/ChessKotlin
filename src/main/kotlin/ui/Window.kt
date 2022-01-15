package ui

import model.Chess
import utils.ImageLoader
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Font
import javax.swing.JFrame

class Window : JFrame() {

    companion object {
        val WIDTH = 600
        val HEIGHT = 600
        val FONT = Font("Garamond", Font.BOLD, 24)
    }

    var chess: Chess
    var initialPanel: InitialPanel

    init {
        title = "Chess Game"
        layout = BorderLayout()
        defaultCloseOperation = EXIT_ON_CLOSE
        size = Dimension(WIDTH, HEIGHT)
        preferredSize = Dimension(WIDTH, HEIGHT)
        setLocationRelativeTo(null)
        iconImage = ImageLoader.loadImage("logo.png")
        isResizable = false
        chess = Chess()

        initialPanel = InitialPanel(this)
        add(initialPanel)

    }

    fun refresh() {
        revalidate()
        repaint()
    }

    fun transform() {
        dispose()
        isVisible = true
    }

    fun adjust() {
        pack()
        setLocationRelativeTo(null)
    }


}