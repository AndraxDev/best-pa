package org.eu.best.pa.ui

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.CookieManager
import android.webkit.JavascriptInterface
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast

import androidx.browser.customtabs.CustomTabsCallback
import androidx.browser.customtabs.CustomTabsClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsServiceConnection
import androidx.browser.customtabs.CustomTabsSession
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import androidx.core.splashscreen.SplashScreen

import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.eu.best.pa.Config
import org.eu.best.pa.R

class LoginActivity : FragmentActivity() {

    companion object {
        private const val BASE_URL = "${Config.PA_URL}${Config.PA_WELCOME}"
    }

    /* Init components */
    private lateinit var serviceConnection: CustomTabsServiceConnection
    private lateinit var client: CustomTabsClient
    private lateinit var session: CustomTabsSession
    private var builder = CustomTabsIntent.Builder()
    private lateinit var splashScreen: SplashScreen

    class RabbitCallback : CustomTabsCallback() {
        override fun onNavigationEvent(navigationEvent: Int, extras: Bundle?) {
            super.onNavigationEvent(navigationEvent, extras)
            Log.d("Nav", navigationEvent.toString())
            when (navigationEvent) {
                1 -> Log.d("Navigation", "Start") // NAVIGATION_STARTED
                2 -> Log.d("Navigation", "Finished") // NAVIGATION_FINISHED
                3 -> Log.d("Navigation", "Failed") // NAVIGATION_FAILED
                4 -> Log.d("Navigation", "Aborted") // NAVIGATION_ABORTED
                5 -> Log.d("Navigation", "Tab Shown") // TAB_SHOWN
                6 -> Log.d("Navigation", "Tab Hidden") // TAB_HIDDEN
                else -> Log.d("Navigation", "Else")
            }
        }
    }

    /* Init UI */
    private var fieldLogin: EditText? = null
    private var fieldPassword: EditText? = null
    private var btnLogin: MaterialButton? = null
    private var actionParser: WebView? = null
    private var layoutLoading: LinearLayout? = null
    private var btnRequestAccess: MaterialButton? = null

    /* Init states */
    private var actionLogin = false
    private var failuresCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        splashScreen.setKeepOnScreenCondition { true }

        serviceConnection = object : CustomTabsServiceConnection() {
            override fun onCustomTabsServiceConnected(name: ComponentName, mClient: CustomTabsClient) {
                client = mClient
                client.warmup(0L)
                val callback = RabbitCallback()
                session = mClient.newSession(callback)!!
                builder.setSession(session)
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                Log.d("Service", "Disconnected")
            }
        }
        CustomTabsClient.bindCustomTabsService(this, "com.android.chrome", serviceConnection)

        initializeUI()
    }

    private fun initializeUI() {
        fieldLogin = findViewById(R.id.field_login)
        fieldPassword = findViewById(R.id.field_password)
        btnLogin = findViewById(R.id.btn_login)
        actionParser = findViewById(R.id.action_parser)
        layoutLoading = findViewById(R.id.layout_loading)
        btnRequestAccess = findViewById(R.id.btn_request_access)

        btnLogin?.isEnabled = false

        initializeLogin()
    }

    @JavascriptInterface
    fun processHTML(html: String?) {
        runOnUiThread {
            if (html?.contains("<input class=\"form-control form-control-user\" type=\"text\" name=\"username\"") == true) {
                btnLogin?.isEnabled = true
                layoutLoading?.visibility = View.GONE
                splashScreen.setKeepOnScreenCondition { false }

                if (failuresCounter > 0) {
                    MaterialAlertDialogBuilder(this, R.style.App_MaterialAlertDialog)
                        .setTitle("Failed to sign in")
                        .setMessage("Invalid username or password.")
                        .setPositiveButton("OK") { _, _ -> }
                        .show()
                }

                failuresCounter++
            } else {
                val cookieManager: CookieManager = CookieManager.getInstance()
                cookieManager.setAcceptCookie(true)
                CookieManager.getInstance().flush()
                layoutLoading?.visibility = View.VISIBLE
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initializeLogin() {
        actionParser?.settings?.javaScriptEnabled = true
        actionParser?.settings?.domStorageEnabled = true
        actionParser?.addJavascriptInterface(this, "HTMLOUT");
        actionParser?.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                actionParser?.loadUrl("javascript:window.HTMLOUT.processHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');")
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                splashScreen.setKeepOnScreenCondition { false }

                if (error?.description != "net::ERR_FAILED") {
                    MaterialAlertDialogBuilder(this@LoginActivity, R.style.App_MaterialAlertDialog)
                        .setTitle("Network error")
                        .setMessage("Failed to connect to the server.")
                        .setPositiveButton("Retry") { _, _ -> run {
                            btnLogin?.isEnabled = false
                            layoutLoading?.visibility = View.VISIBLE
                            actionParser?.loadUrl(BASE_URL)
                        } }
                        .show()
                }
            }
        }

        actionParser?.loadUrl(BASE_URL)

        btnLogin?.setOnClickListener {
            btnLogin?.isEnabled = false
            layoutLoading?.visibility = View.VISIBLE
            actionParser?.loadUrl("javascript:(function(){document.getElementsByName('username')[0].value='"
                    + fieldLogin?.text?.toString()
                    + "';document.getElementsByName('password')[0].value='"
                    + fieldPassword?.text?.toString() + "';document.getElementsByClassName('btn-user')[0].click();})()")
        }

        btnRequestAccess?.setOnClickListener {
            val customTabsIntent: CustomTabsIntent = builder.build()
            customTabsIntent.launchUrl(this, Uri.parse(Config.LBGS))
        }
    }
}

// (function(){document.getElementsByClassName("mb2")[0].src = "https://best.eu.org/images/BEST_signature.svg"})()


// (function(){document.getElementsByClassName("btn-user")[0].click();})()
// (function(){click_event = new CustomEvent('click'); btn_element = document.getElementsByClassName("btn-user"); btn_element.dispatchEvent(click_event);})()

// javascript:(function(){document.getElementsByName('username')[0].value='dostapenko82@gmail.com';document.getElementsByName('password')[0].value='M8i8XQc7';document.getElementsByTagName('form')[0].submit();})()