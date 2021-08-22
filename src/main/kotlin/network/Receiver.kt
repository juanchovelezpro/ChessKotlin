package network

import java.io.ObjectInputStream

class Receiver(private val inputStream: ObjectInputStream, val observer: NetworkObserver) : Thread() {

    private fun receive() {
        try {
            while (true) {
                val packetReceived = inputStream.readObject() as Packet
                observer.onDataReceived(packetReceived)
            }
        } catch (ex: Exception) {
            println("Ups... Looks like something went wrong!\nError:${ex.localizedMessage}")
            ex.printStackTrace()
        } finally {
            inputStream.close()
            observer.onClose()
        }
    }

    override fun run() {
        receive()
    }
}