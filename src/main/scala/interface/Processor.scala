package interface

import io.TextFile
import sys.FileSystem

object Processor {

	val newLine = "".r
	val ignoreLine = "".r

	def processFiles[A](systemPath: String, lineFunction: String => A): List[A] = {
		new FileSystem(systemPath)
			.listFiles()
			.foldLeft(List[A]())((acc, file) => {
				new TextFile(file, newLine, ignoreLine).getLines(lineFunction) ::: acc
			})
	}
}