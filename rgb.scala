package filter

/* For us to mess around with RGB values and convert to the Java format */
case class RGB(r:Int, g:Int, b:Int) {
  def this(p:Int) = this((p >> 16 & 0xFF), (p >> 8) & 0xFF, p & 0xFF)
	def asPixel() = (0xFF << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF)
}
