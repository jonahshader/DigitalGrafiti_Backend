package difti

import difti.display.Display
import processing.core.PApplet

class MainApp : PApplet() {
//    val display = Display(this)

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