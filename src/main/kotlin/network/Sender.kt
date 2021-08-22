package network

import java.io.ObjectOutputStream

class Sender(private val outputStream: ObjectOutputStream, val observer: NetworkObserver) : Thread() {

    fun send(packet: Packet) {
        outputStream.writeObject(packet)
        observer.onDataSent(packet)
    }

    private fun send() {
        try {
            while (true) {
                val data = Packet(readLine()!!)
                outputStream.writeObject(data)
                observer.onDataSent(data)
            }
        } catch (ex: Exception) {
            println("Ups... Looks like something went wrong!\nError:${ex.localizedMessage}")
            ex.printStackTrace()
        } finally {
            outputStream.close()
            observer.onClose()
        }

    }

    override fun run() {
        send()
    }
}