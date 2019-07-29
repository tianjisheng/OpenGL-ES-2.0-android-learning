package com.tian.studyopengles.triangle

import android.app.Activity
import android.opengl.GLSurfaceView
import android.os.Bundle

class TriangleActivity : Activity() {
    private lateinit var mGLSurfaceView: GLSurfaceView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mGLSurfaceView = GLSurfaceView(this)
        setContentView(mGLSurfaceView)

        mGLSurfaceView.setEGLContextClientVersion(2)
        mGLSurfaceView.setRenderer(TriangleRender())
        mGLSurfaceView.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY


    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }
}