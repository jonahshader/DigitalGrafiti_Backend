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
//    val x_center = (((app.width - w) * Math.random()) + (w * .5)).toFloat()
//    val y_center = (((app.height - h) * Math.random()) + (h * .5)).toFloat()
    val x_center = (app.width * Math.random()).toFloat()
    val y_center = (app.height * Math.random()).toFloat()
    val rotation = obj.getFloat("rotation")
    val pixArray = obj.getJSONArray("rgba")
    var img = ArrayList<ArrayList<Pixel>>()

    for (y in 0 until h) {
        img.add(ArrayList())
        for (x in 0 until w) {
            val index = ((y * w) + x) * 4
            var r = Integer.parseInt(pixArray[index].toString())
            var g = Integer.parseInt(pixArray[index + 1].toString())
            var b = Integer.parseInt(pixArray[index + 2].toString())
            var a = Integer.parseInt(pixArray[index + 3].toString())

//            if (r + g + b + a == 0) {
//                r = 255
//                g = 255
//                b = 255
//                a = 20
//            }

            img[y].add(Pixel(r, g, b, a))

        }
    }
    val i = difti.PixelArrayImage(img)
    return GImage(app, i, x_center, y_center, rotation)
}
