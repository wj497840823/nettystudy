package com.wj.netty.rpc.netty;



import com.wj.netty.simple.NettyClientHandler;
import com.wj.netty.util.BootStrapUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wangjie
 * @create 2020-03-25 14:47
 */
public class Client {
    //创建线程池

    private static ExecutorService executorService = Executors.newFixedThreadPool(5);

    private static RpcClientHandler client;
    private int count = 0;

    //创建代理对象(在代理对象中启动netty客户端)

    public Object getBean(final Class<?> serviceClass,final String providerName){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),new Class<?>[]{serviceClass},
                (proxy,method,args)->{
            System.out.println("(proxy, method, args) 进入...." + (++count) + " 次");

            //如果client为null,初始化链接
            if(client==null){
                initClient();
            }
            System.out.println("client初始化完成");
            //设置参数
            client.setPara(providerName+args[0]);
            //设置参数
            return executorService.submit(client).get();
        });
    }

    private void initClient() {
        client = new RpcClientHandler();
        //创建EventLoopGroup
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(
                        new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ChannelPipeline pipeline = ch.pipeline();
                                pipeline.addLast(new StringDecoder());
                                pipeline.addLast(new StringEncoder());
                                pipeline.addLast(client);
                            }
                        }
                );

        try {
            bootstrap.connect("127.0.0.1", 9000).sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
