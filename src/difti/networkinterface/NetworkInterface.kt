package difti.networkinterface

import difti.GImage
import difti.Pixel
import difti.display.Display
import processing.core.PApplet
import processing.data.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Math.floor
import java.util.*
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread
import kotlin.math.floor

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
        val l = JSONObject.parse(br.readLine())
        disp.addImage(mkImgFromPixArray(l, app))
    }
}

private fun mkImgFromPixArray(obj: JSONObject, app: PApplet) : GImage {
    val h = obj.getInt("height")
    val w = obj.getInt("width")
//    val x_center = obj.getFloat("x")
//    val y_center = obj.getFloat("y")
    val x_center = (((app.width - (w * 2)) * Math.random()) + w).toFloat()
    val y_center = (((app.height - (h * 2)) * Math.random()) + h).toFloat()
    val rotation = obj.getFloat("rotation")
    val pixArray = obj.getJSONArray("rgba")
    var img = ArrayList<ArrayList<Pixel>>()
    var tAr = ArrayList<Pixel>()

    for (y in 0 until h) {
        img.add(ArrayList())
        for (x in 0 until w) {
            val index = ((y * w) + x) * 4
            img[y].add(
                Pixel(
                    Integer.parseInt(pixArray[index].toString()),
                    Integer.parseInt(pixArray[index + 1].toString()),
                    Integer.parseInt(pixArray[index + 2].toString()),
                    Integer.parseInt(pixArray[index + 3].toString())
                ))
        }
    }

//    for (i in 0 until pixArray.size()) {
//        if ((i%((pixArray.size()/4)/w)).equals(0)) {
//            println(img)
//            img.add(tAr)
//            tAr = ArrayList<Pixel>()
//        }
//        if (i%4 == 0) {
//           val pix = Pixel(Integer.parseInt(pixArray[i].toString()), Integer.parseInt(pixArray[i+1].toString()), Integer.parseInt(pixArray[i+2].toString()), Integer.parseInt(pixArray[i+3].toString()))
//           tAr.add(pix)
//        }
//    }
    val i = difti.PixelArrayImage(img)
    return GImage(app, i, x_center, y_center, rotation)
}
