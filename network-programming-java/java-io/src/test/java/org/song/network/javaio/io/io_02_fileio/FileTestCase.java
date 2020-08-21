package org.song.network.javaio.io.io_02_fileio;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class FileTestCase {

    /********************Files*************************/
    /**
     * Files 是JDK8 自带的文件工具, 功能十分强大
     */

    /**
     * 递归目录 如何展示?
     */
    @Test
    public void files4() throws IOException {
        Path startingDir = Paths.get("D:\\临时");
        List<Path> result = new LinkedList<Path>();
        Path path = Files.walkFileTree(startingDir, new FindJavaVisitor(result));
        System.out.println(result);
    }

    /**
     * 遍历 单个目录
     */
    @Test
    public void files3() {
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
    public void files2() throws IOException {

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
     * Files 是 java.nio包下的文件工具类
     */
    @Test
    public void files1() throws IOException {
        // 创建目录
        Files.createDirectory(Paths.get("D:\\临时\\temp\\createone"));
        // 创建多层级目录
        Files.createDirectories(Paths.get("D:\\临时\\temp\\createtwo\\son"));
        // 创建文件
        // 必须先有目录，才能在目录中创建文件。
        Files.createFile(Paths.get("D:\\临时\\temp\\mappedByteBuffer_OS.txt"));
    }

    /******************Path***********************/

    /**
     * 遍历整个文件目录
     *
     * @throws IOException
     */
    @Test
    public void test6() throws IOException {
        Path startingDir = Paths.get("D:\\workspace\\project\\idea-git\\song\\labortory\\javase\\src\\main\\java\\io");
        List<Path> result = new LinkedList<Path>();
        Files.walkFileTree(startingDir, new FindJavaVisitor(result));
        System.out.println("result.size()=" + result.size());
    }


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
    public void test5() {
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
    public void test4() {
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
     * 文件创建 转换读取方式
     *
     * @throws IOException
     */
    @Test
    public void test3() throws IOException {

        // file 转 path
        File file = new File("D:\\临时\\temp\\text.txt");
        Path path = file.toPath();

        // path 不存在 则创建
        if (!Files.exists(path)) {
            Files.createFile(path);
        }

    }

    /**
     * Path 获取一个文件
     * Path 就是取代File的
     */
    @Test
    public void test2() {
        // Path 通过静态方法获取文件
        Path path = Paths.get("D:\\临时\\temp\\text.txt");
//        Path path2 = Paths.get("D:\\", "临时\\temp\\text.txt");

        System.out.println(path);
        System.out.println(path.getRoot());
        System.out.println(path.getFileName());
        System.out.println(path.getNameCount());

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
}
