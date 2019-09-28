package difti.networkinterface

import difti.display.Display
import processing.data.JSONArray
import java.util.*
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread

class SocketServer(disp: Display) {
    private val server = ServerSocket(1337)

    init {
        thread {
            while (true) {
                val client = server.accept()
                thread {
                    ClientHandler(client).run()
                }
            }
        }
    }
}

private class ClientHandler(client: Socket) {
    private val client: Socket = client
    private var active: Boolean = false
    fun run() {
        active = true
        while (active) {

        }
    }
}

private fun pixArrayImage(obj: JSONArray) {

}
