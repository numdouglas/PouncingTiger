package GameHose;

/**
 * Created by user on 4/3/2017.
 */

public class TextureRegion {//converts texture cordinates to pixel coordinates for easier construction and processing
    public final float u1, v1;
    public final float u2, v2;
    public final Texture texture;
    public TextureRegion(Texture texture, float x, float y, float width, float height) {
        this.u1 = x / texture.width;
        this.v1 = y / texture.height;
        this.u2 = this.u1 + width / texture.width;
        this.v2 = this.v1 + height / texture.height;
        this.texture = texture;
    }

}
