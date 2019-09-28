package difti

import processing.core.PApplet

class MainApp : PApplet() {

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