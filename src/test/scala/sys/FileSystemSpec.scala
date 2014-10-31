package uniform.sys

import org.scalatest._

class FileSystemSpec extends FunSuite with Matchers {

  test("FileSystem#listFiles(./test_files) should find 2 files") {
    val fs = new FileSystem("./test_files")
    fs.listFiles.size should be(3)
  }

  test("FileSystem#listFiles(./test_files/*.log) should find 2 files") {
    val fs = new FileSystem("./test_files/*.log")
    val files = fs.listFiles
    println(files)
    files.size should be(2)
  }

  test("FileSystem#listFiles(test_files/*.log) should find 2 file") {
    val fs = new FileSystem("./*.log")
    val files = fs.listFiles
    println(files)
    files.size should be(4)
  }

  test("FileSystem#listFiles should throw exception with invalid path") {
    val fs = new FileSystem("./test_logs")
    intercept[FileSystemException] {
      fs.listFiles()
    }
  }

}