package NIO;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIODeamo1 {

    public static void main(String[] args) {
        try {
            String str = "hello NIO";
            FileOutputStream fos = new FileOutputStream("D:\\download\\nio.txt");
            FileChannel channel = fos.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(str.getBytes());
            // 翻转缓冲区
            buffer.flip();
            channel.write(buffer);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            File file = new File("D:\\download\\nio.txt");
            FileInputStream fis = new FileInputStream(file);
            FileChannel channel = fis.getChannel();
            // 缓冲区的初始容量和文件的大小一样
            ByteBuffer buffer = ByteBuffer.allocate((int) file.length());
            channel.read(buffer);
            System.out.println(new String(buffer.array()));
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
