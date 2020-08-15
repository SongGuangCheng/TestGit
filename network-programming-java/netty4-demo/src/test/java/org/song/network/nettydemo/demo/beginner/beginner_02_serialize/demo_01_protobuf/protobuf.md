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
        1. 解压并找到bin/protoc.exe, 可以设置到环境变量中
        2. 使用 protoc -h 或者 ./protoc.exe -h 查看帮助
        3. 执行命令 protoc java_out=类输出目录 proto文件, 即可根据proto文件生成java类
    - 方式2. 下载并使用idea快捷插件
        1. 解压并找到bin/protoc.exe
        2. 下载插件 
            1. protocol buffer editor(可用于.proto文件的编辑, 支持其特有语法)
            2. genprotobuf(用于将.proto文件生成对应java类, 等同与使用命令)
                1. 基本配置: tools->configuration genprotobuf
                2. 一键生成所有: tools->generater all protobuffers
        
    