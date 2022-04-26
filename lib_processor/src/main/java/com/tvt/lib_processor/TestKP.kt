package com.tvt.lib_processor

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import java.util.*

/**
 * @author YYQ
 * @date 2022/4/21  15:38
 * @fileName TestKP
 * @description:
 */
class TestKP {


}

fun main(args: Array<String>) {
    kp1()
    println("===================================================")
}

private fun kp1() {
    val hello = TypeSpec.classBuilder("HelloWorld")
        .addFunction(whatMyName("Nick"))
        .addFunction(whatMyName("Selinda"))
        .addFunction(whatMyName("Micheal"))
        .addFunction(whatMyName("MichealLove$"))

    val java = PropertySpec.builder("java", String::class.asTypeName().copy(nullable = true))
        .addModifiers(KModifier.PRIVATE)
        .mutable()
        .initializer("null")
        .build()

    val kotlin = PropertySpec.builder("kotlin", String::class, KModifier.PRIVATE)
        .build()

    val funP = FunSpec.builder("total")
        .returns(String::class)// String 和 基本类型自动导入
        .addStatement("return %P", "Your total is " + "$" + "amount")
        .build()

    val today = FunSpec.builder("Today")
        .returns(Date::class)
        .addStatement("return %S : %T()","今天日期", Date::class)
        .build()

    val hoverboard = ClassName("com.mattel", "Hoverboard")
    val list = ClassName("kotlin.collections", "List")
    val arrayList = ClassName("kotlin.collections", "ArrayList")
    val listofHoverboard = list.parameterizedBy(hoverboard)//泛型
    val arrayListofHoverboard = arrayList.parameterizedBy(hoverboard)//泛型

    val thing = ClassName("com.misc", "Thing")
    val array = ClassName("kotlin", "Array")
    val producerArrayOfThings = array.parameterizedBy(WildcardTypeName.producerOf(thing)) //通配符

    val beyond = FunSpec.builder("beyond")
        .returns(listofHoverboard)//List<Hoverboard>
        .addStatement(
            "val result = %T()",
            arrayListofHoverboard
        ) // val result = ArrayList<Hoverboard>()
        .addStatement("result += %T()", hoverboard)
        .addStatement("result += %T()", hoverboard)
        .addStatement("result += %T()", hoverboard)
        .addStatement("return result")
        .build()

    val printThing = FunSpec.builder("printThing")
        .addParameter("things", producerArrayOfThings)
        .addStatement("println(things)")
        .build()

    val add = FunSpec.builder("add")
        .addParameter("a", Int::class)
        .addParameter(
            ParameterSpec.builder("b", Int::class)
                .defaultValue("%L", 0)
                .build()
        )
        .addStatement("println(\"a + b = \${a + b}\")")
        .build()

    hello.addProperty(java)
    hello.addProperty(kotlin)
    hello.addFunction(funP)
    hello.addFunction(today)
    hello.addFunction(beyond)
    hello.addFunction(printThing)
    hello.addFunction(add)
    val fileKt = FileSpec.builder("com.example.hello", "HelloWorld")
        .addType(hello.build())
        .build()
    fileKt.writeTo(System.out)
}


private fun whatMyName(name: String): FunSpec {
    return FunSpec.builder(name)
        .returns(String::class)
        .addStatement("return %S", name)
        .build()
}