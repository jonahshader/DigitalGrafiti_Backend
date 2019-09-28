package difti

import processing.core.PApplet
import processing.core.PGraphics

data class PixelArrayImage(val image: ArrayList<ArrayList<Pixel>>) {
    fun toPGraphics(graphics: PApplet) : PGraphics {
        val pGraphics = graphics.createGraphics(image[0].size, image.size)

        pGraphics.beginDraw()
        for (y in 0 until pGraphics.height) {
            for (x in 0 until pGraphics.width) {
                val pixel = image[y][x]
                pGraphics.stroke(pixel.r.toFloat(), pixel.g.toFloat(), pixel.b.toFloat(), pixel.a.toFloat())
                pGraphics.point(x.toFloat(), y.toFloat())
            }
        }
        pGraphics.endDraw()
        return pGraphics
    }
}

data class Pixel(val r: Int, val g: Int, val b: Int, val a: Int)