package com.othello;

import android.app.Fragment;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by john on 7/25/15.
 */
public class GUI extends Fragment {
    private MyGLSurfaceView mGLView;

    Game othello;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int size = getArguments().getInt("size");
        mGLView = new MyGLSurfaceView(getActivity());
        mGLView.setSize(size);
        mGLView.setLayoutParams(new ViewGroup.LayoutParams(size, size));


        new Thread(new Runnable() {
            public void run() {
                othello = new Game(mGLView);
                othello.start();
            }
        }).start();

        return mGLView;
    }



}
