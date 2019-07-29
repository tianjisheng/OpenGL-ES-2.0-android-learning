package com.tian.studyopengles.points

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import com.tian.studyopengles.utils.GL2Utils
import com.tian.studyopengles.utils.LogUtils
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MultiplePointsRender : GLSurfaceView.Renderer {
    private val V_SHADER_SOURCE = "" +
            "attribute vec4 a_Position;\n" +
            "void main(){\n" +
            "gl_Position = a_Position;\n" +
            "gl_PointSize = 20.0;\n" +
            "}"

    private val F_SHADER_SOURCE = "" +
            "void main(){\n" +
            "gl_FragColor = vec4(1.0,0.0,0.0,1.0);\n" +
            "}"
    private val vertexPoints = floatArrayOf(
        0.0f, 0.0f,
        -0.5f, -0.5f,
        0.5f, 0.5f
    )
    private var mProgram: Int = -1

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 3)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {

        mProgram = GL2Utils.createProgram(V_SHADER_SOURCE, F_SHADER_SOURCE)

        val buffer = ByteBuffer.allocateDirect(vertexPoints.size * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .put(vertexPoints)
            .position(0)

        val position = GLES20.glGetAttribLocation(mProgram, "a_Position")
        GLES20.glVertexAttribPointer(position, 2, GLES20.GL_FLOAT, false, 0, buffer)
        GLES20.glEnableVertexAttribArray(position)

        GLES20.glValidateProgram(mProgram)
        val statusArray = IntArray(1)
        GLES20.glGetProgramiv(mProgram, GLES20.GL_VALIDATE_STATUS, statusArray, 0)

        val statusInfo = GLES20.glGetProgramInfoLog(mProgram)
        LogUtils.i("status code == ${statusArray[0]},status info == $statusInfo")

        GLES20.glUseProgram(mProgram)
    }

}