package com.jesse.jnotes.components

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebView(data: String) {

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()

                //settings.loadWithOverviewMode = true
                //settings.useWideViewPort = true
                settings.setSupportZoom(true)

            }
        },
        Modifier.height(Dp(300F)),
        update = { webView ->
            webView.loadData(data, null, null)
        }
    )
}

@Preview
@Composable
fun preview() {
    WebView("<h1>Hello World</h1>")
}