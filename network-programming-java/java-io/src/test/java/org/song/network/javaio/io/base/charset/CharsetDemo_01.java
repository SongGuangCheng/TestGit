package org.song.network.javaio.io.base.charset;

import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;

public class CharsetDemo_01 {

    /**
     * 查看系统支持的编码集
     */
    @Test
    public void test01() {
        Charset.availableCharsets().forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });
    }
}
