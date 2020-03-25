package com.wj.netty.rpc.netty;

import com.wj.netty.rpc.costumer.HelloRemote;
import com.wj.netty.rpc.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author wangjie
 * @create 2020-03-25 14:06
 */
public class RPCServerHandler extends SimpleChannelInboundHandler<String>{

    /**
     * 读取从客户端请求的数据
     * 符合要求返回方法调用结果
     * @param channelHandlerContext
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        //获取s的信息,找到对应的/

        System.out.println("获取的数据信息为:"+msg);

        if(msg!=null&&msg.startsWith(HelloRemote.providerName)){
            String result = new HelloServiceImpl().sayHello(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
            channelHandlerContext.writeAndFlush(result);
        }
    }
}
