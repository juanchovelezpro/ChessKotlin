package network

import java.net.Socket

interface NetworkObserver {
    fun onConnection(socket: Socket?)
    fun onDataReceived(packet: Packet?)
    fun onDataSent(packet: Packet?)
    fun onClose()
}