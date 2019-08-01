package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @description: 客户端
 * @author: Czz
 * @create: 2019-08-01 11:31
 */
public class NettyClient {
    public static void main(String[] args) {
        // 创建一个 EventLoopGroup 线程组
        EventLoopGroup group = new NioEventLoopGroup();
        // 创建客户端启动助手
        Bootstrap b = new Bootstrap();

        // 设置 EventLoopGroup 线程组
        b.group(group)
                // 使用 NioSocketChannel 作为客户端通道实现
                .channel(NioSocketChannel.class)
                // 创建一个通道初始化对象
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    // 往 Pipeline 链中添加自定义的业务处理handler
                    protected void initChannel(SocketChannel sc) {
                        // 客户端业务处理类
                        sc.pipeline().addLast(new NettyClientHandle());
                        System.out.println("......Client is ready.......");
                    }
                });
        try {
            // 启动客户端,等待连接上服务器端(非阻塞)
            ChannelFuture cf = b.connect("127.0.0.1", 9999).sync();
            // 等待连接关闭
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
