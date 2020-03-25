package com.wj.netty.protocaltcp;

/**
 * 自定义协议包
 * @author wangjie
 * @create 2020-03-25 10:38
 */
public class MessageProtocol {

    private int len;
    private byte[] content;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
