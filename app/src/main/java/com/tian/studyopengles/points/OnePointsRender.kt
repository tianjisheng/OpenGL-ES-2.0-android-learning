package com.tian.studyopengles.points

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import com.tian.studyopengles.utils.GL2Utils
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class OnePointsRender : GLSurfaceView.Renderer {
    private val V_SHADER_SOURCE = "" +
            "void main(){\n" +
            "gl_Position = vec4(0.0,0.0,0.0,1.0);\n" +
            "gl_PointSize = 10.0;\n" +
            "}"

    private val F_SHADER_SOURCE = "" +
            "void main(){\n" +
            "gl_FragColor = vec4(1.0,0.0,0.0,1.0);" +
            "}"

    private var mProgram: Int = -1
    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 1)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)

        mProgram = GL2Utils.createProgram(V_SHADER_SOURCE, F_SHADER_SOURCE)

        GLES20.glUseProgram(mProgram)
    }

}