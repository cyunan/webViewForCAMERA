package com.bundletool.myapplication.testtest

open class BaseTest {
    var testValue: String? = null
}

fun main() {
    Test1().changeValue()
    println(Test2().getValue())
    println(Test2().testValue)
}