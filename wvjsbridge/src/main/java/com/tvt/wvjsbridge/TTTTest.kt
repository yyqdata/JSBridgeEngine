package com.tvt.wvjsbridge

import com.github.lzyzsd.jsbridge.CallBackFunction
import com.tvt.lib_annotation.annotation.JRegister

/**
 * @author YYQ
 * @date 2022/4/25  15:45
 * @fileName TTTTest
 * @description:
 */
class TTTTest {

    @JRegister("hahahahaha")
    fun jregister_call(data: String, callback: CallBackFunction) {
    }
}