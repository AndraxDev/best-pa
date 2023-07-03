package org.eu.best.pa.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.text.method.LinkMovementMethod
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
import com.google.android.material.button.MaterialButton
import org.eu.best.pa.Config

import org.eu.best.pa.R
import org.eu.best.pa.ui.LoginActivity
import org.eu.best.pa.ui.MainActivity

class AccountFragment : Fragment() {

    companion object {
        const val PROFILE_URL = "${Config.PA_URL}${Config.PA_PROFILE}"
        const val STATE_LOAD_ICON = 0
        const val STATE_LOAD_NAME = 1
    }

    private var btnMenu: ImageButton? = null
    private var profileIcon: WebView? = null
    private var actionParser: WebView? = null
    private var name: TextView? = null
    private var textBirthday: TextView? = null
    private var textGender: TextView? = null
    private var textNationality: TextView? = null
    private var textLbg: TextView? = null
    private var textStudentId: TextView? = null
    private var textStudentUntil: TextView? = null
    private var textPhone: TextView? = null
    private var textBestEmail: TextView? = null
    private var textEmail: TextView? = null
    private var textOtherEmail: TextView? = null
    private var textOtherContacts: TextView? = null
    private var textWebsite: TextView? = null
    private var btnReimbursements: MaterialButton? = null

    private var loadState = STATE_LOAD_ICON

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
            profileIcon?.loadUrl(url)

            Handler(Looper.getMainLooper()).postDelayed(
                {
                    run {
                        actionParser?.loadUrl("javascript:window.HTMLOUT.loadUserData(document.getElementsByTagName('td')[4].innerHTML + ' ' + document.getElementsByTagName('td')[6].innerHTML," +
                                "document.getElementsByTagName('td')[8].innerHTML," +
                                "document.getElementsByTagName('td')[10].innerHTML," +
                                "document.getElementsByTagName('td')[12].innerHTML," +
                                "document.getElementsByTagName('td')[14].innerHTML," +
                                "document.getElementsByTagName('td')[20].innerHTML," +
                                "document.getElementsByTagName('td')[22].innerHTML," +
                                "document.getElementsByTagName('td')[25].innerHTML," +
                                "document.getElementsByTagName('td')[27].innerHTML," +
                                "document.getElementsByTagName('td')[29].innerHTML," +
                                "document.getElementsByTagName('td')[31].innerHTML," +
                                "document.getElementsByTagName('td')[33].innerHTML," +
                                "document.getElementsByTagName('td')[35].innerHTML" +
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

    @JavascriptInterface
    fun loadUserData(
        name: String,
        birthday: String,
        gender: String,
        nationality: String,
        lbg: String,
        studentID: String,
        studentStatusUntil: String,
        phone: String,
        bestEmail: String,
        email: String,
        otherEmail: String,
        otherContacts: String,
        site: String
    ) {
        requireActivity().runOnUiThread {
            val parsedName = name.replace("\n", "").trim()
            this.name?.text = parsedName

            textBirthday?.text = birthday.replace("\n", "").trim()
            textGender?.text = gender.replace("\n", "").trim()
            textNationality?.text = nationality.replace("\n", "").trim()
            textLbg?.text = Html.fromHtml(lbg, Html.FROM_HTML_MODE_COMPACT)
            textStudentId?.text = studentID.replace("\n", "").trim()
            textStudentUntil?.text = studentStatusUntil.replace("until", "").trim()
            textPhone?.text = phone.replace("\n", "").trim()
            textBestEmail?.text = Html.fromHtml(bestEmail, Html.FROM_HTML_MODE_COMPACT)
            textEmail?.text = Html.fromHtml(email, Html.FROM_HTML_MODE_COMPACT)
            textOtherEmail?.text = Html.fromHtml(otherEmail, Html.FROM_HTML_MODE_COMPACT)
            textOtherContacts?.text = Html.fromHtml(otherContacts, Html.FROM_HTML_MODE_COMPACT)
            textWebsite?.text = Html.fromHtml(site, Html.FROM_HTML_MODE_COMPACT)

            textBestEmail?.movementMethod = LinkMovementMethod.getInstance()
            textEmail?.movementMethod = LinkMovementMethod.getInstance()
            textOtherEmail?.movementMethod = LinkMovementMethod.getInstance()
            textWebsite?.movementMethod = LinkMovementMethod.getInstance()
        }
    }

    @SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnMenu = view.findViewById(R.id.btn_menu)
        profileIcon = view.findViewById(R.id.profile_icon)
        actionParser = view.findViewById(R.id.action_parser)
        name = view.findViewById(R.id.account_name)

        textBirthday = view.findViewById(R.id.text_birthday)
        textGender = view.findViewById(R.id.text_gender)
        textNationality = view.findViewById(R.id.text_nationality)
        textLbg = view.findViewById(R.id.text_lbg)
        textStudentId = view.findViewById(R.id.text_student_id)
        textStudentUntil = view.findViewById(R.id.text_student_until)
        textPhone = view.findViewById(R.id.text_phone)
        textBestEmail = view.findViewById(R.id.text_best_email)
        textEmail = view.findViewById(R.id.text_email)
        textOtherEmail = view.findViewById(R.id.text_other_email)
        textOtherContacts = view.findViewById(R.id.text_other_contacts)
        textWebsite = view.findViewById(R.id.text_website)
        btnReimbursements = view.findViewById(R.id.btn_reimbursements)

        btnMenu?.setImageResource(R.drawable.ic_menu)

        btnMenu?.setOnClickListener {
            val parentActivity = requireActivity()

            if (parentActivity is MainActivity) {
                parentActivity.openDrawer()
            }
        }

        profileIcon?.setBackgroundColor(0x00000000)
        actionParser?.setBackgroundColor(0x00000000)

        profileIcon?.settings?.javaScriptEnabled = true
        profileIcon?.settings?.domStorageEnabled = true
        profileIcon?.settings?.useWideViewPort = true
        profileIcon?.isVerticalScrollBarEnabled = false
        profileIcon?.isHorizontalScrollBarEnabled = false
        profileIcon?.addJavascriptInterface(this, "HTMLOUT")
        profileIcon?.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                val cookieManager: CookieManager = CookieManager.getInstance()
                cookieManager.setAcceptCookie(true)
                CookieManager.getInstance().flush()
            }
        }

        profileIcon?.setOnTouchListener { _, event -> event.action == MotionEvent.ACTION_MOVE }

        actionParser?.settings?.javaScriptEnabled = true
        actionParser?.settings?.domStorageEnabled = true
        actionParser?.settings?.useWideViewPort = true
        actionParser?.isVerticalScrollBarEnabled = false
        actionParser?.isHorizontalScrollBarEnabled = false
        actionParser?.addJavascriptInterface(this, "HTMLOUT")
        actionParser?.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                when (loadState) {
                    STATE_LOAD_ICON -> {
                        val cookieManager: CookieManager = CookieManager.getInstance()
                        cookieManager.setAcceptCookie(true)
                        CookieManager.getInstance().flush()
                        // profileIcon?.loadUrl("javascript:window.HTMLOUT.processHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');")
                        actionParser?.loadUrl("javascript:window.HTMLOUT.getProfileIcon(document.getElementsByClassName('img-profile')[0].src);")

                        loadState = STATE_LOAD_NAME
                    }
                }
            }
        }

        actionParser?.loadUrl(PROFILE_URL)
    }
}