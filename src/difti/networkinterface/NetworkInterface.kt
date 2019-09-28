package difti.networkinterface

import processing.data.JSONArray
import java.util.*
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread

//TODO:
fun main() {
    val server = ServerSocket(1337)
    while (true) {
        val client = server.accept()
        thread {
            ClientHandler(client).process()
        }
    }
}

class ClientHander(client: Socket) {
    private val client: Socket = client
    private val active: Boolean = false
    fun process() {
        active = true
        while (active) {

        }
    }
}

fun pixArrayImage(obj: JSONArray) {

}
