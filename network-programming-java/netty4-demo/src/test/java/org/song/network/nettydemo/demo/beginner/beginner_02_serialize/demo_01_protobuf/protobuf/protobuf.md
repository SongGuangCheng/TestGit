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
