package donets.danylo.testtask.OneSygnal

import android.content.Context
import com.onesignal.OSNotificationOpenedResult
import com.onesignal.OneSignal

import android.app.Application


class OneSignal(private val context: Context) {

    fun init() {
        OneSignal.initWithContext(context)
        OneSignal.setAppId("OWFlODJmNDAtMTcwNS00OTc4LWEzMjMtZWM2MjljYjU2YWVk")
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.DEBUG, OneSignal.LOG_LEVEL.NONE)
        OneSignal.setNotificationOpenedHandler(MyNotificationOpenedHandler())
    }

    private inner class MyNotificationOpenedHandler : OneSignal.OSNotificationOpenedHandler {
        override fun notificationOpened(result: OSNotificationOpenedResult) {
            val notification = result.notification
            val data = notification.additionalData

            // Обробка відкриття повідомлення OneSignal
            // ...

        }
    }
}