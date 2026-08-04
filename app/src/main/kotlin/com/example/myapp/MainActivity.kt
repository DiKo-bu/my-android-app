package com.example.myapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            var page by remember {
                mutableStateOf("file:///android_asset/index.html")
            }

            var webView: WebView? by remember {
                mutableStateOf(null)
            }

            BackHandler(drawerState.isOpen) {
                scope.launch {
                    drawerState.close()
                }
            }

            ModalNavigationDrawer(

                drawerState = drawerState,

                drawerContent = {

                    ModalDrawerSheet {

                        NavigationDrawerItem(
                            label = { Text("Главная") },
                            selected = false,
                            onClick = {
                                page = "file:///android_asset/index.html"
                                scope.launch { drawerState.close() }
                            }
                        )

                        NavigationDrawerItem(
                            label = { Text("Настройки") },
                            selected = false,
                            onClick = {
                                page = "about:blank"
                                scope.launch { drawerState.close() }
                            }
                        )

                        NavigationDrawerItem(
                            label = { Text("Выход") },
                            selected = false,
                            onClick = {
                                finish()
                            }
                        )
                    }
                }

            ) {

                Scaffold(

                    topBar = {

                        TopAppBar(

                            title = {
                                Text("MyWebViewApp")
                            },

                            navigationIcon = {

                                IconButton(
                                    onClick = {
                                        scope.launch {
                                            drawerState.open()
                                        }
                                    }
                                ) {
                                    Icon(Icons.Default.Menu, null)
                                }

                            }

                        )

                    }

                ) { padding ->

                    AndroidView(

                        modifier = Modifier
                            .fillMaxSize(),

                        factory = { context ->

                            WebView(context).apply {

                                webView = this

                                webViewClient = WebViewClient()

                                settings.javaScriptEnabled = true

                                loadUrl(page)

                            }

                        },

                        update = {

                            if (it.url != page) {
                                it.loadUrl(page)
                            }

                        }

                    )

                }

            }

        }

    }

}