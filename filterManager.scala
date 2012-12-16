package filter

object FilterManager {
	def filterFromName(name:String):(List[RGB])=>List[RGB] =
		Map[String,(List[RGB]=>List[RGB])](("rotate", Filters.rotate180),
		    ("grayscale", Filters.grayscale),
				("dropred", Filters.dropRed),
				("maxred", Filters.maxRed))(name)

	def runFilter(ls:List[RGB], name:String) =
	  filterFromName(name)(ls)
}
