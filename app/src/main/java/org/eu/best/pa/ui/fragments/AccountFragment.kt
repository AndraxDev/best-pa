package org.eu.best.pa.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton

import androidx.fragment.app.Fragment

import org.eu.best.pa.R
import org.eu.best.pa.ui.LoginActivity
import org.eu.best.pa.ui.MainActivity

class AccountFragment : Fragment() {

    private var btnMenu: ImageButton? = null
    private var profileIcon: WebView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    @JavascriptInterface
    fun getProfileIcon(url: String) {
        requireActivity().runOnUiThread {
            // profileIcon?.loadUrl(url)
        }
    }

    @JavascriptInterface
    fun processHTML(html: String?) {
        requireActivity().runOnUiThread {
            if (html?.contains("<input class=\"form-control form-control-user\" type=\"text\" name=\"username\"") == true) {
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
                requireActivity().finish()
            } else {
                /* nothing */
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnMenu = view.findViewById(R.id.btn_menu)

        btnMenu?.setImageResource(R.drawable.ic_menu)

        btnMenu?.setOnClickListener {
            val parentActivity = requireActivity()

            if (parentActivity is MainActivity) {
                parentActivity.openDrawer()
            }
        }

        profileIcon = view.findViewById(R.id.profile_icon)

        profileIcon?.setBackgroundColor(0x00000000)

        profileIcon?.settings?.javaScriptEnabled = true
        profileIcon?.settings?.domStorageEnabled = true
        profileIcon?.settings?.useWideViewPort = true
        profileIcon?.isVerticalScrollBarEnabled = false
        profileIcon?.isHorizontalScrollBarEnabled = false
        profileIcon?.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                val cookieManager: CookieManager = CookieManager.getInstance()
                cookieManager.setAcceptCookie(true)
                CookieManager.getInstance().flush()
                profileIcon?.loadUrl("javascript:window.HTMLOUT.processHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');")
            }
        }

        profileIcon?.setOnTouchListener { _, event -> event.action == MotionEvent.ACTION_MOVE }
    }
}