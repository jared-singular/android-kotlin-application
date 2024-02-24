package com.singular.androidkotlinapplication

import android.app.Application
import android.content.Intent
import android.util.Log
import com.singular.sdk.Singular
import com.singular.sdk.SingularConfig
import com.singular.sdk.SingularLinkParams

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("Singular", "--Application-- Lifecycle onCreate")
        // Perform any application-wide initialization here

        // Initialize Singular SDK from the Application Class
        initSingularSDK()

        // Perform Any In-App Events before the MainActivity, if required...
        Singular.event("App Launch")
    }

    override fun onTerminate() {
        super.onTerminate()
        Log.d("Singular", "Application Lifecycle onTerminate")
        // Perform any application-wide terminations here
    }

    fun initSingularSDK(intent: Intent? = null) {
        val singularSdkKey = BuildConfig.SINGULAR_SDK_KEY
        val singularSdkSecret = BuildConfig.SINGULAR_SDK_SECRET

        // Singular Initialization Object
        val config = SingularConfig(singularSdkKey, singularSdkSecret)
            .withDDLTimeoutInSec(30)

        if(intent != null){
            Log.d("Singular", "Singular Init with Intent")
            Log.d("Singular", "Intent: $intent")
            config.withSingularLink(intent) { singularLinkParams ->
                handleDeeplink(singularLinkParams)
            }
        } else {
            Log.d("Singular", "Singular Init WITHOUT Intent - Skipping DeeplinkHandler")
        }

        Singular.init(this, config)
    }

    private fun handleDeeplink(singularLinkParams: SingularLinkParams){
        /* The handleDeeplink function parses the specific values from the Intent passed to the Singular SDK. */
        Log.d("Singular", "Singular handleDeeplink()")
        val deeplink = singularLinkParams.deeplink
        val passthrough = singularLinkParams.passthrough
        val isDeferred = singularLinkParams.isDeferred
        val urlParams = singularLinkParams.urlParameters

        // Add deep link handling code here
        Log.d("Singular", "Deeplink: $deeplink")
        Log.d("Singular", "Passthrough: $passthrough")
        Log.d("Singular", "isDeferred: $isDeferred")
        Log.d("Singular", "urlParams: $urlParams")
    }

}