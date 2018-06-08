package GameHose;

/**
 * Created by user on 3/31/2017.
 */

public class DynamicObject extends GameObject {
    public final Vector2 velocity;
    public final Vector2 accel;
    public DynamicObject(float x, float y, float width, float height) {
        super(x, y, width, height);
        velocity = new Vector2();
        accel = new Vector2();
    }
}
