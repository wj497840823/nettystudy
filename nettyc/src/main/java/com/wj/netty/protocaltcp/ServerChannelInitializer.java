package com.wj.netty.protocaltcp;


import io.netty.channel.ChannelInitializer;

import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


/**
 * @author wangjie
 * @create 2020-03-25 9:55
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //增加自定义解码器
        pipeline.addLast( new MyMessageDecoder());
        //增加自定义编码器
        pipeline.addLast(new MyMessageEncoder());
        //增加自定义的handler
        pipeline.addLast(new ServerHandler());

    }
}
