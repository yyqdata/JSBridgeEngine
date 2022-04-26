package com.tvt.lib_annotation.annotation

import org.jetbrains.annotations.NotNull

/**
 * @author YYQ
 * @date 2022/4/15  17:24
 * @fileName JRegister
 * @description: JSBridge registerHandler 注解 被注解函数最多只能拥有 2个参数（由JS限制） 返回类型不受限制且没有作用
 * 不要在四大组件中使用，会出现数据丢失
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class JRegister(
    @NotNull val name: String = "", // js注册函数名，如果为 "" 则获取注解函数名
    val needData: Boolean = true,//js 回调的数据是否需要
    val needCallbackFun: Boolean = true, // js 回调函数是否需要  如果需要，函数入参必须带 CallBackFunction对象
    val isDefaultHandler:Boolean = false // 默认不是 DefaultHandler ，如果是则调用 setDefaultHandler
)
