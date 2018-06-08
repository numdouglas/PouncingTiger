package GameHose;

import android.graphics.Bitmap;

/**
 * Created by user on 3/21/2017.
 */

public class AndroidPixmap implements Pixmap {
    Bitmap bitmap;
    Graphics.PixmapFormat format;
    public AndroidPixmap(Bitmap bitmap, Graphics.PixmapFormat format) {
        this.bitmap = bitmap;
        this.format = format;
    }
    public int getWidth() {
        return bitmap.getWidth();
    }
    public int getHeight() {
        return bitmap.getHeight();
    }
    public Graphics.PixmapFormat getFormat() {
        return format;
    }
    public void dispose() {
        bitmap.recycle();
    }
}

