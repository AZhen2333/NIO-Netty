package NIO;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIODeamo1 {

    public static void main(String[] args) {
        // 写;
        try {
            String str = "hello NIO";
            FileOutputStream fos = new FileOutputStream("D:\\nio.txt");
            FileChannel channel = fos.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(str.getBytes());
            // 翻转缓冲区, 写模式转为读模式,
            // 此时position指向头, limit指向原position, 表示只能读取到有数据的长度, 而不是整个缓存的大小;
            // 写模式下不需要翻转?
            buffer.flip();
            channel.write(buffer);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 读;
        try {
            File file = new File("D:\\nio.txt");
            FileInputStream fis = new FileInputStream(file);
            FileChannel channel = fis.getChannel();
            // 缓冲区的初始容量和文件的大小一样
            ByteBuffer buffer = ByteBuffer.allocate((int) file.length());
            int byteRead = channel.read(buffer);
            while (byteRead != -1) {
                // 写模式切换为读模式，读取之前写入到buffer的所有数据;
                buffer.flip();
                // 翻转后的范围内有数据时;
                while (buffer.hasRemaining()) {
                    System.out.println("char=" + (char) buffer.get());
                }
                System.out.println(new String(buffer.array()));
                // make buffer ready for writing, 清空缓冲区数据, 用于再次写数据;
                buffer.clear();
                // 此时buffer已清空;
                byteRead = channel.read(buffer);
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
