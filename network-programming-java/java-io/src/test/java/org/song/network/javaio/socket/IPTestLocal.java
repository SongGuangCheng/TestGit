package org.song.network.javaio.socket;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class IPTestLocal {

    /**
     * InetAddress 代表主机的IP地址
     *
     * @throws IOException
     */
    @Test
    public void test01() throws IOException {
        // 入参是IP或者主机名
        // 工厂方法返回自身示例
        InetAddress localHost = InetAddress.getByName("www.baidu.com");
        // IP
        System.out.println(localHost.getHostAddress());
        // 域名
        System.out.println(localHost.getHostName());
        // getHostname()会先查找DNS缓存，减少查找DNS服务器的概率，这样做能提高查找性能。因为查找DNS服务器是很耗时的操作。而getCanonicalHostName()总是查找DNS服务器，确保获得当前最新版本的主机名。
        System.out.println(localHost.getCanonicalHostName());
        // 是否能从本地连接到指定主机, 超时时间 毫秒
        System.out.println(localHost.isReachable(1000));


        // 获取本地 CYY50515
        System.out.println(InetAddress.getLocalHost().getHostName());
    }

    /**
     * NetworkInterface 表示物理上的网络接口
     *
     * @throws SocketException
     */
    @Test
    public void test02() throws SocketException {
        // 返回指定网络接口名字
        NetworkInterface networkInterface = NetworkInterface.getByName("eth0");
        //
        System.out.println(networkInterface.getName());

        // 返回本机所有网络接口
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            System.out.println(networkInterfaces.nextElement().getDisplayName());
        }
    }
}
