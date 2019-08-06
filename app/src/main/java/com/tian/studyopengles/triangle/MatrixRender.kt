package com.tian.studyopengles.triangle

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import com.tian.studyopengles.utils.GL2Utils
import com.tian.studyopengles.utils.LogUtils
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MatrixRender : GLSurfaceView.Renderer {
    private val V_SHADER_SOURCE = "" +
            "attribute vec4 a_Position;\n" +
            "uniform mat4 u_xformMatrix;\n" +
            "void main(){\n" +
            "gl_Position = u_xformMatrix * a_Position;\n" +
            "}"
    private val F_SHADER_SOURCR = "" +
            "void main(){\n" +
            "gl_FragColor = vec4(1.0,0.0,0.0,1.0);\n" +
            "}"

    private var mProgram: Int = -1

    private val ANGLE = 90.0
    private val radian = Math.PI * ANGLE / 180.0

    private val cosB: Float = Math.cos(radian).toFloat()
    private val sinB: Float = Math.sin(radian).toFloat()

    private val xformMatrix = floatArrayOf(
        cosB, sinB, 0.0f, 0.0f,
        -sinB, cosB, 0.0f, 0.0f,
        0.0f, 0.0f, 1.0f, 0.0f,
        0.0f, 0.0f, 0.0f, 1.0f
    )

//    private val xformMatrix = FloatArray(16)

    private val vertexPoints = floatArrayOf(
        0.0f, 0.5f,
        -0.5f, -0.5f,
        0.5f, -0.5f
    )


    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }


    var u_xformMatrix = -1

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        mProgram = GL2Utils.createProgram(V_SHADER_SOURCE, F_SHADER_SOURCR)

        val buffer = ByteBuffer.allocateDirect(vertexPoints.size * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .put(vertexPoints)
            .position(0)

        val position = GLES20.glGetAttribLocation(mProgram, "a_Position")
        LogUtils.i("position == $position")
        GLES20.glEnableVertexAttribArray(position)
        GLES20.glVertexAttribPointer(position, 2, GLES20.GL_FLOAT, false, 0, buffer)
        GL2Utils.checkGlError("VertexAttrib")



        u_xformMatrix = GLES20.glGetUniformLocation(mProgram, "u_xformMatrix")
        LogUtils.i("u_xformMatrix ===$u_xformMatrix")
        GL2Utils.checkGlError("uniform loaction")

        GL2Utils.validateProgram(mProgram)

        GLES20.glUseProgram(mProgram)

        //？？？为什么必须在glUseProgram才能赋值，不然会出现1282 错误
        GLES20.glUniformMatrix4fv(u_xformMatrix, 1, false, xformMatrix, 0)
        GL2Utils.checkGlError("Matrix4fv")


    }

}