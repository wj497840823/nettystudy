package com.wj.netty.groupchat;

import com.wj.netty.util.BootStrapUtil;

/**
 * @author wangjie
 * @create 2020-03-24 11:02
 */
public class GroupChatServer {
    public static void main(String[] args) throws InterruptedException {

        BootStrapUtil.serverRunSimple(new GroupChatServerChannelInitializer(),7000);
    }

}
