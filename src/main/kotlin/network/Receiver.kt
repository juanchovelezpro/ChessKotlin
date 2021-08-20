package network

import java.io.ObjectInputStream

class Receiver(val inputStream: ObjectInputStream, val observer: NetworkObserver) : Thread() {

    private fun receive() {
        try {
            while (true) {
                val msg = inputStream.readObject() as Message
                println("MSG: -> ${msg.text}")
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