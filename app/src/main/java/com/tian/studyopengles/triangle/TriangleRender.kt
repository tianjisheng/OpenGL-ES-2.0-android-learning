package com.tian.studyopengles.triangle

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import com.tian.studyopengles.utils.GL2Utils
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class TriangleRender : GLSurfaceView.Renderer {
    private val V_SHADER_SOURCE = "" +
            "attribute vec4 a_Position;\n" +
            "void main(){\n" +
            "gl_Position = a_Position;\n" +
            "gl_PointSize = 10.0;\n" +//设为10.0，便于查看
            "}"
    private val F_SHADER_SOURCR = "" +
            "void main(){\n" +
            "gl_FragColor = vec4(1.0,0.0,0.0,1.0);\n" +
            "}"

    private var mProgram: Int = -1

        private val vertexPoints = floatArrayOf(
        0.0f, 0.0f,
        0.5f, 0.0f,
        0.5f, 0.3f,

        -0.1f, 0.0f,
        -0.5f, 0.0f,
        -0.5f, -0.3f
    )

//    private val vertexPoints = floatArrayOf(
//        0.0f, 0.0f,
//        0.25f, 0.25f,
//        0.25f, -0.25f,
//
//        0.5f, 0.0f
//    )


    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 6)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        mProgram = GL2Utils.createProgram(V_SHADER_SOURCE, F_SHADER_SOURCR)

        val buffer = ByteBuffer.allocateDirect(vertexPoints.size * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .put(vertexPoints)
            .position(0)

        var position = GLES20.glGetAttribLocation(mProgram, "a_Position")
        GLES20.glEnableVertexAttribArray(position)
        GLES20.glVertexAttribPointer(position, 2, GLES20.GL_FLOAT, false, 0, buffer)

        GL2Utils.validateProgram(mProgram)

        GLES20.glUseProgram(mProgram)

    }

}