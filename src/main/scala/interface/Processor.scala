package uniform.interface

import uniform.io.TextFile
import uniform.sys.FileSystem
import uniform.config.UniformConfig

object Processor {

  private val newLine = UniformConfig.get("new_line").r
  private val ignoreLine = UniformConfig.get("ignore_line").r

  def processFiles[A](systemPath: String, lineFunction: String => A): List[A] = {
    new FileSystem(systemPath)
      .listFiles()
      .foldLeft(List[A]())((acc, file) => {
        new TextFile(file, newLine, ignoreLine).getLines(lineFunction) ::: acc
      })
  }
}