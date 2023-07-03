package org.eu.best.pa.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import android.widget.TextView

import androidx.fragment.app.Fragment
import org.eu.best.pa.Config

import org.eu.best.pa.R
import org.eu.best.pa.ui.LoginActivity
import org.eu.best.pa.ui.MainActivity

class LBGFragment : Fragment() {

    companion object {
        const val LBG_URL = "${Config.PA_URL}${Config.PA_LBG}"
    }

    private var btnMenu: ImageButton? = null
    private var actionParser: WebView? = null
    private var lbgLogo: WebView? = null
    private var textUniversity: TextView? = null
    private var textStatus: TextView? = null
    private var textRegion: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lbg, container, false)
    }

    @JavascriptInterface
    fun getLbgData(university: String?, status: String, region: String) {
        requireActivity().runOnUiThread {
            textUniversity?.text = Html.fromHtml(university, Html.FROM_HTML_MODE_COMPACT)
            textStatus?.text = Html.fromHtml(status, Html.FROM_HTML_MODE_COMPACT)
            textRegion?.text = Html.fromHtml(region, Html.FROM_HTML_MODE_COMPACT)
        }
    }

    @JavascriptInterface
    fun getLbgIcon(url: String) {
        requireActivity().runOnUiThread {
            lbgLogo?.loadUrl(url)

            Handler(Looper.getMainLooper()).postDelayed(
                {
                    run {
                        actionParser?.loadUrl("javascript:window.HTMLOUT.getLbgData(document.getElementsByTagName('td')[4].innerHTML," +
                                "document.getElementsByTagName('td')[6].innerHTML," +
                                "document.getElementsByTagName('td')[8].innerHTML," +
                                ");")
                    }
                }, 100
            )
        }
    }

    @JavascriptInterface
    fun processHTML(html: String?) {
        requireActivity().runOnUiThread {
            if (html?.contains("<input class=\"form-control form-control-user\" type=\"text\" name=\"username\"") == true) {
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
                requireActivity().finish()
            } else {
                // profileIcon?.loadUrl("javascript:window.HTMLOUT.getProfileIcon(document.getElementsByClassName('img-profile')[0].src);")
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnMenu = view.findViewById(R.id.btn_menu)
        actionParser = view.findViewById(R.id.action_parser)
        lbgLogo = view.findViewById(R.id.lbg_logo)
        textUniversity = view.findViewById(R.id.text_university)
        textStatus = view.findViewById(R.id.text_status)
        textRegion = view.findViewById(R.id.text_region)

        lbgLogo?.setBackgroundColor(0x00000000)
        actionParser?.setBackgroundColor(0x00000000)

        lbgLogo?.settings?.javaScriptEnabled = true
        lbgLogo?.settings?.domStorageEnabled = true
        lbgLogo?.settings?.useWideViewPort = true
        lbgLogo?.isVerticalScrollBarEnabled = false
        lbgLogo?.isHorizontalScrollBarEnabled = false
        lbgLogo?.addJavascriptInterface(this, "HTMLOUT")
        lbgLogo?.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                val cookieManager: CookieManager = CookieManager.getInstance()
                cookieManager.setAcceptCookie(true)
                CookieManager.getInstance().flush()
            }
        }

        lbgLogo?.setOnTouchListener { _, event -> event.action == MotionEvent.ACTION_MOVE }

        actionParser?.settings?.javaScriptEnabled = true
        actionParser?.settings?.domStorageEnabled = true
        actionParser?.settings?.useWideViewPort = true
        actionParser?.isVerticalScrollBarEnabled = false
        actionParser?.isHorizontalScrollBarEnabled = false
        actionParser?.addJavascriptInterface(this, "HTMLOUT")
        actionParser?.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                val cookieManager: CookieManager = CookieManager.getInstance()
                cookieManager.setAcceptCookie(true)
                CookieManager.getInstance().flush()
                actionParser?.loadUrl("javascript:window.HTMLOUT.getLbgIcon(document.getElementsByTagName(\"img\")[8].src);")
            }
        }

        actionParser?.loadUrl(LBG_URL)

        btnMenu?.setImageResource(R.drawable.ic_menu)

        btnMenu?.setOnClickListener {
            val parentActivity = requireActivity()

            if (parentActivity is MainActivity) {
                parentActivity.openDrawer()
            }
        }
    }
}