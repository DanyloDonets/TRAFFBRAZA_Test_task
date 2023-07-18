package donets.danylo.testtask.OneSygnal

import android.content.Context
import com.onesignal.OSNotificationOpenedResult
import com.onesignal.OneSignal

import android.app.Application
import android.content.Intent
import donets.danylo.testtask.activities.WebView


class OneSignal(private val context: Context) {

    private val ONESIGNAL_APP_ID = "5423c5d7-aef6-4f49-b801-d61b042fe7cb"

    fun init() {
        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(context)
        OneSignal.setAppId(ONESIGNAL_APP_ID)

        // promptForPushNotifications will show the native Android notification permission prompt.
        // We recommend removing the following code and instead using an In-App Message to prompt for notification permission (See step 7)
        OneSignal.promptForPushNotifications()

        OneSignal.setNotificationOpenedHandler(MyNotificationOpenedHandler())
    }

    private inner class MyNotificationOpenedHandler : OneSignal.OSNotificationOpenedHandler {
        override fun notificationOpened(result: OSNotificationOpenedResult) {
            val intent = Intent(context, WebView::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)

        }
    }
}




