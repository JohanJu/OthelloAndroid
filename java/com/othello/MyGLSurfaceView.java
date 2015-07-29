package com.othello;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by john on 7/27/15.
 */
public class MyGLSurfaceView extends GLSurfaceView {
    private int size;
    public int b = -1;
    public final MyGLRenderer mRenderer;

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        int x = (int) e.getX() * 8 / size;
        int y = (int) e.getY() * 8 / size;

        switch (e.getAction()) {
            case MotionEvent.ACTION_UP:
                Log.w("click", "x" + x + " y" + y);
                b = 10 * x + y;
                break;
        }
        return true;
    }

    public MyGLSurfaceView(Context context) {
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        mRenderer = new MyGLRenderer();

        setRenderer(mRenderer);

        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}