package com.wj.netty.util;

import com.wj.netty.simple.NettyServerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

/**
 * @author wangjie
 * @create 2020-03-24 11:04
 */
public class BootStrapUtil {

    public static void serverRunSimple(ChannelInitializer channelInitializer,int port) throws InterruptedException {
        NioEventLoopGroup bossGruop = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //创建服务器端的启动对象，配置参数
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            //使用链式编程来进行设置
            serverBootstrap
                    .group(bossGruop,workerGroup)//设置两个线程
                    .channel(NioServerSocketChannel.class)//使用NioSocketChannel 作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG,128)//设置线程队列得到连接数
                    .childOption(ChannelOption.SO_KEEPALIVE,true)//设置保持活动连接状态
                    //.handler(null) // 该 handler对应 bossGroup , childHandler 对应 workerGroup
                    .childHandler(channelInitializer);
            System.out.println(".....服务器 is ready...");

            //绑定了一个端口并且生成了一个ChannelFuture对象
            //sync:等待异步操作执行完毕
            ChannelFuture future = serverBootstrap.bind(port).sync();


            //给channelFuture 注册监听器，监控我们关心的事件

            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("监听端口 "+port+"成功");
                    } else {
                        System.out.println("监听端口 "+port+" 失败");
                    }
                }
            });
            //对关闭通道进行监听
            future.channel().closeFuture().sync();
        } finally {
            bossGruop.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void clientSimpleRun(ChannelInitializer channelInitializer,String host,int port) throws InterruptedException {

        NioEventLoopGroup clientGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(clientGroup)
                    .channel(NioSocketChannel.class)
                    .handler(channelInitializer);

            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();



//            channelFuture.channel().closeFuture().sync();

            Channel channel = channelFuture.channel();
            System.out.println("-------" + channel.localAddress()+ "--------");

            channel.closeFuture().sync();
        } finally {
            clientGroup.shutdownGracefully();
        }


    }

    public static void clientSimpleRunWithScanner(ChannelInitializer channelInitializer,String host,int port) throws InterruptedException {

        NioEventLoopGroup clientGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(clientGroup)
                    .channel(NioSocketChannel.class)
                    .handler(channelInitializer);

            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();



//            channelFuture.channel().closeFuture().sync();

            Channel channel = channelFuture.channel();
            System.out.println("-------" + channel.localAddress()+ "--------");
            //客户端需要输入信息，创建一个扫描器
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String msg = scanner.nextLine();
                //通过channel 发送到服务器端
                channel.writeAndFlush(msg + "\r\n");
            }
        } finally {
            clientGroup.shutdownGracefully();
        }


    }
}
