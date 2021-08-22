package ui

import model.Chess
import network.Client
import network.NetworkObserver
import network.Packet
import java.net.Socket

class ClientBoardPanel(window: Window) : BoardPanel(window), NetworkObserver {

    val client = Client("192.168.1.8", 20980, this)

    init {
        client.start()
        drawBoard(window.chess.rotateBoard180())
    }

    override fun onConnection(socket: Socket?) {

    }

    override fun onDataReceived(packet: Packet?) {
        val chess = packet?.content as Chess
        window.chess = chess
        drawBoard(window.chess.rotateBoard180())
    }

    override fun onDataSent(packet: Packet?) {

    }

    override fun onClose() {

    }
}