package donets.danylo.testtask.activities

import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.WindowManager
import android.webkit.CookieManager
import android.webkit.URLUtil
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import donets.danylo.testtask.R

class WebView : AppCompatActivity() {

    private lateinit var webView: WebView
    private val initialUrl = "https://www.google.com/"


    private val fileChooserRequestCode = 1
    private var fileChooserCallback: ValueCallback<Array<Uri>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        webView = findViewById(R.id.webView)
        webView.webViewClient = MyWebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.allowContentAccess = true
        webView.settings.allowFileAccess = true

        val sharedPreferences = getSharedPreferences("WebViewHistoryPrefs", Context.MODE_PRIVATE)
        val savedUrl = sharedPreferences.getString("lastUrl", initialUrl)
        webView.loadUrl(savedUrl!!)

        webView.setDownloadListener { url, userAgent, contentDisposition, mimeType, _ ->
            val request = DownloadManager.Request(Uri.parse(url))
            request.setMimeType(mimeType)
            request.addRequestHeader("cookie", CookieManager.getInstance().getCookie(url))
            request.addRequestHeader("User-Agent", userAgent)
            request.setDescription("Downloading file...")
            request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType))
            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, ".png")
            val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)
            Toast.makeText(applicationContext, "Downloading File", Toast.LENGTH_LONG).show()
        }

        // Add "Up" button to the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        webView.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                fileChooserCallback = filePathCallback
                val intent = fileChooserParams?.createIntent()
                try {
                    if (intent != null) {
                        startActivityForResult(intent, fileChooserRequestCode)
                    }
                } catch (e: ActivityNotFoundException) {
                    fileChooserCallback = null
                    Toast.makeText(
                        this@WebView,
                        "File picker not found",
                        Toast.LENGTH_SHORT
                    ).show()
                    return false
                }
                return true
            }
        }



    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == fileChooserRequestCode) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                if (fileChooserCallback != null) {
                    val result = if (data.data != null) arrayOf(data.data!!) else null
                    fileChooserCallback?.onReceiveValue(result)
                }
                fileChooserCallback = null
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }





    private inner class MyWebViewClient : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            title = view?.title

            val sharedPreferences = getSharedPreferences("WebViewHistoryPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("lastUrl", webView.url)
            editor.apply()

            if (url != null && url.contains("fex.net")) {
                Log.i("WebViewActivity", "Send AppsFlyer event")
                // Perform AppsFlyer event logging here
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Clear WebView cache and history
        webView.clearCache(true)
        webView.clearHistory()
    }


}
