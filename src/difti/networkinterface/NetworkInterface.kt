package difti.networkinterface

import difti.Pixel
import difti.display.Display
import processing.data.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
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

private class ClientHandler(val client: Socket) {
    private var active: Boolean = false
    fun run() {
        active = true
        val inp = client.getInputStream()
        val br = BufferedReader(InputStreamReader(client))
        var l
        while (l != null) {
            l += br.readLine()
        }

        Display.addImage(mkImgFromPixArray(l))
    }
}

private fun mkImgFromPixArray(obj: JSONObject) : difti.GImage {
    val h = obj.getJSONObject('height')
    val w = obj.getJSONObject('width')
    val x_center = obj.getJSONObject('x')
    val y_center = obj.getJSONObject('y')
    val pixArray = obj.getJSONArray('rgba')
    var img = ArrayList<ArrayList<Pixel>>()
    var tAr = ArrayList<Pixel>()
    for (i in pixArray.indices) {
        if (i%pixArray.size()/w == 0) {
            img.add(tAr)
            tAr = ArrayList<Pixel>()
        }
        if (i%4 == 0) {
           val pix = Pixel(pixArray[i], pixArray[i+1], pixArray[i+2], pixArray[i+3])
           tAr.add(pixArray[i])
        }
    }
    val i = difti.PixelArrayImage(img)
}
