package com.wj.netty.protocaltcp;

import com.wj.netty.util.BootStrapUtil;

/**
 * @author wangjie
 * @create 2020-03-25 9:53
 */
public class ClientMain {

    public static void main(String[] args) throws InterruptedException {
        BootStrapUtil.clientSimpleRun(new ClientChannelInitializer(),"127.0.0.1",9000);
    }
}
