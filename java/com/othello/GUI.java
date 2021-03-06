package com.othello;

import android.app.Fragment;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by john on 7/25/15.
 */
public class GUI extends Fragment {
    private MyGLSurfaceView mGLView;

    Game othello;
    Thread t;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int size = getArguments().getInt("size");
        mGLView = new MyGLSurfaceView(getActivity());
        mGLView.setSize(size);
        mGLView.setLayoutParams(new ViewGroup.LayoutParams(size, size));


        t = new Thread(new Runnable() {
            public void run() {
                othello = new Game(mGLView, getArguments().getInt("lvl"), getArguments().getBoolean("pvp"), getArguments().getBoolean("help"));
                othello.start();
            }
        });
        t.start();

        return mGLView;
    }


}
