package rpc.handler;

import io.netty.channel.ChannelInboundHandlerAdapter;
import rpc.pojo.ClassInfo;

/**
 * @description: 服务器端业务处理类
 * @author: Czz
 * @create: 2019-08-01 15:38
 */
public class InvokeHandler extends ChannelInboundHandlerAdapter {

    /**
     * 得到某接口下某个实现类的名字
     *
     * @param classInfo
     * @return
     * @throws Exception
     */
    private String getImplClassName(ClassInfo classInfo) throws Exception {
        // 服务方接口和实现类所在的包路径
        String interfacePath = "rpc.server";
        int lastDot = classInfo.getClassName().lastIndexOf(".");
        String interfaceName = classInfo.getClassName().substring(lastDot);
        Class superClass = Class.forName(interfacePath + interfaceName);

        return null;
    }
}
