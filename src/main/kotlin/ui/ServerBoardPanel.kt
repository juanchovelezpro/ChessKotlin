package ui

import model.Chess
import network.NetworkObserver
import network.Packet
import network.Server
import java.net.Socket
import kotlin.concurrent.thread

class ServerBoardPanel(window: Window) : BoardPanel(window), NetworkObserver {

    val server = Server(20980, this)


    init {
        thread {
            server.start()
        }
    }


    override fun onConnection(socket: Socket?) {
        drawBoard(window.chess.board)
    }

    override fun onDataReceived(packet: Packet?) {
        val chess = packet?.content as Chess
        window.chess = chess
        window.chess.observer = this
        drawBoard(window.chess.board)
    }

    override fun onDataSent(packet: Packet?) {

    }

    override fun onClose() {

    }


}