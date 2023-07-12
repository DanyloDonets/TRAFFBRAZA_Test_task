package donets.danylo.testtask.AppsFlyer

import android.app.Application
import android.content.Context
import android.util.Log
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val FLYER_DEV_KEY = "uzAQueXY9w3qzJYNRG9K5Q"

class AppsFlyer {
    fun init(context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            AppsFlyerLib.getInstance()
                .init(
                    FLYER_DEV_KEY,
                    object : AppsFlyerConversionListener {
                        override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
                            data!!.map { Log.d("RD_", "${it.key} == ${it.value}") }
                        }

                        override fun onConversionDataFail(error: String?) {
                            Log.e("RD_", "error onAttributionFailure : $error")
                        }

                        override fun onAppOpenAttribution(data: MutableMap<String, String>?) {
                            data?.map {
                                Log.d("RD_", "onAppOpen_attribute: ${it.key} = ${it.value}")
                            }
                        }

                        override fun onAttributionFailure(error: String?) {
                            Log.e("RD_", "error onAttributionFailure : $error")
                        }
                    }, context)
                .start(
                    context,
                    FLYER_DEV_KEY,
                    object : AppsFlyerRequestListener {
                        override fun onSuccess() {
                            Log.d("RD_", "Launch sent successfully")
                        }

                        override fun onError(errorCode: Int, errorDesc: String) {
                            Log.d(
                                "RD_", "Launch failed to be sent:\n" +
                                        "Error code: " + errorCode + "\n"
                                        + "Error description: " + errorDesc
                            )
                        }
                    }
                )
        }
    }
}