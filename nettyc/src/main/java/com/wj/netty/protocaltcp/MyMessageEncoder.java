package com.wj.netty.protocaltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author wangjie
 * @create 2020-03-25 10:05
 */
public class MyMessageEncoder extends MessageToByteEncoder<MessageProtocol>{
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MessageProtocol msg, ByteBuf out) throws Exception {
        System.out.println("调用MyMessageEncoder的encode方法");

        out.writeInt(msg.getLen());

        out.writeBytes(msg.getContent());
    }
}
