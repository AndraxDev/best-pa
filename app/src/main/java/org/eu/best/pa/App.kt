package org.eu.best.pa

import android.app.Application
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.google.android.material.color.DynamicColors

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        DynamicColors.applyToActivitiesIfAvailable(this)

        /**
         * Initialize crash handler. This component is not necessary
         * but it will help to recognize bugs.
         * */
        CaocConfig.Builder.create()
            .backgroundMode(CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM)
            .enabled(true)
            .showErrorDetails(true)
            .showRestartButton(false)
            .logErrorOnRestart(true)
            .trackActivities(true)
            .minTimeBetweenCrashesMs(2000)
            .errorDrawable(R.mipmap.ic_launcher_round)
            .restartActivity(null)
            .errorActivity(org.teslasoft.core.CrashHandler::class.java)
            .eventListener(null)
            .customCrashDataCollector(null)
            .apply()
    }
}