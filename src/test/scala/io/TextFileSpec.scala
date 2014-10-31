package uniform.io

import org.scalatest._

class TextFileSpec extends FunSuite with Matchers {

  val newLine = "(^\\d{4}-\\d{2}-\\d{2}[ T]\\d{2}:\\d{2}:\\d{2}.*)".r
  val ignoreLine = "(^\\s*\\t*\\r*)".r

  def findLine(str: String): Option[String] = {
  	if (str.contains("DBConnectionManager")) Some(str)
  	else None
  }

  test("TextFile#ctx should find 12 lines with stacktrace") {
    val tf = new TextFile("./test_files/test.log", newLine, ignoreLine)
    tf.ctx.size should be (12)
  }

  test("TextFile#getLines() should find 5 lines with stacktrace") {
    val tf = new TextFile("./test_files/test.log", newLine, ignoreLine)
    tf.getLines().size should be (5)
  }

  test("TextFile#getLines() should find 1 line containing 'DBConnectionManager'") {
  	val tf = new TextFile("./test_files/test.log", newLine, ignoreLine)
  	tf.getLines(findLine).filter(_ != None).size should be (1)
  }

}