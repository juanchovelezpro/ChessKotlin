package ui

import utils.ImageLoader
import java.awt.*
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.border.BevelBorder
import kotlin.system.exitProcess

class InitialPanel(val window: Window) : JPanel() {

    val background = ImageLoader.loadImage("background.jpg")

    var btnPlay = JButton("P L A Y")

    var btnSolo = JButton("S O L O")
    var btnCreateMatch = JButton("CREATE A MATCH")
    var btnFindMatch = JButton("FIND A MATCH")
    var btnExit = JButton("E X I T")
    var labWaiting = JLabel("Waiting for an opponent...")

    var btnBack = JButton("<")

    var soloBoardPanel: BoardPanel? = null
    var serverBoardPanel: ServerBoardPanel? = null
    var clientBoardPanel: ClientBoardPanel? = null


    init {
        initComponents()
        listeners()
        showMainOptions()
    }

    private fun initComponents() {

        layout = null

        val size = Dimension(Window.WIDTH / 2, Window.HEIGHT / 16)
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

        // Waiting for an opponent label
        labWaiting.size = Dimension(Window.WIDTH, 60)
        labWaiting.location = Point(Window.WIDTH / 2 - size.width / 2 + 20, Window.HEIGHT / 2 - labWaiting.height / 2)
        labWaiting.font = Window.FONT
        labWaiting.foreground = Color.BLACK
        labWaiting.background = backgroundColor

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
            initCreateMatch()
        }

        btnFindMatch.addActionListener {
            initFindMatch()
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
        layout = BorderLayout()
        window.transform()
        soloBoardPanel = BoardPanel(window)
        window.chess.observer = soloBoardPanel
        soloBoardPanel?.drawBoard(window.chess.board)
        add(soloBoardPanel)
        window.adjust()
        window.refresh()
    }

    private fun initCreateMatch() {
        removeAll()
        layout = BorderLayout()
        window.transform()
        waitingForOpponents()
        window.isResizable = false
        serverBoardPanel = ServerBoardPanel(window)
        window.chess.observer = serverBoardPanel
        serverBoardPanel?.isOpaque = true
        add(serverBoardPanel)
        window.adjust()
        window.refresh()
    }

    private fun initFindMatch() {
        removeAll()
        layout = BorderLayout()
        window.transform()
        window.isResizable = false
        clientBoardPanel = ClientBoardPanel(window)
        clientBoardPanel?.isOpaque = true
        window.chess.observer = clientBoardPanel
        add(clientBoardPanel)
        window.adjust()
        window.refresh()
    }

    private fun waitingForOpponents() {
        remove(btnSolo)
        remove(btnBack)
        remove(btnFindMatch)
        remove(btnCreateMatch)
        add(labWaiting)
    }

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        g?.drawImage(background, 0, 0, null)
        repaint()
    }

}