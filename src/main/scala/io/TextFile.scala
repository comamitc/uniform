package uniform.io

import scala.annotation.tailrec
import scala.util.matching.Regex

class TextFile[A](filename: java.io.File, newline: Regex, comment: Regex) extends FileOps {

  /** Private value that extracts `\?n` delimited lines from a file. */
  val ctx: Iterator[String] = this.open(filename).getLines

  def this(filename: java.io.File) = this(filename, "(^[\\w]+.*[:=].*$)".r, "(^[#|//].*)".r)

  def this(filename: java.io.File, newline: Regex) = this(filename, newline, "(^[#|//].*)".r)

  /** auxiliary constructor when only supplying a filename: String */
  def this(filename: String) = this(new java.io.File(filename), "(^[\\w]+.*[:=].*$)".r, "(^[#|//].*)".r)

  /** auxiliary constructor when only supplying a filename: String, newline: Regex */
  def this(filename: String, newline: Regex) = this(new java.io.File(filename), newline, "(^[#|//].*)".r)

  def this(filename: String, newLine: Regex, comment: Regex) = this(new java.io.File(filename), newLine, comment)

  /**
   * Overload method to handle no function passing to getLines.
   *
   * @return List[String]
   */
  def getLines(): List[String] = this.getLines[String]((s) => s)

  /**
   * @param func		String => A	: Applied function to do text pre-processing. Used to
   * 								  increase efficiency in processing and keep passes through lines 0(n)
   * @return List[A]
   *
   */
  def getLines[A](func: String => A): List[A] = {
    @tailrec
    def inner(fileLine: String, logLine: String = null, acc: List[A]): List[A] = fileLine match {
      case newline(_) =>
        if (!ctx.hasNext && logLine != null) func(logLine) :: func(fileLine) :: acc
        else if (!ctx.hasNext) func(fileLine) :: acc
        else if (logLine == null) inner(ctx.next, fileLine, acc)
        else inner(ctx.next, fileLine, func(logLine) :: acc)
      case comment(_) =>
        if (!ctx.hasNext) acc
        else inner(ctx.next, logLine, acc)
      case _ =>
        if (!ctx.hasNext && logLine != null) func(logLine + "\n" + fileLine) :: acc
        else if (!ctx.hasNext) acc
        else if (logLine == null) inner(ctx.next, logLine, acc)
        else inner(ctx.next, (logLine + "\n" + fileLine), acc)
    }
    if (ctx.hasNext) inner(ctx.next, null, Nil)
    else Nil
  }

}