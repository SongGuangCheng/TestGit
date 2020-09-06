package org.song.network.javaio.io.io_02_niojava.api.selector;

import org.junit.jupiter.api.Test;

public class Selector_02_SelectKey {

    /**
     * SelectionKey 抽象类
     * -    - AbstractSelectionKey 抽象类, 提供简单的基本实现
     * -    -   - SelectionKeyImpl 具体实现类, 未开源,
     * <p>
     * SelectionKeyImpl
     * 包含一个 Selector (SelectorImpl) 引用
     */
    @Test
    public void test_01_base() {

    }

    /**
     * SelectionKey 定义了主要的方法和常量
     * 1. 通道和选择器 相关操作
     * channel(); 获取事件对应的通道
     * selector(); 获取当前选择器
     * isValid(); key 是否有效
     * cancel(); 取消 key
     *
     * 2. 操作集相关(事件类型集/具体的 key set 类型)
     * 2.1 操作集访问
     * interestOps(); 返回当前key感兴趣操作类型集
     * interestOps(int ops);
     * readyOps(); 返回就绪集
     * 2.2 事件类型常量的定义和获取结果
     * OP_READ; 读操作事件
     * OP_WRITE; 写操作事件
     * OP_CONNECT; 客户端连接事件
     * OP_ACCEPT; 服务端接收连接事件
     * isReadable(); 通道是否可读
     * isWritable(); 通道是否可写
     * isConnectable(); 是否客户端连接完成
     * isAcceptable(); 服务端是否准备好接收新连接
     *
     * 3. 附件相关
     * attach(); 添加附件
     * attachment(); 获取附件
     */
    @Test
    public void test_02_SelectionKey() {

    }
}
