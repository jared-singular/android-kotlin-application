package com.singular.androidkotlinapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.singular.androidkotlinapplication.ui.theme.AndroidKotlinApplicationTheme
import com.singular.sdk.Attributes
import com.singular.sdk.Events
import com.singular.sdk.Singular
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Singular", "--MainActivity-- Lifecycle onCreate")

        // Access application context
        val context = applicationContext

        // Call initSingularSDK only when there is an intent
        if(intent != null){
            (application as MyApplication).initSingularSDK(intent)
        } else {
            Log.d("Singular", "Skipping SingularInit - no Intent")
        }

        // Call function to send a group of Singular Events for Testing.
        sendSingularEvents()

        setContent {
            AndroidKotlinApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
    override fun onResume() {
        super.onResume()
        Log.d("Singular", "--MainActivity-- Lifecycle onResume")
    }

    override fun onStart() {
        super.onStart()
        Log.d("Singular", "--MainActivity-- Lifecycle onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Singular", "--MainActivity-- Lifecycle onStop")
    }

    private fun sendSingularEvents(){
        /* Singular Standard Events: Full List and Recommended Events by Vertical
        See: https://support.singular.net/hc/en-us/articles/7648172966299-Singular-Standard-Events-Full-List-and-Recommended-Events-by-Vertical */

        /* EXAMPLE: Send Singular Standard Event "TutorialComplete" when User finishes viewing the tutorial */
        Singular.event(Events.sngTutorialComplete.toString())

        /* EXAMPLE: Alternatively, you may include attributes using a JSON Object. Send Singular Standard Event "TutorialComplete" when User finishes viewing the tutorial with attributes */
        val att = JSONObject().apply {
            put(Attributes.sngAttrContent.toString(), "GenericTutorialFlow")
            put(Attributes.sngAttrContentId.toString(), 32)
            put(Attributes.sngAttrContentType.toString(), "WelcomeVideo")
            put(Attributes.sngAttrSuccess.toString(), 75)
        }
        Singular.eventJSON(Events.sngTutorialComplete.toString(), att)

        // EXAMPLE: Send a Simple Revenue (IAP) Event
        Singular.revenue("USD", 0.99)

        // EXAMPLE: Send Custom Revenue Event with custom name
        Singular.customRevenue("Subscription_1yr", "USD", 49.99)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidKotlinApplicationTheme {
        Greeting("Android")
    }
}