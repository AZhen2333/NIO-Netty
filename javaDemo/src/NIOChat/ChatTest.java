package NIOChat;

import java.util.Scanner;

public class ChatTest {
    public static void main(String[] args) {
        // 创建一个聊天客户端对象
        ChatClient chatClient = new ChatClient();

        /**
         * 单独开一条线程不断地接受服务端广播的数据
         */
        new Thread() {
            public void run() {
                while (true) {
                    chatClient.receiveMsg();

                    try {
                        // 间隔3秒
                        Thread.currentThread().sleep(3000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String nextLine = scanner.nextLine();
            chatClient.sendMsg("send message ...");
        }
    }
}
