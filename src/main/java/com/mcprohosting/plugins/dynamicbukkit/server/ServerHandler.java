package com.mcprohosting.plugins.dynamicbukkit.server;

import com.mcprohosting.plugins.dynamicbukkit.DynamicBukkit;
import com.mcprohosting.plugins.dynamicbukkit.data.NetTaskSubscribe;
import org.bukkit.Bukkit;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;

public class ServerHandler {

    @NetTaskSubscribe(name = "heartbeat", args = {"name", "ip", "port", "players"})
    public void onHeartbeat(HashMap<String, Object> args) {
        Object i = args.get("ip");
        Object n = args.get("name");
        Object p = args.get("port");
        Object pl = args.get("players");

        if ((i instanceof String) == false
                || (n instanceof String) == false
                || (p instanceof Integer) == false
                || (pl instanceof List) == false) {
            return;
        }
        String ip = (String) i;
        String name = (String) n;
        Integer port = (Integer) p;
        List list = (List) pl;

        InetSocketAddress socketAddress = new InetSocketAddress(ip, port);
        ServerInfo serverInfo = ServerInfo.getServerInfo(name);
        if (serverInfo != null) {
            if (serverInfo.getAddress().equals(socketAddress)) {
                DynamicBukkit.getPlugin().getBeatHandler().heartbeatReceived(serverInfo, list);
                return;
            }
        }
        ServerInfo info = new ServerInfo(name, socketAddress, Bukkit.getMotd(), list);
        ServerInfo.addServerInfo(info);
        DynamicBukkit.getPlugin().getBeatHandler().heartbeatReceived(info, list);
    }

    @NetTaskSubscribe(name = "disconnect", args = {"name"})
    public void onDisconnect(HashMap<String, Object> args) {
        Object n = args.get("name");
        if ((n instanceof String) == false) {
            return;
        }
        String name = (String) n;
        ServerInfo serverInfo = ServerInfo.getServerInfo(name);
        if (serverInfo == null) {
            return;
        }
        ServerInfo.removeServerInfo(name);
    }

}
