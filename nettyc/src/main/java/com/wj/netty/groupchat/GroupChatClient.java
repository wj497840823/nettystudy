package com.wj.netty.groupchat;

import com.wj.netty.util.BootStrapUtil;

/**
 * @author wangjie
 * @create 2020-03-24 13:40
 */
public class GroupChatClient {
    public static void main(String[] args) throws InterruptedException {
        BootStrapUtil.clientSimpleRunWithScanner(new GroupChatClientChannelInitializer(),"127.0.0.1",7000);
    }
}
