package network

import model.Chess
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.Socket

class Client(val host: String, val port: Int) : NetworkObserver {

    var socket : Socket? = null
    var dataOutput: ObjectOutputStream? = null
    var dataInput: ObjectInputStream? = null

    fun start(){
        socket = Socket(host,port)
        dataOutput = ObjectOutputStream(socket?.getOutputStream())
        dataInput = ObjectInputStream(socket?.getInputStream())
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

fun main(args: Array<String>){
    val client = Client("192.168.1.8", 20980)
    try {
        client.start()
    }catch (ex : Exception){
        println("Something went wrong\n${ex.localizedMessage}")
    }
}