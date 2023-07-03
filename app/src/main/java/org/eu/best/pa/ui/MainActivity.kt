package org.eu.best.pa.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import android.webkit.CookieManager
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.elevation.SurfaceColors
import com.google.android.material.navigation.NavigationBarView

import org.eu.best.pa.Config
import org.eu.best.pa.R

class MainActivity : FragmentActivity() {

    companion object {
        private const val BASE_URL = "${Config.PA_URL}${Config.PA_WELCOME}"
    }

    /* Init components */
    private lateinit var splashScreen: SplashScreen

    /* Init UI */
    private var actionParser: WebView? = null
    private var drawerLayout: DrawerLayout? = null
    private var bottomNavigationBar: BottomNavigationView? = null
    private var homeFragment: Fragment? = null
    private var lbgFragment: Fragment? = null
    private var tasksFragment: Fragment? = null
    private var accountFragment: Fragment? = null
    private var homeContainer: ConstraintLayout? = null
    private var lbgContainer: ConstraintLayout? = null
    private var tasksContainer: ConstraintLayout? = null
    private var accountContainer: ConstraintLayout? = null
    private var drawerFragment: Fragment? = null

    /* Init states */
    private var state = 0
    private var bottomNavigationBarAnimationState = false
    private var selectedTab: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        splashScreen.setKeepOnScreenCondition { true }

        window.navigationBarColor = SurfaceColors.SURFACE_2.getColor(this)

