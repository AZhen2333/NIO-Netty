package rpc.server.impl;

import rpc.server.HelloRPC;

/**
 * @description: Server(服务的提供方)实现类
 * @author: Czz
 * @create: 2019-08-01 15:33
 */
public class HelloRPCImpl implements HelloRPC {
    @Override
    public String hello(String name) {
        return "hello," + name;
    }
}
