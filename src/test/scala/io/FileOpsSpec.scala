package io

import org.scalatest._
import scala.io.Source
import java.io.File
import java.io.FileNotFoundException

// fake class to use for testing the interface
class FileOpsExt() extends FileOps

class FileOpsSpec extends FunSuite with Matchers {

  test("FileOps should open a file that exists 'test.log'") {
    val fo = new FileOpsExt().open(new File("./test_files/test.log"))
    fo shouldBe a [Source]
  }

  test("FileOps#trim(	hi  ) should be 'hi'") {
  	val fo = new FileOpsExt()
  	fo.trim("	hi  ") should be ("hi")
  }

  test("Fileops should throw FileNotFound when file doesn't exist") {
  	intercept[FileOpsException] {
      new FileOpsExt().open(new File("./road/to/nowhere.log"))
    }
  }

  test("FileOpsException should throw base Exception") {
  	intercept[Exception] {
      throw new FileOpsException("uh-oh")
    }
  }
  
}