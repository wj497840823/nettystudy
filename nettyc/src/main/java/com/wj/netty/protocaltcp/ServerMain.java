package com.wj.netty.protocaltcp;

import com.wj.netty.util.BootStrapUtil;

/**
 * @author wangjie
 * @create 2020-03-25 9:50
 */
public class ServerMain {

    public static void main(String[] args) throws InterruptedException {
        BootStrapUtil.serverRunSimple(new ServerChannelInitializer(),9000);
    }
}
