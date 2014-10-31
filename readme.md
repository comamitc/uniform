uniform
====
uniform is a plain text file processing library written in scala making file data collection trivial.

#Basic Use
```scala

import uniform.interface.Processor

// Don't manipulate the log lines
def returnSelf(str: String) = str

// Trigger file processing
Processor.processFiles("./*.log", returnSelf)

```

#Testing

test-coverage: 79.8%

pass-rate: 100%
