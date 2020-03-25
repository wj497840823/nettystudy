package com.wj.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author wangjie
 * @create 2020-03-23 18:32
 */
public class SimpleNettyClient {

    public static void main(String[] args) throws Exception {
        //客户端需要一个事件循环组
        NioEventLoopGroup clientGruop = new NioEventLoopGroup();


        try {
            //创建客户端启动对象
            //注意客户端使用的不是 ServerBootstrap 而是 Bootstrap
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(clientGruop)//设置线程组
                    .channel(NioSocketChannel.class)//设置客户端通道的实现类
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyClientHandler());//加入自己的处理器
                        }
                    });
            System.out.println("客户端 ok..");
            ChannelFuture connect = bootstrap.connect("127.0.0.1", 6666);

            connect.channel().closeFuture().sync();
        } finally {
            clientGruop.shutdownGracefully();
        }

    }
}
