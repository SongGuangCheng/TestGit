package org.song.network.javaio.io.bio;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.song.network.javaio.utils.ExecutorUtils;
import org.song.network.javaio.utils.IOUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOTestCase_03_ThreadPool {

    private static Logger logger = LoggerFactory.getLogger(BIOTestCase_03_ThreadPool.class);

    final static String ADDRESS = "127.0.0.1";
    final static int PORT = 8765;

    @Test
    public void client() {

        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            // java中 客户端使用 socket 对象
            socket = new Socket(ADDRESS, PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            //向服务器端发送数据
            out.println("接收到客户端的请求数据...");
            String response = in.readLine();
            System.out.println("Client: " + response);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(in, out, socket);
        }
    }


    @Test
    public void server() {

        ServerSocket serverSocket = null;
        try {
            // java中 服务器端使用ServerSocket对象
            serverSocket = new ServerSocket(PORT);
            System.out.println(" server start .. ");

            while (true) {
                Socket socket = serverSocket.accept();
                // 使用自定义线程池 处理每一个链接
                ExecutorUtils.executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        BufferedReader in = null;
                        PrintWriter out = null;
                        try {
                            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            out = new PrintWriter(socket.getOutputStream(), true);
                            String body = null;
                            while (true) {
                                body = in.readLine();
                                if (body == null) break;
                                System.out.println("Server :" + body);
                                out.println("服务器端回送响的应数据.");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            IOUtils.close(in, out, socket);
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(serverSocket);
        }
    }
}
