package com.othello;

import android.util.Log;

/**
 * Created by john on 8/4/15.
 */
public class Dot extends Square {
    public Dot() {
        Log.w("Dot", "con");
        size = 0.02f;
        squareCoords[0] = -size;
        squareCoords[1] = size;
        squareCoords[3] = -size;
        squareCoords[4] = -size;
        squareCoords[6] = size;
        squareCoords[7] = -size;
        squareCoords[9] = size;
        squareCoords[10] = size;
    }

}
