package difti.networkinterface

import difti.display.Display
import processing.data.JSONArray
import java.util.*
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread

//TODO: get out of main function
fun socketserver() {
    val server = ServerSocket(1337)
    while (true) {
        val client = server.accept()
        thread {
            ClientHandler(client).run()
        }
    }
}

class ClientHandler(client: Socket, disp: Display) {
    private val client: Socket = client
    private var active: Boolean = false
    fun run() {
        active = true
        while (active) {

        }
    }
}

fun pixArrayImage(obj: JSONArray) {

}
