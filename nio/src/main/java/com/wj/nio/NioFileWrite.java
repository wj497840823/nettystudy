package com.wj.nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 利用byteBuffer和FileChannel在file01.txt中写入指定内容
 * 1.通过bio的文件输出流获取filechannel
 * 2.创建bytebuffer数组
 * 3.将字符串写入buffer中
 * 4.将buffer的数据写入channel
 * @author wangjie
 * @create 2020-03-22 10:57
 */
public class NioFileWrite {
    public static void main(String[] args) {
        String str = "hello,netty1131";
        try {
            //1
            FileOutputStream fileOutputStream = new FileOutputStream(new File("d:"+ File.separator+"file"+File.separator+"file01.txt"));
            FileChannel channel = fileOutputStream.getChannel();
            //2
            ByteBuffer allocate = ByteBuffer.allocate(str.getBytes().length);
            //3
            allocate.put(str.getBytes());
            //4
            allocate.flip();
            channel.write(allocate);

            channel.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
