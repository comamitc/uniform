uniform
====
uniform is a plain text file processing library written in scala making file data collection trivial.

##Basic Use

The first step to using uniform is to bring in the dependency if you scala / sbt project:

```scala

import sbt._

object LoGrepBuild extends Build {
  println("building...")
  lazy val LoGrep = Project("MyDependentProject", file(".")) dependsOn(uniform % "compile")

  lazy val uniform = RootProject(uri("git://github.com/comamitc/uniform.git"))
}

```

Then to use it in your project you can do the following:

```scala

import uniform.interface.Processor

// Don't manipulate the log lines
def returnSelf(str: String) = str

// Trigger file processing
Processor.processFiles("./*.log", returnSelf)

```

##Configuration

uniform accepts two configuration parameters that help the tool narrow in on the log line format and ignore lines.

```
new_line=(^\\d{4}-\\d{2}-\\d{2}[ T]\\d{2}:\\d{2}:\\d{2}.*)
ignore_line=(^\\s*\\t*\\r*)

```


##Testing

[info] [scoverage] All done. Coverage was [77.60%]

[info] Run completed in 2 seconds, 495 milliseconds.

[info] Total number of tests run: 11

[info] Suites: completed 4, aborted 0

[info] Tests: succeeded 11, failed 0, canceled 0, ignored 0, pending 0

[info] All tests passed.
