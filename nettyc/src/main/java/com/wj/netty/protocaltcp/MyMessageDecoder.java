package com.wj.netty.protocaltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 自定义解码器
 * @author wangjie
 * @create 2020-03-25 10:05
 */
public class MyMessageDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("MyMessageDecoder方法被调用");

        int length = byteBuf.readInt();
        byte[] content = new byte[length];

        byteBuf.readBytes(content);

        MessageProtocol messageProtocol = new MessageProtocol();

        messageProtocol.setLen(length);
        messageProtocol.setContent(content);


        list.add(messageProtocol);

    }
}
