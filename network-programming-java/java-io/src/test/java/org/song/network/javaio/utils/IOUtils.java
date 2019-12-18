package org.song.network.javaio.utils;

public class IOUtils {

    public static void close(AutoCloseable ... autoCloseables) {
        if (autoCloseables != null) {
            for (AutoCloseable autoCloseable : autoCloseables) {
                try {
                    autoCloseable.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
