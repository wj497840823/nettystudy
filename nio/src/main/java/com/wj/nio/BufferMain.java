package com.wj.nio;

import java.nio.IntBuffer;

/**
 * @author wangjie
 * @create 2020-03-22 10:56
 */
public class BufferMain {

    public static void main(String[] args) {
        IntBuffer allocate = IntBuffer.allocate(5);
        for (int i = 0; i <allocate.capacity() ; i++) {
            allocate.put(i);
        }
        allocate.flip();
        while(allocate.hasRemaining()){
            System.out.println(allocate.get());
        }
    }
}
