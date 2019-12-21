package org.song.network.nettydemo.api;

import io.netty.buffer.AbstractByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;

/**
 * Netty 中的零拷贝技术
 */
public class NettyZeroCopyTest {

    /**
     *
     */
    public void test() {
        // CompositeChannelBuffer Netty 5.x
        // Netty 4.x

        CompositeByteBuf byteBufs = new CompositeByteBuf(ByteBufAllocator.DEFAULT, true, 1);

        ByteBuf byteBuf = null;
        byteBufs.addComponent(byteBuf);

    }

}
