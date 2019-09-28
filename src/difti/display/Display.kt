package difti.display

import difti.GImage
import processing.core.PApplet

class Display(graphics: PApplet, width: Int, height: Int) {
    private val buffer = graphics.createGraphics(width, height)

    fun drawToScreen(graphics: PApplet) {
        graphics.image(buffer, 0f, 0f, graphics.width.toFloat(), graphics.height.toFloat())
    }

    fun addImage(image: GImage) {
        image.renderToBuffer(buffer)
    }
}