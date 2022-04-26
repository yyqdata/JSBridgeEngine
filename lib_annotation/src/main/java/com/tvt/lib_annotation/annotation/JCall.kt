package com.tvt.lib_annotation.annotation

import org.jetbrains.annotations.NotNull

/**
 * @author YYQ
 * @date 2022/4/15  17:25
 * @fileName JCall
 * @description: JSBridge callHandler 注解 标注的函数必须有入参 data:String 传递给 JS 的参数 被注解函数最多只能拥有 1个参数（由JS限制） 返回类型不受限制且没有作用
 * 不要在四大组件中使用，会出现数据丢失
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class JCall(
    @NotNull val name: String = "", // js注册函数名，如果为 "" 则获取注解函数名
    val needData: Boolean = true //js 回调的数据是否需要
)
