package sys

import java.io.File

/* abstract over exception to show that we caught it or not */
case class FileSystemException(msg: String) extends Exception(msg)

class FileSystem(systemPath: String) {

  val WILD = '*'
  val SPLIT_COND = Array('\\', '/')

  /**
   *  Replace wildcard symbol `*` with well formed Regex for `String#matches` operation
   */
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

  /* helper function to check file match */
  private def isFile(file: File, wildCard: String): Boolean = (file.isFile() && file.getName().matches(wildCard))

  /**
   * Using a pattern and wildcard, file all cooresponding file definitions in location
   */
  private def buildFileList(file: File, wildCard: String): List[File] = file.exists match {
    case true => {
      if (file.isDirectory) file.listFiles().foldLeft(List[File]())((acc, f) => buildFileList(f, wildCard) ::: acc)
      else if (isFile(file, wildCard)) List(file)
      else throw new FileSystemException("FileSystem encountered bad state.")
    }
    case false => throw new FileSystemException("File not found: " + file.getCanonicalPath)
  }

  /**
   * Use the class argument systemPath to file appropriate file matches
   */
  def listFiles(): List[File] = {
    val (file, wildCard) = normalizeFilePattern(systemPath)
    buildFileList(file, wildCard)
  }

}