package donets.danylo.testtask.activities


    import android.app.DownloadManager
    import android.content.Context
    import android.content.Intent
    import android.content.pm.ActivityInfo
    import android.net.Uri
    import android.os.Bundle
    import android.os.Environment
    import android.util.Log
    import android.view.WindowManager
    import android.webkit.CookieManager
    import android.webkit.URLUtil
    import android.webkit.WebView
    import android.webkit.WebViewClient
    import android.widget.Toast
    import androidx.appcompat.app.AppCompatActivity
    import com.appsflyer.AppsFlyerLib
    import donets.danylo.testtask.R


class WebView : AppCompatActivity() {
    private lateinit var webView: WebView
    private var initialUrl = "https://www.google.com/"
    private var mainActivityIntent: Intent? = null
    private var isFirstPageLoaded = false

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

        mainActivityIntent = Intent(this, MainActivity::class.java)

        val sharedPreferences = getSharedPreferences("WebViewHistoryPrefs", Context.MODE_PRIVATE)
        val savedUrl = sharedPreferences.getString("lastUrl", initialUrl)
        webView.loadUrl(savedUrl!!)

        webView.setDownloadListener({ url, userAgent, contentDisposition, mimeType, contentLength ->
            val request = DownloadManager.Request(Uri.parse(url))

            request.setMimeType(mimeType)
            request.addRequestHeader("cookie", CookieManager.getInstance().getCookie(url))
            request.addRequestHeader("User-Agent", userAgent)
            request.setDescription("Downloading file...")
            request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType))
            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalFilesDir(this@WebView, Environment.DIRECTORY_DOWNLOADS, ".png")
            val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)
            Toast.makeText(applicationContext, "Downloading File", Toast.LENGTH_LONG).show()
        })





    // Додати кнопку "Назад" до панелі дій
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onBackPressed() {


        if (webView.url == initialUrl) {


            val intent = Intent(this@WebView, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        else if (webView.canGoBack()) {
            webView.goBack()
        }

             else {
                webView.loadUrl(initialUrl)
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

            if (url != null) {
                if (url.contains("fex.net")) {
                    Log.i("RD_", "send")
                    AppsFlyerLib.getInstance().logEvent(applicationContext, "fexnet_event", null)
                }
            }
            if (url == initialUrl) {
                isFirstPageLoaded = true
            }
        }
    }
}

