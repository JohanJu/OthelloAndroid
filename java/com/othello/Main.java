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

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;


public class Main extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout


        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity
//        mGLView = new MyGLSurfaceView(this);
//        setContentView(mGLView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // The following call pauses the rendering thread.
        // If your OpenGL application is memory intensive,
        // you should consider de-allocating objects that
        // consume significant memory here.
//        mGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The following call resumes a paused rendering thread.
        // If you de-allocated graphic objects for onPause()
        // this is a good place to re-allocate them.
//        mGLView.onResume();
    }
    GUI firstFragment;
    public void start(View view) {

        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point p = new Point();
        display.getSize(p);
        int size = p.x;
        Log.w("main", "" + size);

        setContentView(R.layout.game);
        if (findViewById(R.id.fragc) != null) {
            Log.w("main", "fragc");

            firstFragment = new GUI();

            Bundle args = new Bundle();
            args.putInt("size", size);
            firstFragment.setArguments(args);
            //getFragmentManager().beginTransaction().add(R.id.fragc, firstFragment).commit();

            FragmentManager FM = getFragmentManager();
            FragmentTransaction transaction = FM.beginTransaction();
            transaction.replace(R.id.fragc, firstFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            FM.executePendingTransactions();



        }
    }
    public void start2(View view) {

    }

}