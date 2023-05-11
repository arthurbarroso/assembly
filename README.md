# Small assembler

A tool to transform a very basic assembly language into instruction code (to be used with [logisim](https://sourceforge.net/projects/circuit/)). Created for college and for fun. The CPU this is meant to be used with can be found [here](https://github.com/MarceloCamponez/CPU_LOGISIM)

## Usage

Grab yourself one of the jar files from [releases](https://github.com/arthurbarroso/revoir/releases) and run it with

``` sh
java -jar revoir.jar -i "input-file-path" -o "output-file-path"
```
The input file should be the `.asm` file. The output file will correspond to the logisim usable "memory file".

## Development

This is written in Clojure. The main commands here (apart from starting youserlf a REPL) are `bin/kaocha` and `clojure -X:build :jar release-path.jar :main-class revoir.cli`

Tests can be found in `test` and the test-related files sit in `resources/tests`
