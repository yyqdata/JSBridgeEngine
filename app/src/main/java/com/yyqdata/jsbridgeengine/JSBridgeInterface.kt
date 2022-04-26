package com.yyqdata.jsbridgeengine

import android.util.Log
import android.widget.Toast
import com.github.lzyzsd.jsbridge.CallBackFunction
import com.tvt.lib_annotation.annotation.JCall
import com.tvt.lib_annotation.annotation.JRegister

/**
 * @author YYQ
 * @date 2022/4/26  14:40
 * @fileName JSBridgeInterface
 * @description:
 */
class JSBridgeInterface {

    @JRegister(isDefaultHandler = true)
    fun defaultHandler(data: String, function: CallBackFunction) {
        Log.d("JSBridgeInterface", "defaultHandler: $data")
        function.onCallBack("defaultHandler 返回数据 111111111111")
    }

    @JRegister
    fun submitFromWeb(data: String, function: CallBackFunction) {
        Log.d("JSBridgeInterface", "submitFromWeb: $data")
        function.onCallBack("submitFromWeb 返回数据 submitFromWeb")
    }

    @JCall
    fun functionInJs(data: String) {
        Log.d("JSBridgeInterface", "functionInJs: $data")
    }

}