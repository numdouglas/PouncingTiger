package com.example.douglas.pouncingtiger;

import GameHose.GameObject;

/**
 * Created by user on 4/5/2017.
 */

public class Castle extends GameObject {
    public static float CASTLE_WIDTH = 1.7f;
    public static float CASTLE_HEIGHT = 1.7f;
    public Castle(float x, float y) {
        super(x, y, CASTLE_WIDTH, CASTLE_HEIGHT);
}
}
