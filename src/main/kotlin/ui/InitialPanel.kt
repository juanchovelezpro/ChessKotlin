package ui

import utils.ImageLoader
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Point
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.border.BevelBorder
import kotlin.system.exitProcess

class InitialPanel(val window: Window) : JPanel() {

    val background = ImageLoader.loadImage("background.jpg")

    var btnPlay = JButton("P L A Y")

    var btnSolo = JButton("S O L O")
    var btnMatchLAN = JButton("L A N  M A T C H")
    var btnMatchFind = JButton("F I N D  A  M A T C H")
    var btnExit = JButton("E X I T")

    var boardPanel: BoardPanel? = null


    init{
        initComponents()
        listeners()
    }

    private fun initComponents(){

        layout = null

        val size = Dimension(Window.WIDTH / 3,Window.HEIGHT / 16)
        val backgroundColor = Color(0,0,0, 100)
        val border = BevelBorder(BevelBorder.RAISED)

        // Button Play
        btnPlay.size = size
        btnPlay.location = Point(Window.WIDTH / 2 - size.width / 2, Window.HEIGHT / 2 - size.height / 2)
        btnPlay.font = Window.FONT
        btnPlay.foreground = Color.WHITE
        btnPlay.background = backgroundColor
        btnPlay.isFocusPainted = false
        btnPlay.border = border

        // Button Exit
        btnExit.size = size
        btnExit.location = Point(Window.WIDTH / 2 - size.width / 2, btnPlay.y + size.height + 20)
        btnExit.font = Window.FONT
        btnExit.foreground = Color.WHITE
        btnExit.background = backgroundColor
        btnExit.isFocusPainted = false
        btnExit.border = border

        add(btnPlay)
        add(btnExit)
    }

    private fun listeners(){

        btnExit.addActionListener {
            exitProcess(0)
        }

        btnPlay.addActionListener {

            removeAll()

            boardPanel = BoardPanel(window)
            boardPanel?.isOpaque = true

            add(boardPanel)

            revalidate()
            repaint()

        }
    }

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        g?.drawImage(background,0,0,null)
        repaint()
    }

}