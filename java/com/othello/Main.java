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
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.ToggleButton;


public class Main extends Activity {

    GUI firstFragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        Spinner spinner = (Spinner) findViewById(R.id.lvl);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter < CharSequence > adapter = ArrayAdapter.createFromResource(this,
                R.array.lvl_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
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

    public void start(View view) {
        boolean pvp, help;
        Spinner spinner = (Spinner) findViewById(R.id.lvl);
        int lvl = spinner.getSelectedItemPosition();
        RadioButton radio = (RadioButton) findViewById(R.id.pvp);
        pvp = radio.isChecked();
        ToggleButton toggle = (ToggleButton)findViewById(R.id.help);
        help = toggle.isChecked();

        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point p = new Point();
        display.getSize(p);
        int size = p.x;

        setContentView(R.layout.game);
        if (findViewById(R.id.fragc) != null) {

            firstFragment = new GUI();

            Bundle args = new Bundle();
            args.putInt("size", size);
            args.putInt("lvl", lvl);
            args.putBoolean("pvp", pvp);
            args.putBoolean("help", help);
            firstFragment.setArguments(args);
            FragmentManager FM = getFragmentManager();
            FragmentTransaction transaction = FM.beginTransaction();
            transaction.replace(R.id.fragc, firstFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            FM.executePendingTransactions();

        }
    }
    @Override
    public void onBackPressed() {
        if (findViewById(R.id.fragc) == null) {
            finish();
        }else{
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("End game")
                    .setMessage("Are you sure you want to end this game")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }

    }

}