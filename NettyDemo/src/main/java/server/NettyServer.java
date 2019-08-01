package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @description: 服务器端程序
 * @author: Czz
 * @create: 2019-08-01 11:09
 */
public class NettyServer {
    public static void main(String[] args) {
        // 创建一个线程组：用来处理网络事件（接受客户端连接）
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        // 创建一个线程组：用来处理网络事件（处理通道IO操作）
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        // 创建服务器端助手来配置参数
        ServerBootstrap b = new ServerBootstrap();

        // 设置两个线程组 EventLoopGroup
        b.group(bossGroup, workGroup)
                // 使用NioServerSocketChannel作为服务器端通道实现
                .channel(NioServerSocketChannel.class)
                // 设置线程队列中等待连接的个数
                .option(ChannelOption.SO_BACKLOG, 128)
                // 保持活动连接状态
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                // 创建一个通道初始化对象
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    // 往 Pipeline 链中添加自定义的业务处理 handler
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        // 服务器端业务处理类
                        socketChannel.pipeline().addLast(new NettyServerHandler());
                        System.out.println(".......Server is ready.......");
                    }
                });

        try {
            // 启动服务器端并绑定端口，等待接受客户端连接（非阻塞）
            ChannelFuture cf = b.bind(9999).sync();
            System.out.println("......Server is Starting......");
            // 关闭通道， 关闭线程池
            cf.channel().closeFuture().sync();
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
