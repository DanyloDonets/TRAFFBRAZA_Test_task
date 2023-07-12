package donets.danylo.testtask.AsyncLogic

import android.app.Application
import donets.danylo.testtask.AppsFlyer.AppsFlyer
import donets.danylo.testtask.OneSygnal.OneSignal


class AsyncLogic : Application() {

    override fun onCreate() {
        super.onCreate()

        // Ініціалізація AppsFlyer
        AppsFlyer().init(this)

        // Ініціалізація OneSignal
        OneSignal(this).init()
    }
}