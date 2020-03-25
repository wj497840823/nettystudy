package com.wj.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author wangjie
 * @create 2020-03-24 11:16
 */
public class GroupChatServerChannelInitializer extends ChannelInitializer {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        //增加解码器
        pipeline.addLast("decode",new StringDecoder());
        //增加编码器
        pipeline.addLast("encode",new StringEncoder());

        //增加心跳检测
        pipeline.addLast(new IdleStateHandler(3,4,5, TimeUnit.SECONDS));

        pipeline.addLast(new GroupChatServerHandler());

    }
}
