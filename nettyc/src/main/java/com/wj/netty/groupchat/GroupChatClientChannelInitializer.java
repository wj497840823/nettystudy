package com.wj.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author wangjie
 * @create 2020-03-24 13:41
 */
public class GroupChatClientChannelInitializer extends ChannelInitializer {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        pipeline.addLast("decode",new StringDecoder());

        pipeline.addLast("encode",new StringEncoder());

        pipeline.addLast(new GroupChatClientHandler());

    }
}
