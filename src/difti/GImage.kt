package difti

import processing.core.PApplet
import processing.core.PConstants.CENTER
import processing.core.PGraphics

class GImage(mainApp: PApplet, pixelArrayImage: PixelArrayImage, private val xCenter: Float, private val yCenter: Float, private val rotationRadians: Float) {
    private var image = pixelArrayImage.toPGraphics(mainApp)

    fun renderToBuffer(buffer: PGraphics) {
        buffer.beginDraw()
        buffer.imageMode(CENTER)
        buffer.pushMatrix()
        buffer.translate(xCenter, yCenter)
        buffer.rotate(rotationRadians)
        buffer.image(image, 0f, 0f)
        buffer.popMatrix()
        buffer.endDraw()
    }
}