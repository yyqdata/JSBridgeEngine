package com.yyqdata.jsbridgeengine

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.github.lzyzsd.jsbridge.BridgeWebViewClient
import com.google.gson.Gson
import com.tvt.wvjsbridge.JSBridgeManager
import com.yyqdata.jsbridgeengine.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var RESULT_CODE = 0
    var mUploadMessage: ValueCallback<Uri>? = null

    var mUploadMessageArray: ValueCallback<Array<Uri>>? = null

    internal class Location {
        var address: String? = null
    }

    internal class User {
        var name: String? = null
        var location: Location? = null
        var testStr: String? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        JSBridgeManager.init(JSBridgeInterface::class.java)
        val user = User()
        val location = Location()
        location.address = "SDU"
        user.location = location
        user.name = "大头鬼"
        binding.webView.functionInJs(Gson().toJson(user))
        binding.button.setOnClickListener {
            binding.webView.functionInJs("data from Java")
        }
        webViewSetting()
    }

    // webview的设置
    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewSetting() {
        binding.webView.webViewClient = BridgeWebViewClient(binding.webView)
        binding.webView.webChromeClient = object : WebChromeClient() {
            fun openFileChooser(
                uploadMsg: ValueCallback<Uri>,
                AcceptType: String?,
                capture: String?
            ) {
                this.openFileChooser(uploadMsg)
            }

            fun openFileChooser(uploadMsg: ValueCallback<Uri>, AcceptType: String?) {
                this.openFileChooser(uploadMsg)
            }

            fun openFileChooser(uploadMsg: ValueCallback<Uri>) {
                mUploadMessage = uploadMsg
                pickFile()
            }

            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                mUploadMessageArray = filePathCallback
                pickFile()
                return true
            }
        }
        val settings = binding.webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true // 不设置会导致网页白屏
        //设置大小
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = false //缩放屏幕大小
        settings.textZoom = 100 //保证字体大小不受系统字体影响
        settings.allowFileAccess = true
        binding.webView.loadUrl("file:///android_asset/demo.html")

        JSBridgeManager.start(binding.webView)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == RESULT_CODE) {
            if (null == mUploadMessage && null == mUploadMessageArray) {
                return
            }
            if (null != mUploadMessage && null == mUploadMessageArray) {
                val result = if (intent == null || resultCode != RESULT_OK) null else intent.data
                mUploadMessage!!.onReceiveValue(result)
                mUploadMessage = null
            }
            if (null == mUploadMessage && null != mUploadMessageArray) {
                val result = if (intent == null || resultCode != RESULT_OK) null else intent.data
                if (result != null) {
                    mUploadMessageArray!!.onReceiveValue(arrayOf(result))
                }
                mUploadMessageArray = null
            }
        }
    }

    fun pickFile() {
        val chooserIntent = Intent(Intent.ACTION_GET_CONTENT)
        chooserIntent.type = "image/*"
        startActivityForResult(chooserIntent, RESULT_CODE)
    }
}