package org.song.network.javaio.io.io_02_niojava.api.selector;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.channels.spi.SelectorProvider;

public class Selector_01_structure_01_Selector {

    /**
     * Selector 抽象类
     * -    - AbstractSelector 抽象类
     * -    -   - SelectorImpl 抽象类 (未开源)
     * -    -   -   - WindowsSelectorImpl 类 (未开源 window JDK)
     * <p>
     * SelectorProvider 抽象类
     * -    - SelectorProviderImpl 类(未开源)
     * -    -   - WindowsSelectorProvider windows 系统下的实现 类(未开源)
     */
    @Test
    public void test_01_base() {

    }

    /**
     * Selector
     * <p>
     * 定义了一些方法, 需要有具体的实现类实现
     * <p>
     * 方法
     * Selector.open();         打开一个 Selector, 静态方法, 根据系统
     * selector.isOpen();       判断选择器是否开启
     * selector.provider();     返回选择器提供者 SelectorProvider
     * <p>
     * selector.keys();         返回可以处理的事件集
     * selector.selectedKeys(); 返回以处理完的事件集, 需要删除
     * <p>
     * selector.select();       开始选择 阻塞
     * selector.select(long);   开始选择 阻塞超时
     * selector.selectNow();    开始选择 非阻塞
     * <p>
     * selector.wakeup();       唤醒阻塞的线程
     * selector.close();        关闭选择器
     */
    @Test
    public void test_02_Selector() {

    }

    /**
     * SelectorProvider 抽象类, 为 selector 和 通道等提供服务的类, 主要是 open 服务
     * <p>
     * SelectorProvider 的 这些 open 方法, 会根据不同平台有不同的实现类, 实现类负责和平台的底层交互
     * 他们的实现逻辑是, 不同的类的 open() 方法, 会打开不同平台的 provider 的指定 open 方法
     * 示例:
     * Selector.open()              最终会调用 SelectorProvider.openSelector()
     * DatagramChannel.open()       最终会调用 SelectorProvider.openDatagramChannel()
     * ServerSocketChannel.open()   最终会调用 SelectorProvider.openServerSocketChannel()
     * 以此类推, 其他的包括
     * DatagramChannel.open()
     * Pipe.open()
     * Selector.open()
     * ServerSocketChannel.open()
     * SocketChannel.open()
     * 和 System.inheritedChannel()
     * <p>
     * 什么时候给 SelectorProvider 实例化呢?
     * 当调用 SelectorProvider.provider() 的时候, 会以加锁单例的方式提供, 具体提供者还是不同的平台实现
     */
    @Test
    public void test_02_SelectorProvider() throws IOException {
        SelectorProvider selectorProvider = SelectorProvider.provider();
        // window 下是 WindowsSelectorProvider
        System.out.println(selectorProvider.getClass());

        selectorProvider.openSelector();            // Selector.open(); 的底层实现
        selectorProvider.openServerSocketChannel(); // ServerSocketChannel.open(); 的底层实现
        selectorProvider.openSocketChannel();       // SocketChannel.open(); 的底层实现
        selectorProvider.openPipe();                // Pipe.open(); 的底层实现
        selectorProvider.openDatagramChannel();     // DatagramChannel.open(); 的底层实现
    }

    /**
     * 底层调用总结
     * 1. 各个XXX组件的 open() 方法, 最终调用的是 SelectorProvider.openXXX() 子类具体平台实现
     * 2. AbstractSelectableChannel 及其子类的 注册方法 register(), 最终调用的是 AbstractSelector.register() 的子类具体平台实现
     */
    @Test
    public void test_02_summary() {

    }
}
