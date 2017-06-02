# ITMO University paradigms course
## 1. Getting programm arguments in command line, parsing them not to contain whitespace symbols and printing their sum
Modification : parsing in decimal and in octadecimal radix

## 2. Design by contract(DbC). It contains 2 implimentations of binsearch algorithm with full contract in comments.<br />
Modification : number of elements equal to some number.

## 3. ArrayQueue implimentation in 3 different OOP concepts: 1) class methods, 2) ADT (OOP without syntax sugar of missing the first argument), 3) classical OOP implementation<br />
Modification: Deque methods<br />
<br />
## 4. LinkedQueue. Implementation of common interface and abstract class of task3 ArrayQueue and LinkedQueue.<br />
Modification: filter(predicate) and map(function) methods to both of classes.<br />
<br />
## 5. Expressions parser. Programm is a parser of any expression containing integer constants, variables(x,y,z), binary operations<br />
(+, -, *, /, <<, >>) and unary (abs, square, -). Implementation contains classes allowing to build any combination of operators and priorities in math expressions context.<br />
<br />
## 6. Modification of 5th task. Now programm also throws exceptions and prints informative messages to user. Also some operators<br />
added : unary(log2, pow2) and binary(min, max with sintax "a max b" and "a min b").<br />
<br />
## 7. JavaScript Functional expressions parser. Expressions should be performed in poland notation.<br />
Operations : +, -, *, /, negate, min3, max5.<br />
Variables : x, y, z, u, v, w<br />
Literals : x, y, z<br />
Constants :  pi, e<br />
<br />
## 8. JavaScript Object Oriented expressions parser.<br />
Operations : +, -, *, /, negate, pow, log, sqr, sqrt.<br />
Variables : x, y, z, u, v, w<br />
Supports dirivatives (.diff() method) of each expression<br />
Supports toString() method<br />
Supports simplify() method - simplifies each expression (calculating constant expressions, deleting *1, *0, +0 etc.)<br />
<br />
