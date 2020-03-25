package com.wj.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 将文件中的数据通过nio读入buffer中
 * 1.获取文件输入流
 * 2.通过文件输入流获取channel
 * 3.创建bytebuffer，buffer长度为文件的length
 * 4.调用channel.read（）将数据写入buffer中
 * @author wangjie
 * @create 2020-03-22 11:12
 */
public class NioFileRead {
    public static void main(String[] args) {
        try {
            File file = new File("d:\\file\\file01.txt");
            System.out.println("文件长度为"+file.length());
            FileInputStream fileInputStream = new FileInputStream(file);
            FileChannel channel = fileInputStream.getChannel();
            ByteBuffer allocate = ByteBuffer.allocate((int) file.length());

            channel.read(allocate);

            System.out.println(new String(allocate.array()));

            channel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
