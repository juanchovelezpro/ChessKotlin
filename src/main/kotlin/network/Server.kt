package network

import java.awt.Dimension
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.ServerSocket
import java.net.Socket
import javax.swing.JButton
import javax.swing.JFrame

class Server(private val port: Int, val observer: NetworkObserver? = null) : NetworkObserver {

    var socket: ServerSocket? = null
    var connectionEstablished = false
    private var dataInput: ObjectInputStream? = null
    private var dataOutput: ObjectOutputStream? = null
    private lateinit var sender: Sender
    private lateinit var receiver: Receiver

    fun start() {

        socket = ServerSocket(port)
        println("Server on -> ${socket?.inetAddress?.hostAddress}")

        val client = socket?.accept()
        onConnection(client)

        connectionEstablished = true
        dataInput = ObjectInputStream(client?.getInputStream())
        dataOutput = ObjectOutputStream(client?.getOutputStream())
        sender = Sender(dataOutput!!, this)
        receiver = Receiver(dataInput!!, this)
        receiver.start()

    }

    fun send(packet: Packet){
        sender.send(packet)
    }

    override fun onConnection(socket: Socket?) {
        println("Client connected! -> ${socket?.remoteSocketAddress}")
        observer?.onConnection(socket)
    }

    override fun onDataReceived(packet: Packet?) {
        println("Packet received -> $packet")
        observer?.onDataReceived(packet)
    }

    override fun onDataSent(packet: Packet?) {
        println("Packet sent -> $packet")
        observer?.onDataSent(packet)
    }

    override fun onClose() {
        println("Closing connection...")
        socket?.close()
        observer?.onClose()
    }
}

fun main(args: Array<String>) {
    val server = Server(9812)
    try {
        server.start()

        val window = JFrame()
        window.title = "SERVER"
        window.size = Dimension(200,200)
        val but = JButton("PRESS")
        but.addActionListener { server.send(Packet("Pressing!")) }
        window.add(but)
        window.isVisible = true

    } catch (ex: Exception) {
        println("Something went wrong\nError:${ex.localizedMessage}")
        ex.printStackTrace()
    }
}