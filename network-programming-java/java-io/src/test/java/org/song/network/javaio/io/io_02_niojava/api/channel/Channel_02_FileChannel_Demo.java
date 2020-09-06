package org.song.network.javaio.io.io_02_niojava.api.channel;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributeView;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class Channel_02_FileChannel_Demo {

    /********************Files*************************/

    /**
     * Files 是 java.nio包下的文件工具类
     */
    @Test
    public void test_01_Files() throws IOException {
        // 创建目录
        Files.createDirectory(Paths.get("D:\\临时\\temp\\createone"));
        // 创建多层级目录
        Files.createDirectories(Paths.get("D:\\临时\\temp\\createtwo\\son"));
        // 创建文件
        // 必须先有目录，才能在目录中创建文件。
        Files.createFile(Paths.get("D:\\临时\\temp\\mappedByteBuffer_OS.txt"));
    }

    /**
     * 文件复制:
     * <p>
     * 从文件复制到文件：
     * Files.copy(Path source, Path target, CopyOption options);
     * <p>
     * 从输入流复制到文件：
     * Files.copy(InputStream in, Path target, CopyOption options);
     * <p>
     * 从文件复制到输出流：
     * Files.copy(Path source, OutputStream out);
     */
    @Test
    public void test_01_Files_copy() throws IOException {

        // 文件 复制到 输出流
        Files.copy(Paths.get("D:\\临时\\temp\\text.txt"),
                System.out);

        // 文件 复制到 文件 存在则替换
        Files.copy(Paths.get("D:\\临时\\temp\\text.txt"),
                Paths.get("D:\\临时\\temp\\text.txt.copy"),
                StandardCopyOption.REPLACE_EXISTING);

        System.out.println("aaabbb");
        // 输入流 复制到 文件 存在则替换
        Files.copy(System.in,
                Paths.get("D:\\临时\\temp\\text.txt.System.in"),
                StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * 遍历 单个目录
     */
    @Test
    public void test_01_Files_dir() {
        Path dir = Paths.get("D:\\临时");

        // 方式1
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path e : stream) {
                System.out.println(e.getFileName());
            }
        } catch (IOException e) {

        }

        // 方式2
        try (Stream<Path> stream = Files.list(dir)) {
            Iterator<Path> ite = stream.iterator();
            while (ite.hasNext()) {
                Path pp = ite.next();
                System.out.println(pp.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Files 是JDK8 自带的文件工具, 功能十分强大
     */

    /**
     * 递归目录
     */
    @Test
    public void files_01_Files_recursion() throws IOException {
        Path startingDir = Paths.get("D:\\临时");
        List<Path> result = new LinkedList<Path>();
        Path path = Files.walkFileTree(startingDir, new FindJavaVisitor(result));
        System.out.println(result);
    }

    /**
     * 遍历整个文件目录
     *
     * @throws IOException
     */
    @Test
    public void test_01_Files_AllDir() throws IOException {
        Path startingDir = Paths.get("D:\\workspace\\project\\idea-git\\song\\labortory\\javase\\src\\main\\java\\io");
        List<Path> result = new LinkedList<Path>();
        Files.walkFileTree(startingDir, new FindJavaVisitor(result));
        System.out.println("result.size()=" + result.size());
    }

    /******************Path***********************/


    private static class FindJavaVisitor extends SimpleFileVisitor<Path> {
        private List<Path> result;

        public FindJavaVisitor(List<Path> result) {
            this.result = result;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            if (file.toString().endsWith(".java")) {
                result.add(file.getFileName());
            }
            return FileVisitResult.CONTINUE;
        }
    }

    /**
     * path 文件写入
     */
    @Test
    public void test_01_Paths_write() {
        try {
            BufferedWriter writer = Files.newBufferedWriter(
                    Paths.get("D:\\临时\\temp\\text.txt"), StandardCharsets.UTF_8);
            writer.write("测试文件写操作");
            writer.flush();
            writer.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * path文件读取
     */
    @Test
    public void test_01_Files_read() {
        try {
            // path 文件读取
            BufferedReader reader = Files.newBufferedReader(
                    Paths.get("D:\\临时\\temp\\text.txt"), StandardCharsets.UTF_8);
            String str = null;
            while ((str = reader.readLine()) != null) {
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Path 获取一个文件
     * Path 就是取代File的
     */
    @Test
    public void test_02_Path() throws IOException {
        // Path 通过静态方法获取文件
        Path path = Paths.get("D:\\临时\\temp\\text.txt");
//        Path path2 = Paths.get("D:\\", "临时\\temp\\text.txt");

        System.out.println(path);
        System.out.println(path.getRoot());
        System.out.println(path.getFileName());
        System.out.println(path.getNameCount());


        // file 转 path
        File file = new File("D:\\临时\\temp\\text.txt");
        Path path2 = file.toPath();

        // path 不存在 则创建
        if (!Files.exists(path2)) {
            Files.createFile(path2);
        }

    }

    /**
     * file 简单基本用法
     */
    @Test
    public void test1() {
        // 获取/创建 一个文件
        File file = new File("D:\\临时\\temp\\text.txt");
        System.out.println(file.exists());
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getName());
        System.out.println(file.getPath());
    }


    //自动资源管理：自动关闭实现 AutoCloseable 接口的资源
    @Test
    public void test8() {
        try (FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
             FileChannel outChannel = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {

            ByteBuffer buf = ByteBuffer.allocate(1024);
            inChannel.read(buf);

        } catch (IOException e) {

        }
    }

    /*
        Files常用方法：用于操作内容
            SeekableByteChannel newByteChannel(Path path, OpenOption…how) : 获取与指定文件的连接，how 指定打开方式。
            DirectoryStream newDirectoryStream(Path path) : 打开 path 指定的目录
            InputStream newInputStream(Path path, OpenOption…how):获取 InputStream 对象
            OutputStream newOutputStream(Path path, OpenOption…how) : 获取 OutputStream 对象
     */
    @Test
    public void test7() throws IOException {
        SeekableByteChannel newByteChannel = Files.newByteChannel(Paths.get("1.jpg"), StandardOpenOption.READ);

        DirectoryStream<Path> newDirectoryStream = Files.newDirectoryStream(Paths.get("e:/"));

        for (Path path : newDirectoryStream) {
            System.out.println(path);
        }
    }

    /*
        Files常用方法：用于判断
            boolean exists(Path path, LinkOption … opts) : 判断文件是否存在
            boolean isDirectory(Path path, LinkOption … opts) : 判断是否是目录
            boolean isExecutable(Path path) : 判断是否是可执行文件
            boolean isHidden(Path path) : 判断是否是隐藏文件
            boolean isReadable(Path path) : 判断文件是否可读
            boolean isWritable(Path path) : 判断文件是否可写
            boolean notExists(Path path, LinkOption … opts) : 判断文件是否不存在
            public static <A extends BasicFileAttributes> A readAttributes(Path path,Class<A> type,LinkOption... options) : 获取与 path 指定的文件相关联的属性。
     */
    @Test
    public void test6_() throws IOException {
        Path path = Paths.get("e:/nio/hello7.txt");
//		System.out.println(Files.exists(path, LinkOption.NOFOLLOW_LINKS));

        BasicFileAttributes readAttributes = Files.readAttributes(path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
        System.out.println(readAttributes.creationTime());
        System.out.println(readAttributes.lastModifiedTime());

        DosFileAttributeView fileAttributeView = Files.getFileAttributeView(path, DosFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);

        fileAttributeView.setHidden(false);
    }

    /*
        Files常用方法：
            Path copy(Path src, Path dest, CopyOption … how) : 文件的复制
            Path createDirectory(Path path, FileAttribute<?> … attr) : 创建一个目录
            Path createFile(Path path, FileAttribute<?> … arr) : 创建一个文件
            void delete(Path path) : 删除一个文件
            Path move(Path src, Path dest, CopyOption…how) : 将 src 移动到 dest 位置
            long size(Path path) : 返回 path 指定文件的大小
     */
    @Test
    public void test5_() throws IOException {
        Path path1 = Paths.get("e:/nio/hello2.txt");
        Path path2 = Paths.get("e:/nio/hello7.txt");

        System.out.println(Files.size(path2));

//		Files.move(path1, path2, StandardCopyOption.ATOMIC_MOVE);
    }

    @Test
    public void test4_() throws IOException {
        Path dir = Paths.get("e:/nio/nio2");
//		Files.createDirectory(dir);

        Path file = Paths.get("e:/nio/nio2/hello3.txt");
//		Files.createFile(file);

        Files.deleteIfExists(file);
    }

    @Test
    public void test3_() throws IOException {
        Path path1 = Paths.get("e:/nio/hello.txt");
        Path path2 = Paths.get("e:/nio/hello2.txt");

        Files.copy(path1, path2, StandardCopyOption.REPLACE_EXISTING);
    }

    /*
        Paths 提供的 get() 方法用来获取 Path 对象：
            Path get(String first, String … more) : 用于将多个字符串串连成路径。
        Path 常用方法：
            boolean endsWith(String path) : 判断是否以 path 路径结束
            boolean startsWith(String path) : 判断是否以 path 路径开始
            boolean isAbsolute() : 判断是否是绝对路径
            Path getFileName() : 返回与调用 Path 对象关联的文件名
            Path getName(int idx) : 返回的指定索引位置 idx 的路径名称
            int getNameCount() : 返回Path 根目录后面元素的数量
            Path getParent() ：返回Path对象包含整个路径，不包含 Path 对象指定的文件路径
            Path getRoot() ：返回调用 Path 对象的根路径
            Path resolve(Path p) :将相对路径解析为绝对路径
            Path toAbsolutePath() : 作为绝对路径返回调用 Path 对象
            String toString() ： 返回调用 Path 对象的字符串表示形式
     */
    @Test
    public void test2_() {
        Path path = Paths.get("e:/nio/hello.txt");

        System.out.println(path.getParent());
        System.out.println(path.getRoot());

//		Path newPath = path.resolve("e:/hello.txt");
//		System.out.println(newPath);

        Path path2 = Paths.get("1.jpg");
        Path newPath = path2.toAbsolutePath();
        System.out.println(newPath);

        System.out.println(path.toString());
    }

    @Test
    public void test1_() {
        Path path = Paths.get("e:/", "nio/hello.txt");

        System.out.println(path.endsWith("hello.txt"));
        System.out.println(path.startsWith("e:/"));

        System.out.println(path.isAbsolute());
        System.out.println(path.getFileName());

        for (int i = 0; i < path.getNameCount(); i++) {
            System.out.println(path.getName(i));
        }
    }
}
