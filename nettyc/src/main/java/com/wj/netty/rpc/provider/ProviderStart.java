package com.wj.netty.rpc.provider;

import com.wj.netty.rpc.netty.ServerStart;

/**
 * @author wangjie
 * @create 2020-03-25 15:22
 */
public class ProviderStart {

    public static void main(String[] args) {
        ServerStart.startServer("127.0.0.1",9000);
    }
}
