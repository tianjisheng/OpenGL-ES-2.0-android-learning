package com.tian.studyopengles.texture

import android.graphics.Bitmap
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import com.tian.studyopengles.utils.GL2Utils
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import android.opengl.GLUtils
import com.tian.studyopengles.utils.LogUtils


class TextureRender : GLSurfaceView.Renderer {

    private val V_SHADER_SOURCE = "" +
            "attribute vec4 a_Position;\n" +
            "attribute vec2 a_TexCoord;\n" +
            "varying vec2 v_TexCoord;\n" +
            "void main(){\n" +
            "gl_Position = a_Position;\n" +
            "v_TexCoord = a_TexCoord;\n" +
            "}\n"

    private val F_SHADER_SOURCE = "" +
            "precision mediump float;\n" +
            "uniform sampler2D u_Sample;\n" +
            "varying vec2 v_TexCoord;\n" +
            "void main(){\n" +
            "gl_FragColor = texture2D(u_Sample,v_TexCoord);\n" +
            "}\n"


    private var mProgram: Int = -1

    private var mBitmap: Bitmap? = null

    fun setBitmap(bitmap: Bitmap?) {
        mBitmap = bitmap
        LogUtils.i("setBitmap ${mBitmap == null}")
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

        val res = createTexture()
        LogUtils.i("create texture $res")

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)
    }

    private fun createTexture(): Int {
        val texture = IntArray(1)
        if (mBitmap != null && !mBitmap!!.isRecycled()) {
            //生成纹理
            GLES20.glGenTextures(1, texture, 0)
            //生成纹理
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture[0])
            GL2Utils.checkGlError("bind texture")
            //设置缩小过滤为使用纹理中坐标最接近的一个像素的颜色作为需要绘制的像素颜色
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST.toFloat())
            //设置放大过滤为使用纹理中坐标最接近的若干个颜色，通过加权平均算法得到需要绘制的像素颜色
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR.toFloat())
            //设置环绕方向S，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE.toFloat())
            //设置环绕方向T，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE.toFloat())
            //根据以上指定的参数，生成一个2D纹理
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, mBitmap, 0)
            GL2Utils.checkGlError("texImage2D")
            return texture[0]
        }
        return 0
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }

    private val positionArray = floatArrayOf(
        -1.0f, -1.0f,
        -1.0f, -0.0f,
        1.0f, -1.0f,
        1.0f, -0.0f
    )

    private val textureArray = floatArrayOf(

        0.0f, 0.5f,  // bottom right
        0.0f, 0.0f,  // bottom left
        1.0f, 0.5f,  // top left
        1.0f, 0.0f // top right

    )

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        mProgram = GL2Utils.createProgram(V_SHADER_SOURCE, F_SHADER_SOURCE)

        val positionBuffer = ByteBuffer.allocateDirect(positionArray.size * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .put(positionArray)
            .position(0)

        val texCoordBuffer = ByteBuffer.allocateDirect(textureArray.size * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .put(textureArray)
            .position(0)

        val position = GLES20.glGetAttribLocation(mProgram, "a_Position")
        GLES20.glEnableVertexAttribArray(position)
        GLES20.glVertexAttribPointer(position, 2, GLES20.GL_FLOAT, false, 0, positionBuffer)

        val texCoord = GLES20.glGetAttribLocation(mProgram, "a_TexCoord")
        GLES20.glEnableVertexAttribArray(texCoord)
        GLES20.glVertexAttribPointer(texCoord, 2, GLES20.GL_FLOAT, false, 0, texCoordBuffer)


        GLES20.glValidateProgram(mProgram)
        GLES20.glUseProgram(mProgram)
    }
}