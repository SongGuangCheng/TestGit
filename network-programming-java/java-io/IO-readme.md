# IO

## java中的IO

### 流

| 字节流 | 字符流
-|-|-
| InputStream 输入流 | Reader 读取
| OutputStream 输出流 | Writer 写入

**流的分类**

- 节点流: 特定地方读取的流
- 过滤流: 使用节点流作为输入或者输出, 必须依附已有的节点流才能使用


#### 输入流

**读取数据的逻辑**

```
1. 打开一个流 open a stream
2. 循环获取信息 while more information
3. 读取数据 read information
4. 关闭流 close the stream
```

#### 输出流

**写数据逻辑**

```
1. 打开一个流 open a stream
2. 循环获取信息 while more information
3. 读取写入 write information
4. 关闭流 close the stream
```

### 示例

**输入流/流读取**
文件 -> FileInputStream(节点流:读取文件原始数据) -> BufferedInputStream(过滤流: 增加缓冲区功能) -> DataInputStream(节点流: 增加读取java基本数据功能) -> 数据

**输出流/流写入**
数据 -> DataOutputStream(过滤流:可以写入java基本类型) -> BufferedOutputStream(过滤流: 增加缓冲区功能) -> FileOutputStream(节点流:写入数据到原始文件) -> 文件

```
java中使用装饰器设计模式, 进行节点流和过滤流的使用
```


