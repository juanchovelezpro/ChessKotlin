package network

import java.io.ObjectInputStream

class Receiver(val inputStream: ObjectInputStream, val observer: NetworkObserver) : Thread() {

    private fun receive() {
        try {
            while (true) {
                val packet = inputStream.readObject() as Packet
                val theMsg = packet.content as Message
                println("MSG: -> ${theMsg.text}")
            }
        }finally {
            inputStream.close()
            observer.onClose()
        }
    }

    override fun run() {
        receive()
    }
}