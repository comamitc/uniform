package uniform.interface

import uniform.io.TextFile
import uniform.sys.FileSystem

object Processor {

  private val newLine = "(^\\d{4}-\\d{2}-\\d{2}[ T]\\d{2}:\\d{2}:\\d{2}.*)".r
  private val ignoreLine = "(^\\s*\\t*\\r*)".r

  def processFiles[A](systemPath: String, lineFunction: String => A): List[A] = {
    new FileSystem(systemPath)
      .listFiles()
      .foldLeft(List[A]())((acc, file) => {
        new TextFile(file, newLine, ignoreLine).getLines(lineFunction) ::: acc
      })
  }
}