package GameHose;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by user on 3/31/2017.
 */

public class Camera2D {
    public final Vector2 position;
    public float zoom;
    public final float frustumWidth;
    public final float frustumHeight;
    final GLGraphics glGraphics;

    public Camera2D(GLGraphics glGraphics, float frustumWidth, float frustumHeight) {
        this.glGraphics = glGraphics;
        this.frustumWidth = frustumWidth;
        this.frustumHeight = frustumHeight;
        this.position = new Vector2(frustumWidth / 2, frustumHeight / 2);
        this.zoom = 1.0f;
    }

    public void setViewportAndMatrices() {//set viewport to span the whole screen and set projection matrix in accordance with specifications
        GL10 gl = glGraphics.getGL();
        gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(position.x - frustumWidth * zoom / 2,
                position.x + frustumWidth * zoom / 2,
                position.y - frustumHeight * zoom / 2,
                position.y + frustumHeight * zoom / 2,
                1, -1);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }//thsis method is called each frame so that we can start from a new slate. No more calls to open Gl to set our viewport andproj matrix
    public void touchToWorld(Vector2 touch) {//takes vector 2 instance containing touch coordinates and transforms them to world instance
        touch.x = (touch.x / (float) glGraphics.getWidth()) * frustumWidth * zoom;
        touch.y = (1 - touch.y / (float) glGraphics.getHeight()) * frustumHeight * zoom;
        touch.add(position).sub(frustumWidth * zoom / 2, frustumHeight * zoom / 2);
    }
}
