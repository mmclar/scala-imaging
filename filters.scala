package filter

object Filters {
	/* Take a list of RGB values and return a list with them modified to grayscale */
	def grayscale(ls:List[RGB]):List[RGB] = {
		def graypixel(rgb:RGB) = {
			val avg = (rgb.r + rgb.g + rgb.b) / 3
			RGB(avg, avg, avg)
		}
		ls.map(graypixel)
	}

	/*
	 * Rotate the image 180 degrees by reversing all pixel values (e.g., the
	 * pixel at bottom-right becomes top-left).
	 */
	def rotate180(ls:List[RGB]) = ls.reverse

	/* Drop the red channel from each pixel. */
	def dropRed(ls:List[RGB]) = ls.map(rgb => RGB(0, rgb.g, rgb.b))

	/* Max the red channel for each pixel. */
	def maxRed(ls:List[RGB]) = ls.map(rgb => RGB(255, rgb.g, rgb.b))
}
