package sys

import java.io.File

case class FileSystemException(msg: String) extends Exception(msg)

class FileSystem(systemPath: String) {

  val WILD = '*'
  val SPLIT_COND = Array('\\', '/')

  private def assembleWildCard(special: String): String =
    special.split(WILD)
      .map(x => if (x.isEmpty) "(.*)" else "(" + x + ")")
      .mkString("(.*)")

  /**
   * 	Reassemble the path splits into a normailzed path considering that the input is reversed
   */
  private def assemblePath(paths: Array[String]): String = {
    if (paths.isEmpty || paths.head.isEmpty) "./"
    else paths.foldLeft("")((acc, seg) => seg + '/' + acc)
  }

  private def normalizeFilePattern(filePattern: String): (File, String) = {
    if (filePattern.contains(WILD)) {
      val paths = filePattern.split(SPLIT_COND)
      (new File(assemblePath(paths.init)), assembleWildCard(paths.last))
    } else (new File(filePattern), "(.*)")
  }

  private def isFile(file: File, wildCard: String): Boolean = (file.isFile() && file.getName().matches(wildCard))

  private def buildFileList(file: File, wildCard: String): List[File] = {
    if (file.exists()) {
      if (file.isDirectory())
        file.listFiles()
          .foldLeft(List[File]())((acc, f) => buildFileList(f, wildCard) ::: acc)
      else if (isFile(file, wildCard)) List(file)
      else Nil
    } else throw new FileSystemException("File not found: " + file.getCanonicalPath)
  }

  /**
   * listFiles is the only exposed API for the FileSystem
   */
  def listFiles(): List[File] = {
    val (file, wildCard) = normalizeFilePattern(systemPath)
    buildFileList(file, wildCard)
  }

}