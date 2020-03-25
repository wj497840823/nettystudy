package com.wj.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * @author wangjie
 * @create 2020-03-24 10:13
 */
public class MyHttpServerHander extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject msg) throws Exception {
        System.out.println("对应的channel=" + channelHandlerContext.channel() + " pipeline=" + channelHandlerContext
                .pipeline() + " 通过pipeline获取channel" + channelHandlerContext.pipeline().channel());
        System.out.println("当前ctx的handler=" + channelHandlerContext.handler());
        if(msg instanceof HttpRequest){
            System.out.println("channelHandlerContext类型"+channelHandlerContext.getClass());

            System.out.println("msg 类型=" + msg.getClass());
            System.out.println("客户端地址" + channelHandlerContext.channel().remoteAddress());
            HttpRequest httpRequest = (HttpRequest)msg;
            //获取uri, 过滤指定的资源
            URI uri = new URI(httpRequest.uri());

            if("/favicon.ico".equals(uri.getPath())) {
                System.out.println("请求了 favicon.ico, 不做响应");
                return;
            }
            //回复浏览器信息

            ByteBuf content = Unpooled.copiedBuffer("hello,http", CharsetUtil.UTF_8);

            DefaultHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,content);

            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            channelHandlerContext.writeAndFlush(response);
        }


    }
}
