package GameHose;

import android.util.Log;

/**
 * Created by user on 3/30/2017.
 */

public class FPSCounter {long startTime = System.nanoTime();
    int frames = 0;
    public void logFrame() {
        frames++;//frames will continue incrementing until approximately a second is reached
        if(System.nanoTime() - startTime >= 1000000000) {
            Log.d("FPSCounter", "fps: " + frames);
            frames = 0;
            startTime = System.nanoTime();
        }
    }
}


