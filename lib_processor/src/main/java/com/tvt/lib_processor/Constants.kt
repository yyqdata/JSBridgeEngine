package com.tvt.lib_processor

import com.squareup.kotlinpoet.ClassName

/**
 * @author YYQ
 * @date 2022/4/24  16:37
 * @fileName Constants
 * @description:
 */
object Constants {
    val string = ClassName("java.lang", "String")

    val callBackFunction = ClassName("com.github.lzyzsd.jsbridge","CallBackFunction")

    val bridgeWebView = ClassName("com.github.lzyzsd.jsbridge","BridgeWebView")

}