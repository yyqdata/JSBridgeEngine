package com.tvt.wvjsbridge

import com.github.lzyzsd.jsbridge.BridgeWebView
import java.lang.Exception
import java.lang.RuntimeException
import java.lang.reflect.Method

/**
 * @author YYQ
 * @date 2022/4/25  15:55
 * @fileName JSBridgeManager
 * @description: JsBridgeEngine 管理类
 */
object JSBridgeManager {
    private const val jsBridgeEngine = "JsBridgeEngine"

    private lateinit var engines: MutableList<Class<*>> // JsBridgeEngine 列表 不同包名会生成不同的Engine

    private lateinit var startMs: MutableList<Method> // JsBridgeEngine 对应启动函数

    private lateinit var enginePaths: MutableList<String>

    /**
     * @author YYQ
     * @date 2022/4/25  17:09
     * @description 注册 JSBridge 接口代理类 ，可以注册多个接口代理类
     */
    fun init(vararg classes: Class<*>) {
        try {
            if (!this::enginePaths.isInitialized) {
                enginePaths = mutableListOf()
            } else {
                enginePaths.clear()
            }
            classes.forEach {
                it.`package`?.let { it1 -> enginePaths.add(it1.name) }
            }
            if (!this::engines.isInitialized) {
                engines = mutableListOf()
            } else {
                engines.clear()
            }
            if (!this::startMs.isInitialized) {
                startMs = mutableListOf()
            } else {
                startMs.clear()
            }
            enginePaths.forEach {
                val engine = Class.forName("$it.$jsBridgeEngine")
                val startM = engine.getDeclaredMethod("start", BridgeWebView::class.java)
                startM.isAccessible = true
                engines.add(engine)
                startMs.add(startM)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun start(webView: BridgeWebView) {
        if (!this::enginePaths.isInitialized) {
            throw RuntimeException("JSBridgeProxy init not call!!")
        }
        try {
            for (i in engines.indices) {
                startMs[i].invoke(engines[i].kotlin.objectInstance, webView)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}