package difti

import difti.display.Display
import difti.networkinterface.SocketServer
import processing.core.PApplet
import processing.core.PImage

class MainApp : PApplet() {
    lateinit var display: Display
    lateinit var server: SocketServer
    lateinit var backgroundImage: PImage

    override fun settings() {
        fullScreen()
    }

    override fun setup() {
        backgroundImage = loadImage("digitalgraffiti.png")
        display = Display(this, width, height)
        server = SocketServer(display, this)
    }

    override fun draw() {
        image(backgroundImage, 0f, 0f, width.toFloat(), height.toFloat())
        display.drawToScreen(this)
    }
}

fun main() {
    PApplet.main(MainApp::class.java)
}