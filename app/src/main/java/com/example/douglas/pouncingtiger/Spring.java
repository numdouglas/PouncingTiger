package com.example.douglas.pouncingtiger;

import GameHose.GameObject;

/**
 * Created by user on 4/5/2017.
 */

public class Spring extends GameObject {
    public static float SPRING_WIDTH = 0.3f;
    public static float SPRING_HEIGHT = 0.3f;
    public Spring(float x, float y) {
        super(x, y, SPRING_WIDTH, SPRING_HEIGHT);//X,Y for the centre
    }
}

