package com.tvt.lib_processor.bean

import com.tvt.lib_annotation.annotation.JCall
import com.tvt.lib_annotation.annotation.JRegister
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.type.TypeMirror
import javax.lang.model.util.Elements

/**
 * @author YYQ
 * @date 2022/4/19  14:15
 * @fileName JAnnotationElement
 * @description: JSBridge 注解对象封装
 */
sealed class JAnnotationElement(
    open val element: Element,
    protected open val elementUtils: Elements
) {
    val pkgName: String by lazy { elementUtils.getPackageOf(element).asType().toString() } // 包名

    val enclosingElementName: String by lazy { element.enclosingElement.simpleName.toString() } // 根据Element的具体类型判断 可能是类名

    val elementName: String by lazy { element.simpleName.toString() }// 根据Element的具体类型判断 可能是方法名

    val elementType: TypeMirror by lazy { element.asType() } // 类型

    val elementKind: ElementKind by lazy { element.kind } // 分类

    override fun toString(): String {
        return "JAnnotationElement(pkgName='$pkgName', enclosingElementName='$enclosingElementName', " +
                "elementName='$elementName', elementType=$elementType, elementKind=$elementKind)"
    }
}

data class JRegisterElement(override val element: Element, override val elementUtils: Elements) :
    JAnnotationElement(element, elementUtils) {
    val registerName = element.getAnnotation(JRegister::class.java).name // 注解参数 name
    val registerNeedData = element.getAnnotation(JRegister::class.java).needData // 注解参数 needData
    val registerNeedCallback =
        element.getAnnotation(JRegister::class.java).needCallbackFun // 注解参数 needCallbackFun
    val registerIsDefaultHandler =
        element.getAnnotation(JRegister::class.java).isDefaultHandler // 注解参数 isDefaultHandler

    override fun toString(): String {
        return "JRegisterElement(element=$element, elementUtils=$elementUtils, registerName='$registerName', " +
                "registerNeedData=$registerNeedData, registerNeedCallback=$registerNeedCallback, registerIsDefaultHandler=$registerIsDefaultHandler)"
    }
}

data class JCallElement(override val element: Element, override val elementUtils: Elements) :
    JAnnotationElement(element, elementUtils) {
    val callName = element.getAnnotation(JCall::class.java).name // 注解参数 name
    val callNeedData = element.getAnnotation(JCall::class.java).needData // 注解参数 needData

    override fun toString(): String {
        return "JCallElement(element=$element, elementUtils=$elementUtils, callName='$callName', callNeedData=$callNeedData) ${super.toString()}"
    }
}