package com.wj.netty.rpc.costumer;

import com.wj.netty.rpc.allinterface.HelloService;
import com.wj.netty.rpc.netty.Client;

/**
 * @author wangjie
 * @create 2020-03-25 15:26
 */
public class HelloRemote {
    public static final String providerName="HelloService#hello#";

    public static void main(String[] args) {
        Client client = new Client();
        HelloService bean = (HelloService)client.getBean(HelloService.class, providerName);

        String good = bean.sayHello("good");

        System.out.println("最终调用的结果"+good);

    }
}
