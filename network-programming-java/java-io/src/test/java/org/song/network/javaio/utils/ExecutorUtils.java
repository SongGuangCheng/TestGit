package org.song.network.javaio.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorUtils {

    public static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
}
