package com.wj.netty.rpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.Callable;

/**
 * @author wangjie
 * @create 2020-03-25 15:10
 */
public class RpcClientHandler extends SimpleChannelInboundHandler<String> implements Callable<String> {

    private ChannelHandlerContext context;//上下文
    private String result; //返回的结果
    private String para; //客户端调用方法时，传入的参数

    @Override
    public synchronized String call() throws Exception {

        System.out.println("call被调用开始");
        //将请求信息发送给服务端
        context.writeAndFlush(para);
        wait();
        System.out.println("call被调用结束");
        return result;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("远程调用连接成功");
        context = ctx;
    }

    @Override
    protected synchronized void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("获取远程调用的返回结果:"+s);

        result = s;
        notify();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    void setPara(String para) {
        System.out.println(" setPara  ");
        this.para = para;
    }
}
