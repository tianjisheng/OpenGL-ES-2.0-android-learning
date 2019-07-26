package com.tian.studyopengles.points

import android.app.Activity
import android.opengl.GLSurfaceView
import android.os.Bundle

class OnePoints : Activity() {
    private lateinit var mGLSurfaceView: GLSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mGLSurfaceView = GLSurfaceView(this)
        setContentView(mGLSurfaceView)

        mGLSurfaceView.setEGLContextClientVersion(2)
        mGLSurfaceView.setRenderer(OnePointsRender())
        mGLSurfaceView.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
    }

    override fun onPause() {
        super.onPause()
        mGLSurfaceView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mGLSurfaceView.onResume()
    }
}