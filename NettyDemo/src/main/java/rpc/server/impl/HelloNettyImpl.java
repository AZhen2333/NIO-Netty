package rpc.server.impl;

import rpc.server.HelloNetty;

/**
 * @description: Server(服务的提供方)实现类
 * @author: Czz
 * @create: 2019-08-01 15:32
 */
public class HelloNettyImpl implements HelloNetty {
    @Override
    public String hello() {
        return "hello,netty";
    }


}
