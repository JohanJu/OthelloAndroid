/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.othello;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;


public class MyGLRenderer implements GLSurfaceView.Renderer {

    private Square mSquare, mDot;

    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private int[][] matb = new int[8][8];
    private int[][] mata = new int[8][8];
    private boolean help = false;

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        mSquare = new Square();
        mDot = new Dot();
        mSquare.init();
        mDot.init();
        mSquare.logSize();
    }

    public void setPiece(int[][] matb) {
        this.matb = matb;
        help = false;
    }
    public void setHelp(int[][] mata) {

        this.mata = mata;
        help = true;
        Log.w("setHelp",""+help);
    }


    public void onDrawFrame(GL10 unused) {
        Log.w("onDrawFrame",""+help);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        float step = -0.257f;
        float zero = 0.9f - step;
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.translateM(mViewMatrix, 0, zero, zero, 0f);
        for (int i = 0; i < 8; i++) {
            Matrix.translateM(mViewMatrix, 0, 0, step, 0f);
            for (int j = 0; j < 8; j++) {
                Matrix.translateM(mViewMatrix, 0, step, 0, 0f);
                Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
                mSquare.draw(mMVPMatrix, matb[j][i]);
                if (help && mata[j][i] == -1 ){
                    mDot.draw(mMVPMatrix, -1);
                }
            }
            Matrix.translateM(mViewMatrix, 0, -8 * step, 0, 0f);
        }
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    public static int loadShader(int type, String shaderCode) {

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
}