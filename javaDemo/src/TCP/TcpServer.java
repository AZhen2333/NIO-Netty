package TCP;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(9999);
            while (true) {
                // 监听客户端
                // 阻塞：accept()方法用来监听客户端连接，如果没有客户端连接， 就一直等待， 程序会阻塞到这里
                Socket socket = ss.accept();
                // 从连接中取出输入流来接受消息
                InputStream is = socket.getInputStream(); // 阻塞
                byte[] bytes = new byte[10];
                is.read(bytes);
                String clientIP = socket.getInetAddress().getHostAddress();
                System.out.println(clientIP + "说:" + new String(bytes).trim());
                // 从流中取出输出流并回话
                OutputStream os = socket.getOutputStream();
                os.write("没钱".getBytes());
                // 关闭
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
