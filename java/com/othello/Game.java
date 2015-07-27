package com.othello;

import android.app.Fragment;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by john on 7/25/15.
 */
public class Game extends Fragment {
    private GLSurfaceView mGLView;
    private int size;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        size = getArguments().getInt("size");
        Log.w("game", ""+size);
        mGLView = new MyGLSurfaceView(getActivity());
        mGLView.setLayoutParams(new ViewGroup.LayoutParams(size, size));
        return mGLView;
    }

    class MyGLSurfaceView extends GLSurfaceView {

        private final MyGLRenderer mRenderer;

        @Override
        public boolean onTouchEvent(MotionEvent e) {
            // MotionEvent reports input details from the touch screen
            // and other input controls. In this case, you are only
            // interested in events where the touch position changed.

            int x = (int) e.getX()*8/size;
            int y = (int) e.getY()*8/size;

            switch (e.getAction()) {
                case MotionEvent.ACTION_UP:
                    Log.w("click", "x" + x + " y" + y);
//                    mRenderer.putWhite();
                    break;
            }
            return true;
        }

        public MyGLSurfaceView(Context context) {
            super(context);

            // Create an OpenGL ES 2.0 context
            setEGLContextClientVersion(2);

            mRenderer = new MyGLRenderer();

            // Set the Renderer for drawing on the GLSurfaceView
            setRenderer(mRenderer);
            // Render the view only when there is a change in the drawing data
            setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        }
    }
}
