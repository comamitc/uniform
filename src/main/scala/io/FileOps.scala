package io

import java.io.FileNotFoundException
import java.io.IOException

import scala.io.Source
import scala.language.implicitConversions

class FileOpsException(msg: String) extends Exception(msg)

/**
 *
 *
 */
trait FileOps {

  implicit def fileToPath(file: java.io.File): String = file.getCanonicalPath()

  /**
   * @param file 	java.io.File to open and readlines
   * @return 			scala.io.Source buffer
   */
  def open(file: java.io.File): Source = {
    try {
      Source.fromFile(file)
    } catch {
      case ex: FileNotFoundException =>
        throw new FileOpsException("No File: " + file)
      case ex: Exception =>
        throw new FileOpsException("Some weird shit happened while trying to open: " + file)
    }
  }

  /* This function slows down string processing considerably*/
  def trim(s: String): String =
    s.replaceAll("(^\\s*\\t*\\r*)|(\\s*\\t*\\r*$)|(\\s*\\t*\\r*//.*$)|(\\s*\\t*\\r*#.*$)", "")

}