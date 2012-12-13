import java.io.File
import javax.imageio.ImageIO
import java.io.FileInputStream
import java.awt.image.BufferedImage

/* For us to mess around with RGB values and convert to the Java format */
case class RGB(r:Int, g:Int, b:Int)
def rgbToPixel(rgb:RGB) = (0xFF << 24) | ((rgb.r & 0xFF) << 16) | ((rgb.g & 0xFF) << 8) | (rgb.b & 0xFF)
def pixelToRgb(p:Int) = RGB((p >> 16 & 0xFF), (p >> 8) & 0xFF, p & 0xFF)

/*
 * Given an image, generate a list of the RGB values.
 */
def readPixelList(image:BufferedImage):List[RGB] = {
	val a = new Array[RGB](image.getHeight*image.getWidth)
	for (r <- (0 until image.getHeight()).toList) {
		for (c <- (0 until image.getWidth()).toList) {
			var p = image.getRGB(c, r)
			a(r*image.getWidth() + c) = pixelToRgb(p)
		}
	}
	a.toList
}

/* Given a list of RGB values, generate an image. This could freak out if the list
 * is not of the correct length.
 */
def writePixelList(ls:List[RGB], width:Int):BufferedImage = 
	writePixels(ls, new BufferedImage(width, ls.length/width, BufferedImage.TYPE_INT_ARGB), 0, 0)

def writePixels(ls:List[RGB], image:BufferedImage, r:Int, c:Int):BufferedImage = {
	image.setRGB(c, r, rgbToPixel(ls.head))
	if (c < image.getWidth()-1) writePixels(ls.tail, image, r, c+1) else
		if (r < image.getHeight()-1) writePixels(ls.tail, image, r+1, 0) else
			image
}

/* This drives the loading/filtering/saving process. */
def modify(f:List[RGB] => List[RGB], i:String, o:String):Unit = {
	val imageFile = new File(i)
	val inputStream = new FileInputStream(imageFile)
	val image = ImageIO.read(inputStream)
	println("Loading Image...")
	val rgb = readPixelList(image)
	println("Image Size: " + image.getWidth() + "x" + image.getHeight())
	println("Filtering...")
	val filtered = f(rgb)
	println("Writing image...")
	val outputImage = writePixelList(filtered, image.getWidth())
	val	outputFile = new File(o)
	ImageIO.write(outputImage, "png", outputFile) // Some JVMs can't encode to JPEG
}

/*
 * FILTERS
 */

/* Take a list of RGB values and return a list with them modified to grayscale */
def grayscale(ls:List[RGB]):List[RGB] = {
	def graypixel(rgb:RGB) = {
		val avg = (rgb.r + rgb.g + rgb.b) / 3
		RGB(avg, avg, avg)
	}
	ls.map(graypixel)
}

def rotate180(ls:List[RGB]) = ls.reverse

modify(grayscale, "boat-small.jpg", "out.png")
