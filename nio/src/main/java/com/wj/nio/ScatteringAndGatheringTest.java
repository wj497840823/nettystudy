package com.wj.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * scattering:将数据写入buffer时，可以采用buffer数组，依次写入
 * gathering：从buffer读取数据时，可以采用buffer数组，依次读
 * @author wangjie
 * @create 2020-03-22 12:48
 */
public class ScatteringAndGatheringTest {

    public static void main(String[] args) throws Exception {
        //使用serversocketchannel和socketchannel

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //绑定端口并启动
        serverSocketChannel.socket().bind(new InetSocketAddress(7000));

        //创建buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0]=ByteBuffer.allocate(5);
        byteBuffers[1]= ByteBuffer.allocate(3);

        //等待客户端连接
        System.out.println("等待客户端连接");
        SocketChannel accept = serverSocketChannel.accept();
        System.out.println("连接一个客户端");

        //假定从客户端接收8个字节
        int messageength=8;
        while(true){
            int byteRead =0;
            while (byteRead<messageength){
                long read = accept.read(byteBuffers);
                byteRead+=read;
                System.out.println("byteRead:"+byteRead);
                //使用流打印，看看当前的这个position和limit
                Arrays.asList(byteBuffers).stream().
                        map(buffer->"position:"+buffer.position()+";limit:"+buffer.limit())
                        .forEach(System.out::println);

                //将所有的buffer进行flip
                Arrays.asList(byteBuffers).stream().forEach(buffer->buffer.flip());

                //将数据读出显示到客户端
                long byteWrite = 0;
                while (byteWrite<byteRead){
                    long write = accept.write(byteBuffers);
                    byteWrite+=write;

                }
                //将所有的buffer进行clear
                Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.clear());

                System.out.println("byteRead:"+byteRead+";byteWrite:"+byteWrite);


            }

        }

    }
}
