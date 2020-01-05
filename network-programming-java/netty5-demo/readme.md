# netty5 


ByteBuf 
```
是 Java NIO 中 ByteBuffer 的拓展和重写

NIO 存在的缺点
1. 长度固定, 不能自动扩展和收缩
2. 读写的时候需要手动 flip() 和 rewind()等, 操作复杂
3. 功能有限

ByteBuf 中

1. 动态扩容
2. 单独维护读写索引, 操作方便直观
3. 其他高级API功能



```