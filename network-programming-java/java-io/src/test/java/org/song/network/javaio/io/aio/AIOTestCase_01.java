package org.song.network.javaio.io.aio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.*;

public class AIOTestCase_01 {

    //线程池
    private static ExecutorService executorService = Executors.newCachedThreadPool();

    //服务器通道
    public AsynchronousServerSocketChannel assc;

    private int port = 8787;

    /**
     *
     */
    @Test
    public void server() {
        try {
            // 创建线程组
            AsynchronousChannelGroup threadGroup = AsynchronousChannelGroup.withCachedThreadPool(executorService, 1);
            // 创建服务器通道
            assc = AsynchronousServerSocketChannel.open(threadGroup);
            //进行绑定
            assc.bind(new InetSocketAddress(port));

            System.out.println("server start , port : " + port);
            // 不阻塞
            assc.accept(this, new CompletionHandler<AsynchronousSocketChannel, AIOTestCase_01>() {
                /**
                 * 处理完成回调
                 * @param result
                 * @param attachment
                 */
                @Override
                public void completed(AsynchronousSocketChannel result, AIOTestCase_01 attachment) {
                    // 当有下一个客户端接入的时候 直接调用Server的accept方法，这样反复执行下去，保证多个客户端都可以阻塞
                    // 类似于递归调用, 或者责任链 模式, 当前连接处理后 传给下一个 让下一个在处理下一个链接
                    attachment.assc.accept(attachment, this);
                    //读取数据
                    ByteBuffer buf = ByteBuffer.allocate(1024);

                    result.read(buf, buf, new CompletionHandler<Integer, ByteBuffer>() {

                        /**
                         * 读取成功处理
                         * @param resultSize
                         * @param attachment
                         */
                        @Override
                        public void completed(Integer resultSize, ByteBuffer attachment) {
                            // 进行读取之后,重置标识位
                            attachment.flip();
                            // 获得读取的字节数
                            System.out.println("Server -> " + "收到客户端的数据长度为:" + resultSize);
                            // 获取读取的数据
                            String resultData = new String(attachment.array()).trim();
                            System.out.println("Server -> " + "收到客户端的数据信息为:" + resultData);
                            String response = "服务器响应, 收到了客户端发来的数据: " + resultData;
                            try {
                                ByteBuffer buf = ByteBuffer.allocate(1024);
                                buf.put(response.getBytes());
                                buf.flip();
                                result.write(buf).get();
                            } catch (InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
                            exc.printStackTrace();
                        }
                    });
                }

                /**
                 * 处理失败回调
                 * @param exc
                 * @param attachment
                 */
                @Override
                public void failed(Throwable exc, AIOTestCase_01 attachment) {
                    exc.printStackTrace();
                }
            });

            //一直阻塞 不让服务器停止
            Thread.sleep(Integer.MAX_VALUE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void client() {

        try {
            AsynchronousSocketChannel asc = AsynchronousSocketChannel.open();
            // 建立连接
            Future<Void> connect = asc.connect(new InetSocketAddress("127.0.0.1", port));

            TimeUnit.SECONDS.sleep(5);

            // 发送数据
            Future<Integer> write = asc.write(ByteBuffer.wrap("你好 啊;".getBytes()));
            write.get();

            TimeUnit.SECONDS.sleep(5);

            // 读取数据
            ByteBuffer buf = ByteBuffer.allocate(1024);
            asc.read(buf).get();
            buf.flip();
            byte[] respByte = new byte[buf.remaining()];
            buf.get(respByte);
            System.out.println(new String(respByte, "utf-8").trim());

        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void client2() throws Exception {
        AIOTestCase_Client02 c1 = new AIOTestCase_Client02(port);
        AIOTestCase_Client02 c2 = new AIOTestCase_Client02(port);
        AIOTestCase_Client02 c3 = new AIOTestCase_Client02(port);

        Thread.sleep(1000);

        c1.write("c1 aaa");
        c2.write("c2 bbbb");
        c3.write("c3 ccccc");

        Thread.sleep(1000);
    }
}
