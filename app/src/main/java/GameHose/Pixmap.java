package GameHose;
import GameHose.Graphics.PixmapFormat;

/**
 * Created by user on 3/21/2017.
 */

public interface Pixmap {
        public int getWidth();
        public int getHeight();
        public PixmapFormat getFormat();
        public void dispose();
    }

