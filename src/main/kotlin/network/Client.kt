package network

import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.Socket

class Client(private val host: String, private val port: Int, val observer: NetworkObserver? = null) : NetworkObserver {

    var socket: Socket? = null
    var dataOutput: ObjectOutputStream? = null
    var dataInput: ObjectInputStream? = null
    private lateinit var sender: Sender
    private lateinit var receiver: Receiver

    fun start() {
        socket = Socket(host, port)
        if (socket!!.isConnected) onConnection(socket)
        dataOutput = ObjectOutputStream(socket?.getOutputStream())
        dataInput = ObjectInputStream(socket?.getInputStream())
        sender = Sender(dataOutput!!, this)
        sender.start()
        receiver = Receiver(dataInput!!, this)
        receiver.start()
    }

    override fun onConnection(socket: Socket?) {
        println("Connected!")
        observer?.onConnection(socket)
    }

    override fun onDataReceived(packet: Packet?) {
        println("Packet received! -> $packet")
        observer?.onDataReceived(packet)
    }

    override fun onDataSent(packet: Packet?) {
        println("Packet sent! -> $packet")
        observer?.onDataSent(packet)
    }

    override fun onClose() {
        println("Closing connection...")
        socket?.close()
        observer?.onClose()
    }

}

fun main(args: Array<String>) {
    val client = Client("192.168.1.8", 9812)
    try {
        client.start()
    } catch (ex: Exception) {
        println("Something went wrong\nError:${ex.localizedMessage}")
        ex.printStackTrace()
    }
}