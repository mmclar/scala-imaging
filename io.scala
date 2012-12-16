package filter
import java.io.File
import javax.imageio.ImageIO
import java.io.FileInputStream
import java.awt.image.BufferedImage

object RgbIO {
	def read(path:String):(List[RGB], Int) = {
		/*
		 * Given an image, generate a list of the RGB values.
		 */
		def readPixelList(image:BufferedImage):List[RGB] = {
			val a = new Array[RGB](image.getHeight*image.getWidth)
			for (r <- (0 until image.getHeight()).toList) {
				for (c <- (0 until image.getWidth()).toList) {
					var p = image.getRGB(c, r)
					a(r*image.getWidth() + c) = new RGB(p)
				}
			}
			a.toList
		}
		val imageFile = new File(path)
		val inputStream = new FileInputStream(imageFile)
		val image = ImageIO.read(inputStream)
    println("Image Size: " + image.getWidth() + "x" + image.getHeight())
		println("Loading Image...")
		(readPixelList(image), image.getWidth)
	}

	def write(path:String, image:List[RGB], width:Int):Unit = {
		/* 
		 * Given a list of RGB values and an image width, generate an image. This could freak out if the list 
		 * is not of the correct length.
		 */
		def writePixelList(ls:List[RGB], width:Int):BufferedImage = {
			def writePixels(ls:List[RGB], image:BufferedImage, r:Int, c:Int):BufferedImage = {
				image.setRGB(c, r, ls.head.asPixel)
				if (c < image.getWidth()-1) writePixels(ls.tail, image, r, c+1) else
					if (r < image.getHeight()-1) writePixels(ls.tail, image, r+1, 0) else
						image
			}
			writePixels(ls, new BufferedImage(width, ls.length/width, BufferedImage.TYPE_INT_ARGB), 0, 0)
		}
		println("Writing image...")
		val outputImage = writePixelList(image, width)
		val outputFile = new File(path)
		ImageIO.write(outputImage, "png", outputFile) // Some JVMs can't encode to JPEG
	}
}
