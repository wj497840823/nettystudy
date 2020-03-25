package com.wj.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *
 * 利用一个buffer进行文件复制
 * @author wangjie
 * @create 2020-03-22 11:56
 */
public class FileCopy {

    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("d:\\file\\file01.txt");
        FileChannel channel = fileInputStream.getChannel();
        FileOutputStream fileOutputStream = new FileOutputStream("d:\\file\\file02.txt");
        FileChannel channel1 = fileOutputStream.getChannel();

        ByteBuffer allocate = ByteBuffer.allocate(512);
        while(true){
            allocate.clear();
            int read = channel.read(allocate);
            if(read==-1){
                break;
            }
            allocate.flip();
            channel1.write(allocate);
        }
        channel.close();
        channel1.close();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
