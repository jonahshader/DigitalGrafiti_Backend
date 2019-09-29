package difti.networkinterface

import difti.GImage
import difti.Pixel
import difti.display.Display
import processing.core.PApplet
import processing.data.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread

class SocketServer(val disp: Display, val app: PApplet) {
    private val server = ServerSocket(1337)

    init {
        thread {
            while (true) {
                val client = server.accept()
                thread {
                    ClientHandler(client, app, disp).run()
                }
            }
        }
    }
}

private class ClientHandler(val client: Socket, val app: PApplet, val disp: Display) {
    private var active: Boolean = false
    fun run() {
        active = true
        val inp = client.getInputStream()
        val br = BufferedReader(InputStreamReader(inp))
        var l = String()
        while (l != null) {
            l += br.readLine()
        }
        disp.addImage(mkImgFromPixArray(l, app))
    }
}

private fun mkImgFromPixArray(obj: JSONObject, app: PApplet) : difti.GImage {
    val h = obj.getFloat("height")
    val w = obj.getFloat("width")
    val x_center = obj.getFloat("x")
    val y_center = obj.getFloat("y")
    val rotation = obj.getFloat("rotation")
    val pixArray = obj.getJSONArray("rgba")
    var img = ArrayList<ArrayList<Pixel>>()
    var tAr = ArrayList<Pixel>()
    for (i in 0..pixArray.size()-1) {
        if ((i%(pixArray.size()/w)).equals(0)) {
            img.add(tAr)
            tAr = ArrayList<Pixel>()
        }
        if (i%4 == 0) {
           val pix = Pixel(Integer.parseInt(pixArray[i].toString()), Integer.parseInt(pixArray[i+1].toString()), Integer.parseInt(pixArray[i+2].toString()), Integer.parseInt(pixArray[i+3].toString()))
           tAr.add(pix)
        }
    }
    val i = difti.PixelArrayImage(img)
    return GImage(app, i, x_center, y_center, rotation)
}
