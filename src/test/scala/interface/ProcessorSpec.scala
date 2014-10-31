package uniform.interface

import org.scalatest._

class ProcessorSpec extends FunSuite with Matchers {

	def returnSelf(str: String) = str

	test("Processor(./test_files/*.log, () => String) should have length of 5") {
		val lines = Processor.processFiles("./test_files/*.log", returnSelf)
		lines.size should be (5)
	}

}