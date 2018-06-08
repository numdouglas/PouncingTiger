package GameHose;

import android.view.View.OnTouchListener;

import java.util.List;

import GameHose.Input.TouchEvent;

/**
 * Created by user on 3/21/2017.
 */

public interface TouchHandler extends OnTouchListener {
    public boolean isTouchDown(int pointer);
    public int getTouchX(int pointer);
    public int getTouchY(int pointer);
    public List<TouchEvent> getTouchEvents();
}
