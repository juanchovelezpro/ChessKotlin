package network

import java.io.ObjectOutputStream

class Sender(val outputStream: ObjectOutputStream, val observer: NetworkObserver) : Thread() {

    private fun send() {
        try {
            while (true) {
                val msg = Message(readLine()!!)
                val packet = Packet(msg)
                println("ME: -> ${msg.text}")
                outputStream.writeObject(packet)
            }
        } catch (ex: Exception) {
            println("Ups... Looks like something went wrong!\nError:${ex.localizedMessage}")
        } finally {
            outputStream.close()
            observer.onClose()
        }

    }

    override fun run() {
        send()
    }
}