import filter._

/*
 * Main program function,
 */
object Filter {
  def main(args:Array[String]) {
	  println("Started main.")
		val (inputPath, outputPath) = (args(0), args(1))
		val (initial, width) = RgbIO.read(inputPath)
		val filtered = FilterManager.runFilter(initial, args(2))
		RgbIO.write(outputPath, filtered, width)
		println("Execution complete.")
	}
}
