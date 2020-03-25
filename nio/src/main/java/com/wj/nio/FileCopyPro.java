package com.wj.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author wangjie
 * @create 2020-03-22 12:08
 */
public class FileCopyPro {

    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("d:\\file\\file01.txt");
        FileChannel channel = fileInputStream.getChannel();
        FileOutputStream fileOutputStream = new FileOutputStream("d:\\file\\file03.txt");
        FileChannel channel1 = fileOutputStream.getChannel();
        channel1.transferFrom(channel,0,channel.size());

        channel.close();
        channel1.close();
        fileInputStream.close();
        fileOutputStream.close();

    }

}
