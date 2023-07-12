package donets.danylo.testtask.activities


    import android.content.Context
    import android.content.Intent
    import android.os.Bundle
    import android.util.Log
    import android.webkit.WebView
    import android.webkit.WebViewClient
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

        webView = findViewById(R.id.webView)
        webView.webViewClient = MyWebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true

        mainActivityIntent = Intent(this, MainActivity::class.java)

        val sharedPreferences = getSharedPreferences("WebViewHistoryPrefs", Context.MODE_PRIVATE)
        val savedUrl = sharedPreferences.getString("lastUrl", initialUrl)
        webView.loadUrl(savedUrl!!)

        // Додати кнопку "Назад" до панелі дій
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onBackPressed() {


        if (webView.url == initialUrl) {

            setContentView(R.layout.activity_main)
            val intent = Intent(this@WebView, MainActivity::class.java)
            startActivity(intent)
        }
        else if (webView.canGoBack()) {
            webView.goBack()
        }

             else {
                webView.loadUrl(initialUrl)
            }
        }


    override fun onStop() {
        super.onStop()
        val sharedPreferences = getSharedPreferences("WebViewHistoryPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("lastUrl", webView.url)
        editor.apply()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private inner class MyWebViewClient : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            title = view?.title

            if (url != null) {
                if (url.contains("fex.net")) {
                    Log.i("appsFyer", "send")
                    AppsFlyerLib.getInstance().logEvent(applicationContext, "fexnet_event", null)
                }
            }
            if (url == initialUrl) {
                isFirstPageLoaded = true
            }
        }
    }
}