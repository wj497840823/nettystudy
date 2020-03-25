package com.wj.netty.protocaltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * @author wangjie
 * @create 2020-03-25 10:05
 */
public class ServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    //读取数据
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol messageProtocol) throws Exception {
        int len = messageProtocol.getLen();

        byte[] content = messageProtocol.getContent();

        System.out.println("读取到的数据长度"+len);

        System.out.println("读取到的内容:"+new String(content, Charset.forName("utf-8")));

        byte[]  responseContent = UUID.randomUUID().toString().getBytes("utf-8");
        int responseContentlen = responseContent.length;
        MessageProtocol response = new MessageProtocol();
        response.setLen(responseContentlen);
        response.setContent(responseContent);

        channelHandlerContext.writeAndFlush(response);



    }
}
