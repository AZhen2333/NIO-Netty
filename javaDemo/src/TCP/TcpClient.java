package TCP;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class TcpClient {
    public static void main(String[] args) {
        while (true) {
            try {
                // 创建socket对象
                Socket socket = new Socket("127.0.0.1", 9999);
                // 从连接中取出输出流并发消息
                OutputStream os = socket.getOutputStream();
                System.out.println("请输入：");
                Scanner scanner = new Scanner(System.in);
                String msg = scanner.nextLine();
                os.write(msg.getBytes());
                // 从连接中取出输入流并回话
                // 等待服务端返回数据，如果没有，就会一直等待，形成阻塞。
                InputStream is = socket.getInputStream();
                byte[] bytes = new byte[20];
                is.read(bytes);
                System.out.println("回话说；" + new String(bytes).trim());
                // 关闭
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
