package ui

import model.Chess
import network.Client
import network.NetworkObserver
import network.Packet
import java.net.Socket
import javax.swing.JOptionPane

class ClientBoardPanel(window: Window) : BoardPanel(window), NetworkObserver {

    var client : Client? = null

    init {
        val address = JOptionPane.showInputDialog(window,"Server Address", "Enter the server address")
        client = Client(address, 20980, this)
        client?.start()
        drawBoard(window.chess.rotateBoard180())
    }

    override fun onConnection(socket: Socket?) {

    }

    override fun onDataReceived(packet: Packet?) {
        val chess = packet?.content as Chess
        window.chess = chess
        window.chess.observer = this
        drawBoard(window.chess.rotateBoard180())
    }

    override fun onDataSent(packet: Packet?) {

    }

    override fun onClose() {

    }
}