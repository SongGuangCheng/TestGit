# protobuf

## 入门

[protocol buffer for java 官网入门](https://developers.google.com/protocol-buffers/docs/javatutorial)

## demo

### hallo proto

- 下载软件 
    [github地址](https://github.com/protocolbuffers/protobuf/releases)
    找到对应版本, 并找到文件(eg: Windows的 protoc-3.12.4-win64.zip)
- 使用软件生成java类, 
    - 方式1. protoc命令
        1. 解压并找到bin/protoc.exe(protobuf编译器, 用于将proto文件转换成对应类), 可以设置到环境变量中
        2. 使用 protoc -h 或者 ./protoc.exe -h 查看帮助
        3. 执行命令 protoc java_out=类输出目录 proto文件, 即可根据proto文件生成java类
    - 方式2. 下载并使用idea快捷插件
        1. 解压并找到bin/protoc.exe
        2. 下载插件 
            1. protocol buffer editor((protobuf编译器, 用于将proto文件转换成对应类))
            2. genprotobuf(用于将.proto文件生成对应java类, 等同与使用命令)
                1. 基本配置: tools->configuration genprotobuf
                2. 一键生成所有: tools->generater all protobuffers
### maven依赖

```
核心依赖, 
<dependency>
    <groupId>com.google.protobuf</groupId>
    <artifactId>protobuf-java</artifactId>
    <version>3.12.4</version>
</dependency>


```

## proto文件

**官网示例**

名称以.proto结尾

```
syntax = "proto2"; // 协议, 目前最新的是proto3

package tutorial; // 通用包名(跨语言)

option java_package = "com.example.tutorial"; // java包名(java专用)
option java_outer_classname = "AddressBookProtos"; // java输出类名(并不一定是javabean, javabean会以内部类的方式提供)

message Person {
  required string name = 1;
  required int32 id = 2;
  optional string email = 3;

  enum PhoneType {
    MOBILE = 0;
    HOME = 1;
    WORK = 2;
  }

  message PhoneNumber {
    required string number = 1;
    optional PhoneType type = 2 [default = HOME];
  }

  repeated PhoneNumber phones = 4;
}

message AddressBook {
  repeated Person people = 1;
}
```

## 使用protobuf存在的问题(netty)

1. 由于protobuf序列化需要使用并指定建造者javabean, 所以显示的指定该javabean作为传输对象, 需要一个handler对应一个javabean, 极为不灵活.
方式1: 使用多个handler
方式2: 自定义proto文件中包含多个message(对象), 使用类型区分每次传输的具体对象是哪个一个

2. 使用protobuf必要文件.proto文件, 然后在个子项目中(可以不同语言)使用该文件生成Javabean, 然后使用Javabean进行编码, 
所以.proto文件是需要项目共享的, 如果是同一个语言的server和client端, 那么Javabean也需要做到共享, 如何做到项目共享此文件?
方式1: 使用 git submodule, 多个git项目拥有同一个git子项目, git submodule 相当于git仓库的一个子git仓库, 缺点, 外层git切换分支, git submodule是不会切换的, 需要手动切换
方式2: 使用 git subtree, 和git submodule类似, 不过更加推荐

