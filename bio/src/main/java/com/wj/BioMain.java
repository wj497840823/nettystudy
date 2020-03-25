package com.wj;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wangjie
 * @create 2020-03-21 23:10
 */
public class BioMain {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        try {
            ServerSocket serverSocket = new ServerSocket(6666);
            System.out.println("服务端已开启");
            while(true){
                //阻塞
                Socket accept = serverSocket.accept();
                System.out.println("接收一个客户端");
                //handler(accept);
                executorService.execute(()->{
                    handler(accept);
                });



            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void handler(Socket socket){
        byte[] bytes = new byte[1024];

        try {
            InputStream inputStream = socket.getInputStream();
            while (true){
                //阻塞
                int read = inputStream.read(bytes);
                System.out.println(read);
                if(read!=-1){
                    System.out.println(new String(bytes,0,read));
                }else{
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
