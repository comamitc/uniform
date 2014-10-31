package uniform.sys

import org.scalatest._

class FileSystemSpec extends FunSuite with Matchers {

  test("FileSystem#listFiles(./test_files) should find 2 files") {
    val fs = new FileSystem("./test_files")
    fs.listFiles.size should be(2)
  }

  test("FileSystem#listFiles(./test_files/*.log) should find 1 file") {
    val fs = new FileSystem("./test_files/*.log")
    fs.listFiles.size should be(1)
  }

  test("FileSystem#listFiles should throw exception with invalid path") {
    val fs = new FileSystem("./test_logs")
    intercept[FileSystemException] {
      fs.listFiles()
    }
  }

}