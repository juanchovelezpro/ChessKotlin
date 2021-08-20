package network

interface NetworkObserver {
    fun onConnection(host: String, port: Int)
    fun onDataReceived()
    fun onDataSent()
    fun onClose()
}