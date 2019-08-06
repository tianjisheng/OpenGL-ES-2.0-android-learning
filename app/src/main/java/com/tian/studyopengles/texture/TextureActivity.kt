package com.tian.studyopengles.texture

import android.app.Activity
import android.graphics.BitmapFactory
import android.opengl.GLSurfaceView
import android.os.Bundle
import com.tian.studyopengles.R

class TextureActivity : Activity() {
    private lateinit var mGLSurfaceView: GLSurfaceView
    private lateinit var mRender: TextureRender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mGLSurfaceView = GLSurfaceView(this)
        setContentView(mGLSurfaceView)

        mGLSurfaceView.setEGLContextClientVersion(2)
        mRender = TextureRender()
        mRender.setBitmap(BitmapFactory.decodeResource(resources,R.drawable.f1))
        mGLSurfaceView.setRenderer(mRender)
        mGLSurfaceView.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    }

    override fun onPause() {
        super.onPause()
        mGLSurfaceView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mGLSurfaceView.onResume()
        mGLSurfaceView.requestRender()
    }
}