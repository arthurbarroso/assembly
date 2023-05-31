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

## Example conversion table

| ASSEMBLY   | BINARY   | HEX |
|------------|----------|-----|
| MOVE 0, 0  | 00000000 | 00  |
| MOVE 0, 1  | 00010000 | 10  |
| ADD A, 0   | 00100000 | 2a  |
| ADD A, 1   | 00110000 | 3a  |
| GOTO F     | 11101111 | ef  |
| STORE A    | 11111010 | fa  |
| SUB 8, 0   | 01001000 | 48  |
| SUB 8, 1   | 01011000 | 58  |
| OR A, 0    | 01101010 | 6a  |
| OR A, 1    | 01111010 | 7a  |
| XOR 1, 0   | 10000001 | 81  |
| XOR 1, 1   | 10010001 | 91  |
| SHIFT 9, 0 | 10101001 | a9  |
| SHIFT 9, 1 | 10111001 | b9  |
| JMP 8, 0   | 11001000 | c8  |
| JMP 8, 1   | 11011000 | d8  |
