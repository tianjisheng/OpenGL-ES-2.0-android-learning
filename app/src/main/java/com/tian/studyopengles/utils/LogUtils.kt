package com.tian.studyopengles.utils

import android.util.Log


object LogUtils {
    private const val TAG: String = "OpenGLStudy"
    fun i(log: String) {
        Log.i(TAG, log)
    }

    fun w(log: String) {
        Log.w(TAG, log)
    }

    fun e(log: String) {
        Log.e(TAG, log)
    }
}