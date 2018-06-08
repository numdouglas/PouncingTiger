package GameHose;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by user on 3/21/2017.
 */

public interface FileIO {
    public InputStream readAsset(String fileName) throws IOException;
    public InputStream readFile(String fileName) throws IOException;
    public OutputStream writeFile(String fileName) throws IOException;
}
