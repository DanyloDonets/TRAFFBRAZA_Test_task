package donets.danylo.testtask.AsyncLogic

import android.app.Application
import android.content.Context
import donets.danylo.testtask.AppsFlyer.AppsFlyer
import donets.danylo.testtask.OneSygnal.OneSignal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AsyncLogic : Application() {

    override fun onCreate() {
        super.onCreate()
        val context: Context = this
        GlobalScope.launch(Dispatchers.IO) {
            // Ініціалізація AppsFlyer
            AppsFlyer().init(context)

            // Ініціалізація OneSignal
            OneSignal(context).init()
        }
    }
}