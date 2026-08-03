package com.example.myapp

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val webView = findViewById<WebView>(R.id.webView)
        webView.webViewClient = WebViewClient() // чтобы ссылки открывались внутри
        webView.settings.javaScriptEnabled = true // если нужен JS
        // Загружаем HTML из папки assets
        webView.loadUrl("file:///android_asset/index.html")
    }
}
