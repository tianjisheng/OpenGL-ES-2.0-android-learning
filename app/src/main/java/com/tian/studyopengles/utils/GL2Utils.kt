package com.tian.studyopengles.utils

import android.opengl.GLES20
import android.util.Log

object GL2Utils {
    fun loadShader(shaderType: Int, shaderSource: String): Int {
        var shader = GLES20.glCreateShader(shaderType)
        GLES20.glShaderSource(shader, shaderSource)
        GLES20.glCompileShader(shader)
        val status = IntArray(1)
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, status, 0)
        if (status[0] == 0) {
            val errorInfo = GLES20.glGetShaderInfoLog(shader)
            GLES20.glDeleteShader(shader)
            log("loadShader type = $shaderType,error info = $errorInfo")
            shader = 0
        }
        return shader
    }

    fun createProgram(vertexShader: String, fragmentShader: String): Int {
        val vsh = loadShader(GLES20.GL_VERTEX_SHADER, vertexShader)
        if (vsh == 0) {
            //加载着色器失败
            return 0
        }

        val fsh = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader)
        if (fsh == 0) {
            //加载着色器失败
            return 0
        }

        var program = GLES20.glCreateProgram()
        checkGlError("createProgram")
        if (program != 0) {
            GLES20.glAttachShader(program, vsh)
            checkGlError("attach vsh")
            GLES20.glAttachShader(program, fsh)
            checkGlError("attach fsh")
            GLES20.glLinkProgram(program)
            val link = IntArray(1)
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, link, 0)
            if (link[0] != GLES20.GL_TRUE) {
                val errorInfo = GLES20.glGetProgramInfoLog(program)
                log("program link error==$errorInfo")
                GLES20.glDeleteProgram(program)
                program = 0
            }
        }
        return program
    }

    //检查每一步操作是否有错误的方法
    fun checkGlError(op: String) {
        var error: Int = GLES20.glGetError()
        if (error != GLES20.GL_NO_ERROR) {
            log("ES20_ERROR $op: glError $error")
        }
    }

    fun log(log: String) {
        Log.i("GLDemo", log)
    }
}