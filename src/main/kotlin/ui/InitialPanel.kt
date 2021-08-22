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
    var btnCreateMatch = JButton("C R E A T E  M A T C H")
    var btnFindMatch = JButton("F I N D  A  M A T C H")
    var btnExit = JButton("E X I T")

    var btnBack = JButton("<<<")

    var boardPanel: BoardPanel? = null


    init {
        initComponents()
        listeners()
        showMainOptions()
    }

    private fun initComponents() {

        layout = null

        val size = Dimension(Window.WIDTH / 3, Window.HEIGHT / 16)
        val backgroundColor = Color(0, 0, 0, 100)
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

        // Button Back
        btnBack.size = Dimension(Window.WIDTH / 12, Window.HEIGHT / 16)
        btnBack.location = Point(10, 10)
        btnBack.font = Window.FONT
        btnBack.foreground = Color.WHITE
        btnBack.background = backgroundColor
        btnBack.isFocusPainted = false
        btnBack.border = border

        // Button solo
        btnSolo.size = size
        btnSolo.location = Point(Window.WIDTH / 2 - size.width / 2, Window.HEIGHT / 2 - size.height / 2)
        btnSolo.font = Window.FONT
        btnSolo.foreground = Color.WHITE
        btnSolo.background = backgroundColor
        btnSolo.isFocusPainted = false
        btnSolo.border = border

        // Button LAN Match
        btnCreateMatch.size = size
        btnCreateMatch.location = Point(Window.WIDTH / 2 - size.width / 2, btnSolo.y + size.height + 20)
        btnCreateMatch.font = Window.FONT
        btnCreateMatch.foreground = Color.WHITE
        btnCreateMatch.background = backgroundColor
        btnCreateMatch.isFocusPainted = false
        btnCreateMatch.border = border

        // Button Find Match
        btnFindMatch.size = size
        btnFindMatch.location = Point(Window.WIDTH / 2 - size.width / 2, btnCreateMatch.y + size.height + 20)
        btnFindMatch.font = Window.FONT
        btnFindMatch.foreground = Color.WHITE
        btnFindMatch.background = backgroundColor
        btnFindMatch.isFocusPainted = false
        btnFindMatch.border = border


    }

    private fun showMainOptions() {
        add(btnPlay)
        add(btnExit)
    }

    private fun addGameModes() {
        add(btnBack)
        add(btnSolo)
        add(btnCreateMatch)
        add(btnFindMatch)
    }


    private fun listeners() {

        btnExit.addActionListener {
            exitProcess(0)
        }

        btnPlay.addActionListener {
            playPressed()
        }

        btnBack.addActionListener {
            backPressed()
        }

        btnSolo.addActionListener {
            initSoloMatch()
        }

        btnCreateMatch.addActionListener {

        }

    }

    private fun playPressed() {
        removeAll()
        addGameModes()
        window.refresh()
    }

    private fun backPressed() {
        removeAll()
        showMainOptions()
        window.refresh()
    }

    private fun initSoloMatch() {
        removeAll()
        boardPanel = BoardPanel(window)
        boardPanel?.isOpaque = true
        add(boardPanel)
        window.refresh()
    }

    private fun initCreateMatch() {
        removeAll()


        window.refresh()
    }

    private fun initFindMatch() {

    }

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        g?.drawImage(background, 0, 0, null)
        repaint()
    }

}