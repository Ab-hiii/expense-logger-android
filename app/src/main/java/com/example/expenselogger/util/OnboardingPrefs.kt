package com.example.expenselogger.util

import android.content.Context

object OnboardingPrefs {

    private const val PREFS = "onboarding_prefs"
    private const val KEY_DONE = "onboarding_done"

    fun isDone(context: Context): Boolean {
        return context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getBoolean(KEY_DONE, false)
    }

    fun markDone(context: Context) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_DONE, true)
            .apply()
    }
}
