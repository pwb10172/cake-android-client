package com.waracle.androidtest.Utilities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Riad on 20/05/2015.
 */
public class StreamUtils {
    private static final String TAG = StreamUtils.class.getSimpleName();

    public static byte[] readUnknownFully(InputStream stream) throws IOException {
        // Read in stream of bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bufferLength = 0;

        while ((bufferLength = stream.read(buffer)) > 0)
        {
            baos.write(buffer, 0, bufferLength);
        }

        byte[] bytes = baos.toByteArray();
        baos.close();
        // Return the raw byte array.
        return bytes;
    }
}
