package com.wj.netty.rpc.provider;

import com.wj.netty.rpc.allinterface.HelloService;

/**
 * @author wangjie
 * @create 2020-03-25 14:02
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String str) {
        System.out.println("调用sayHello成功"+str);
        return str;
    }
}
