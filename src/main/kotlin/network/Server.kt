package network

import io.ktor.util.network.*
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.ServerSocket

class Server(val host: String, val port: Int) : NetworkObserver {

    var socket: ServerSocket? = null
    var connectionEstablished = false
    var dataInput: ObjectInputStream? = null
    var dataOutput: ObjectOutputStream? = null

    fun start() {

        socket = ServerSocket(port)
        println("Server on -> ${socket?.inetAddress?.hostAddress}")

        val client = socket?.accept()
        println("Client connected ! : ${client?.remoteSocketAddress?.hostname}")


        connectionEstablished = true
        dataInput = ObjectInputStream(client?.getInputStream())
        dataOutput = ObjectOutputStream(client?.getOutputStream())
        val sender = Sender(dataOutput!!, this)
        sender.start()
        val receiver = Receiver(dataInput!!, this)
        receiver.start()

    }

    override fun onConnection(host: String, port: Int) {
        TODO("Not yet implemented")
    }

    override fun onDataReceived() {
        TODO("Not yet implemented")
    }

    override fun onDataSent() {
        TODO("Not yet implemented")
    }

    override fun onClose() {
        socket?.close()
    }


}

fun main(args: Array<String>) {
    val server = Server("", 20980)
    try {
        server.start()
    } catch (ex: Exception) {
        println("Something went wrong\nError:${ex.localizedMessage}")
    }
}