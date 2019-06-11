package com.ruichaoqun.kotlinstudyapplication.kotlin.basic

import java.util.ArrayList

/**
 * @author Rui Chaoqun
 * @description: 基础语法
 * @date :2019/6/11 11:31
 */
class BasicGrammar {

    //使用_划分数字常量
    var oneMillion = 1_000_000
    var idCard = 341204_1991_0210_2056L
    var hexBytes = 0xFF_01_02_F0

    //带有两个 Int 参数、返回 Int 的函数
    fun sun(a: Int, b: Int): Int {
        return a + b
    }

    //将表达式作为函数体、返回值类型自动推断的函数
    fun sum(a: Int = 10, b: Int = 20) = a + b

    //函数返回无意义的值
    fun printSum(a: Int, b: Int): Unit {
        println("sum of $a and $b is ${a + b}")
    }

    //Unit 返回类型可以省略
    fun printSum2(a: Int, b: Int): Unit {
        println("sum of $a and $b is ${a + b}")
    }


    //可变数量的参数
    fun <T> asList(vararg t: T): List<T> {
        val result = ArrayList<T>()
        for (ts in t) {
            result.add(ts)
        }
        return result
    }

    fun test() {
        BasicGrammar().asList(1, 2, 3)
        val a = arrayOf(1, 2, 3)
        val list = asList(0, 1, *a, 3)
    }

    /**
     * 在kotlin中“===”是判断地址是否相等 “==”是判断值是否相等
     */
    fun main() {
        val a: Int? = 1000
        println(a === a)
        val b: Int? = a
        val c: Int? = a
        println(b === c)

        var s1 = BasicGrammar()
        var s2 = BasicGrammar()
        println(s1 == s2)
    }

    fun test1() {
        val a: Int? = 1
        val b: Long? = a?.toLong()
        val i = 1L + 3
        println(a == b?.toInt())
        //有符号左移
        println(1 shl 2)
        //有符号右移
        println(8 shr 2)
        //无符号右移
        println(8 ushr 2)
        //位与
        println("位与${1 and 2}")
        //位或
        println(1 or 2)
        //位异或
        println(1 xor 2)
        //位非
        println(1.inv())
        //
        println(1.inc())


    }

    fun arrayTest(){
        //arrayOf创建数组
        println(arrayOf(1,2,3).size)

        //forEach方法遍历数组
        arrayOf(1,2,3).forEach {
            println(it)
        }
        var s = arrayOf(1,2,3)
        for (a in s){
            println(a)
        }
        println(arrayOfNulls<Int>(10).size)

        //用接受数组大小以及一个函数参数的 Array 构造函数，用作参数的函数能够返回给定索引的每个元素初始值：
        //// 创建一个 Array<String> 初始化为 ["0", "1", "4", "9", "16"]
        var b = Array(5){i -> (i*i).toString()}
        b.forEach { println(it) }

        var x = intArrayOf(1,2,3)
        var y = byteArrayOf(1,2,2)
    }

    fun stringTest(){
        var str = "啊阿"
        for (c in str){
//            println(c)
//            println(c.toInt())
        }
//        str.forEach { println(it) }

        var s1 = """"""
        println(s1)
        s1.forEach { println(it) }
    }
}