        initializeUI(savedInstanceState)
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt("tab", selectedTab)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        selectedTab = savedInstanceState.getInt("tab")
    }

    @JavascriptInterface
    fun processHTML(html: String?) {
        runOnUiThread {
            if (html?.contains("<input class=\"form-control form-control-user\" type=\"text\" name=\"username\"") == true) {
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            } else {
                splashScreen.setKeepOnScreenCondition { false }
            }
        }
    }

    private fun initializeUI(savedInstanceState: Bundle?) {
        actionParser = findViewById(R.id.action_parser)
        drawerLayout = findViewById(R.id.drawer_layout)
        bottomNavigationBar = findViewById(R.id.navigation_bar)
        homeContainer = findViewById(R.id.container_home)
        lbgContainer = findViewById(R.id.container_lbg)
        tasksContainer = findViewById(R.id.container_tasks)
        accountContainer = findViewById(R.id.container_account)

        homeFragment = supportFragmentManager.findFragmentById(R.id.fragment_home)
        lbgFragment = supportFragmentManager.findFragmentById(R.id.fragment_lbg)
        tasksFragment = supportFragmentManager.findFragmentById(R.id.fragment_tasks)
        accountFragment = supportFragmentManager.findFragmentById(R.id.fragment_account)
        drawerFragment = supportFragmentManager.findFragmentById(R.id.fragment_drawer)

        if (savedInstanceState != null) {
            onRestoredState(savedInstanceState)
        }

        initializeLogic()
    }

    @SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
    private fun initializeLogic() {
        actionParser?.settings?.javaScriptEnabled = true
        actionParser?.settings?.domStorageEnabled = true
        actionParser?.addJavascriptInterface(this, "HTMLOUT")
        actionParser?.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                val cookieManager: CookieManager = CookieManager.getInstance()
                cookieManager.setAcceptCookie(true)
                CookieManager.getInstance().flush()
                actionParser?.loadUrl("javascript:window.HTMLOUT.processHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');")
            }
        }

        actionParser?.loadUrl(BASE_URL)

        initializeBottomNavigationView()
    }

    private fun onRestoredState(savedInstanceState: Bundle?) {
        selectedTab = savedInstanceState!!.getInt("tab")

        when (selectedTab) {
            1 -> {
                bottomNavigationBar?.selectedItemId = R.id.menu_home
                switchLayout(lbgContainer!!, tasksContainer!!, accountContainer!!, homeContainer!!)
            }
            2 -> {
                bottomNavigationBar?.selectedItemId = R.id.menu_lbg
                switchLayout(homeContainer!!, tasksContainer!!, accountContainer!!, lbgContainer!!)
            }
            3 -> {
                bottomNavigationBar?.selectedItemId = R.id.menu_tasks
                switchLayout(homeContainer!!, lbgContainer!!, accountContainer!!, tasksContainer!!)
            }
            4 -> {
                bottomNavigationBar?.selectedItemId = R.id.menu_profile
                switchLayout(homeContainer!!, lbgContainer!!, tasksContainer!!, accountContainer!!)
            }
        }
    }

    private fun initializeBottomNavigationView() {
        bottomNavigationBar!!.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item: MenuItem ->
            if (!bottomNavigationBarAnimationState) {
                bottomNavigationBarAnimationState = true
                when (item.itemId) {
                    R.id.menu_home -> {
                        menuHome()
                        return@OnItemSelectedListener true
                    }
                    R.id.menu_lbg -> {
                        menuLbg()
                        return@OnItemSelectedListener true
                    }
                    R.id.menu_tasks -> {
                        menuTasks()
                        return@OnItemSelectedListener true
                    }
                    R.id.menu_profile -> {
                        menuProfile()
                        return@OnItemSelectedListener true
                    }
                    else -> {
                        return@OnItemSelectedListener false
                    }
                }
            } else return@OnItemSelectedListener false
        })
    }

    private fun menuHome() {
        Handler(Looper.getMainLooper()).postDelayed({
            openHome()
        }, 50)
    }

    private fun menuLbg() {
        Handler(Looper.getMainLooper()).postDelayed({
            openLbg()
        }, 50)
    }

    private fun menuTasks() {
        Handler(Looper.getMainLooper()).postDelayed({
            openTasks()
        }, 50)
    }

    private fun menuProfile() {
        Handler(Looper.getMainLooper()).postDelayed({
            openProfile()
        }, 50)
    }

    private fun openHome() {
        transition(
            lbgContainer!!,
            tasksContainer!!,
            accountContainer!!,
            switchHomeAnimation,
            2,
            3,
            4,
            1
        )
    }

    private fun openLbg() {
        transition(
            homeContainer!!,
            tasksContainer!!,
            accountContainer!!,
            switchLbgAnimation,
            1,
            3,
            4,
            2
        )
    }

    private fun openTasks() {
        transition(
            homeContainer!!,
            lbgContainer!!,
            accountContainer!!,
            switchTasksAnimation,
            1,
            2,
            4,
            3
        )
    }

    private fun openProfile() {
        transition(
            homeContainer!!,
            lbgContainer!!,
            tasksContainer!!,
            switchAccountAnimation,
            1,
            2,
            3,
            4
        )
    }

    private fun animate(target: ConstraintLayout, listener: AnimatorListenerAdapter) {
        bottomNavigationBarAnimationState = true
        target.visibility = View.VISIBLE
        target.alpha = 1f
        target.animate().setDuration(150).alpha(0f).setListener(listener).start()
    }

    private fun hideFragment(fragmentManager: FragmentManager, fragment: Fragment) {
        if (!isFinishing && !fragmentManager.isDestroyed) {
            fragmentManager.beginTransaction().hide(fragment).commit()
        }
    }

    private fun showFragment(fragmentManager: FragmentManager, fragment: Fragment) {
        if (!isFinishing && !fragmentManager.isDestroyed) {
            fragmentManager.beginTransaction().show(fragment).commit()
        }
    }

    private fun animationListenerCallback(
        fragmentManager: FragmentManager,
        layout1: ConstraintLayout,
        layout2: ConstraintLayout,
        layout3: ConstraintLayout,
        layoutToShow: ConstraintLayout,
        fragment1: Fragment?,
        fragment2: Fragment?,
        fragment3: Fragment?,
        fragmentToShow: Fragment?
    ) {
        layoutToShow.visibility = View.VISIBLE
        layoutToShow.alpha = 0f

        if (fragment1 != null) {
            hideFragment(fragmentManager, fragment1)
        }

        if (fragment2 != null) {
            hideFragment(fragmentManager, fragment2)
        }

        if (fragment3 != null) {
            hideFragment(fragmentManager, fragment3)
        }

        if (fragmentToShow != null) {
            showFragment(fragmentManager, fragmentToShow)
        }

        layoutToShow.animate()?.setDuration(150)?.alpha(1f)
            ?.setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        switchLayout(layout1, layout2, layout3, layoutToShow)

                        bottomNavigationBarAnimationState = false
                    }, 10)
                }
            })?.start()
    }

    private fun switchLayout(
        layout1: ConstraintLayout,
        layout2: ConstraintLayout,
        layout3: ConstraintLayout,
        layoutToShow: ConstraintLayout
    ) {
        layout1.visibility = View.GONE
        layout2.visibility = View.GONE
        layout3.visibility = View.GONE
        layoutToShow.visibility = View.VISIBLE
    }

    private fun transition(
        l1: ConstraintLayout,
        l2: ConstraintLayout,
        l3: ConstraintLayout,
        animation: AnimatorListenerAdapter,
        tab1: Int,
        tab2: Int,
        tab3: Int,
        targetTab: Int
    ) {
        bottomNavigationBarAnimationState = true
        when (selectedTab) {
            tab1 -> animate(l1, animation)
            tab2 -> animate(l2, animation)
            tab3 -> animate(l3, animation)
            else -> {
                bottomNavigationBarAnimationState = false
            }
        }

        selectedTab = targetTab
    }

    private val switchHomeAnimation: AnimatorListenerAdapter =
        object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                Handler(Looper.getMainLooper()).postDelayed({
                    animationListenerCallback(
                        supportFragmentManager,
                        lbgContainer!!,
                        tasksContainer!!,
                        accountContainer!!,
                        homeContainer!!,
                        lbgFragment,
                        tasksFragment,
                        accountFragment,
                        homeFragment
                    )
                }, 10)
            }
        }

    private val switchLbgAnimation: AnimatorListenerAdapter =
        object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                Handler(Looper.getMainLooper()).postDelayed({
                    animationListenerCallback(
                        supportFragmentManager,
                        homeContainer!!,
                        tasksContainer!!,
                        accountContainer!!,
                        lbgContainer!!,
                        homeFragment,
                        tasksFragment,
                        accountFragment,
                        lbgFragment
                    )
                }, 10)
            }
        }

    private val switchTasksAnimation: AnimatorListenerAdapter =
        object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                Handler(Looper.getMainLooper()).postDelayed({
                    animationListenerCallback(
                        supportFragmentManager,
                        homeContainer!!,
                        lbgContainer!!,
                        accountContainer!!,
                        tasksContainer!!,
                        homeFragment,
                        lbgFragment,
                        accountFragment,
                        tasksFragment
                    )
                }, 10)
            }
        }

    private val switchAccountAnimation: AnimatorListenerAdapter =
        object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                Handler(Looper.getMainLooper()).postDelayed({
                    animationListenerCallback(
                        supportFragmentManager,
                        homeContainer!!,
                        lbgContainer!!,
                        tasksContainer!!,
                        accountContainer!!,
                        homeFragment,
                        lbgFragment,
                        tasksFragment,
                        accountFragment
                    )
                }, 10)
            }
        }

    fun openDrawer() {
        drawerLayout?.openDrawer(GravityCompat.START)
    }

    fun closeDrawer() {
        drawerLayout?.closeDrawer(GravityCompat.START)
    }
}