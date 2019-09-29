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
                    ClientHandler(client, disp).run()
                }
            }
        }
    }
}

private class ClientHandler(val client: Socket, val disp: Display) {
    private var active: Boolean = false
    fun run() {
        active = true
        val inp = client.getInputStream()
        val br = BufferedReader(InputStreamReader(inp))
        var l = String()
        while (l != null) {
            l += br.readLine()
        }
        disp.addImage(mkImgFromPixArray(l))
    }
}

private fun mkImgFromPixArray(obj: JSONObject, app: PApplet) : difti.GImage {
    val h = obj.getJSONObject("height")
    val w = obj.getJSONObject("width")
    val x_center = obj.getJSONObject("x")
    val y_center = obj.getJSONObject("y")
    val rotation = obj.getJSONObject("rotation")
    val pixArray = obj.getJSONArray("rgba")
    var img = ArrayList<ArrayList<Pixel>>()
    var tAr = ArrayList<Pixel>()
    for (i in 0..pixArray.size()-1) {
        if (i%(pixArray.size()/Integer.parseInt(w.toString())) == 0) {
            img.add(tAr)
            tAr = ArrayList<Pixel>()
        }
        if (i%4 == 0) {
           val pix = Pixel(Integer.parseInt(pixArray[i].toString()), Integer.parseInt(pixArray[i+1].toString()), Integer.parseInt(pixArray[i+2].toString()), Integer.parseInt(pixArray[i+3].toString()))
           tAr.add(pix)
        }
    }
    val i = difti.PixelArrayImage(img)
    return GImage(app, i, Integer.parseInt(x_center.toString()), Integer.parseInt(y_center.toString(), String.toFloat(rotation.toString())))
}
