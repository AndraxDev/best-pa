/**************************************************************************
 * TESLASOFT APP TEMPLATE
 *
 * Copyright (c) 2023 Dmytro Ostapenko. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **************************************************************************/

package org.teslasoft.core

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast

import androidx.activity.OnBackPressedCallback
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity

import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import org.eu.best.pa.R

/** This activity will be opened if app os crashed. */
class CrashHandler : FragmentActivity() {

    private var error: String? = null

    private var textError: TextView? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        try {
            error = CustomActivityOnCrash.getStackTraceFromIntent(intent)

            if (error == null) {
                finishAndRemoveTask()
                overridePendingTransition(0, 0)
            }

            setContentView(R.layout.activity_debug)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

            onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    finishAndRemoveTask()
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                }
            })

            textError = findViewById(R.id.text_error)
            textError!!.setTextIsSelectable(true)
            textError!!.text = "\n$error"
        } catch (_: Exception) {
            finishAndRemoveTask()
            overridePendingTransition(0, 0)
        }
    }

    fun dismiss(v: View?) {
        finishAndRemoveTask()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    fun copy(v: View?) {
        val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Error", textError!!.text.toString())
        clipboard.setPrimaryClip(clip)

        Toast.makeText(
            applicationContext,
            "Copied to clipboard",
            Toast.LENGTH_SHORT
        ).show()
    }
}
