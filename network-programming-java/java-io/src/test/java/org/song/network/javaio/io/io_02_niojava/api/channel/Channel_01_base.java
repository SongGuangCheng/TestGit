package org.song.network.javaio.io.io_02_niojava.api.channel;

import org.junit.jupiter.api.Test;

public class Channel_01_base {

    /**
     * Channel 通道
     * 一个 Channel 表示一个连接, 连接的双方可以是 硬件设备, 网络设备, 文件, 程序等
     * 定义了两个基本方法
     * isOpen() 连接是否开启
     * close(); 关闭一个连接
     * <p>
     * 其子类 包括两大类, 文件通道 FileChannel 和 网络通道 SocketChannel
     * Channel
     * -   - ReadableByteChannel 可读通道
     * -   - WritableByteChannel 可写通道
     * -   - InterruptibleChannel 可异步关闭和可中断通道
     * -   - NetworkChannel 可以连接到网络 Socket 的通道
     * -   - SelectableChannel 可以被 selector 选择的通道
     * -   -   - GatheringByteChannel 聚集写通道
     * -   -   - ScatteringByteChannel 分散读通道
     * -   -   -   - FileChannel 文件通道
     * -   -   -   - SocketChannel Socket通道
     * -   -   -   - ServerSocketChannel 服务端Socket通道
     * <p>
     * FileChannel 通道 和 SocketChannel/ServerSocketChannel 通道, 通过继承不同的父类, 拥有不同的特性
     */
    @Test
    public void test_01_structure() {

    }

    /**
     * 关于读写通道
     * <p>
     * ReadableByteChannel 接口, 不能直接使用, 仅仅是定义了行为
     * 只定义了 读方法
     * int read(ByteBuffer dst); 将通道中数据读入 缓冲区, 并返回读到的字节数
     * read: channel -> buffer
     * WritableByteChannel 接口, 不能直接使用, 仅仅是定义了行为
     * 只定义了 写方法
     * int write(ByteBuffer src); 将缓冲区中的数据写入通道, 并返回写入的字节数
     * write: buffer -> channel
     * read/write 对象都是 channel, 表示从 channel 中读, 和向 channel 中写
     * <p>
     * <p>
     * 异步关闭 和 可中断 通道 InterruptibleChannel
     * 不可直接使用, 仅仅是定义了方法
     * close(), 此方法可以使得阻塞在通道上的其他 IO 操作, 中断
     * 能实现可中断的 IO 操作, 传统的java IO是不可中断的
     * <p>
     * <p>
     * 文件通道
     * FileChannel 抽象类
     * 连接到 文件 的通道
     * 包含方法
     * open(); 打开一个文件通道
     * read(); 从通道中读取数据
     * write(); 将数据写入到通道中
     * position(); 返回通道文件的 操作索引, 或设置通道文件的 操作索引
     * size(); 文件字节数
     * truncate()
     * force()
     * transferTo(); 将本文件通道中的数据直接发送到目标通道中
     * transferFrom(); 从目标通道中直接读取数据
     * map(); 创建一个内存映射文件 MappedByteBuffer
     * lock(); 给通道中指定的文件区域上锁, 或者整个通道上锁
     * tryLock(); 尝试获取通道中指定文件区域的锁, 或者整个通道的锁
     * <p>
     * <p>
     * 网络通道
     * <p>
     * NetworkChannel 接口
     * 连接到 Socket 的通道,
     * 提供的方法
     * bind(SocketAddress local); 绑定到本地地址
     * getLocalAddress(); 获取已绑定的地址
     * setOption(SocketOption<T> name, T value); 设置 Socket 选项
     * getOption(SocketOption<T> name); 获取 Socket 选项
     * supportedOptions(); 获取支持的选项
     * <p>
     * ServerSocketChannel 抽象类
     * 服务端的 Socket 通道, 用于表示一个服务端的 socket 连接
     * 包含方法
     * open(); 新建一个通道
     * bind(); 绑定本地地址
     * getLocalAddress(); 获取本地地址
     * validOps(); 返回可操作的事件集合
     * setOption(); 设置 Socket 选项
     * socket(); 返回与此关联的 ServerSocket
     * accept(); 返回此通道上的连接事件, ServerSocketChannel 上的 SocketChannel
     * <p>
     * SocketChannel 抽象类
     * 客户端 Socket 通道, 表示一个客户端的 Socket 连接
     * 包含方法
     * open(); 新建一个通道
     * bind(); 绑定本地地址
     * setOption(); 设置 socket 选项
     * shutdownInput(); 关闭输入流, 但是不关闭连接
     * shutdownOutput(); 关闭输出流, 但是不关闭连接
     * socket(); 返回与此关联的 SocketChannel
     * isConnected(); 返回是否已连接
     * isConnectionPending(); 连接是否正在进行
     * connect(); 开始连接
     * finishConnect();
     * getRemoteAddress(); 获取远程地址
     * getLocalAddress(); 获取本地地址
     * read(); 从通道中读取数据
     * write(); 将数据写入到通道中
     */
    @Test

    public void test_02_structure02() {

    }

}
