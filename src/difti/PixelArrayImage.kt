package difti

// y x
data class PixelArrayImage(val image: ArrayList<ArrayList<Pixel>>)
data class Pixel(val r: Int, val g: Int, val b: Int)