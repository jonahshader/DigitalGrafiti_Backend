package difti

import difti.display.Display
import processing.core.PApplet

class MainApp : PApplet() {
    lateinit val display: Display

    override fun settings() {
        fullScreen()
    }

    override fun setup() {

    }

    override fun draw() {
        background(255)
    }
}

fun main() {
    PApplet.main(MainApp::class.java)
}