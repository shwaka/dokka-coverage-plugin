package com.github.shwaka.dokkacov.example

/**
 * The main function.
 *
 * Just prints "hello world".
 */
fun main() {
    println("Hello World!")
}

/**
 * Brief description of function1
 */
fun function1(): Int {
    return 1
}

/**
 * Brief description of function2
 */
fun function2(arg: Int): String {
    return ""
}

fun noDocFunction1() {}

/**
 * Brief description of overload1(Int)
 */
fun overload1(arg: Int) {}

/**
 * Brief description of overload1(String)
 */
fun overload1(arg: String) {}

fun noDocOverload1(arg: Int) {}

fun noDocOverload1(arg: String) {}
