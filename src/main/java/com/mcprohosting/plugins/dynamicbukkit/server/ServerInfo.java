package com.mcprohosting.plugins.dynamicbukkit.server;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerInfo {

    private static Map<String, ServerInfo> info;

    private String name;
    private InetSocketAddress address;
    private String motd;
    private List<String> players;

    static {
        info = new HashMap<>();
    }

    public ServerInfo(String name, InetSocketAddress address, String motd, List<String> players) {
        this.name = name;
        this.address = address;
        this.motd = motd;
        this.players = players;
    }

    public static ServerInfo getServerInfo(String name) {
        return info.get(name);
    }

    public static Map<String, ServerInfo> getServerInfos() {
        return info;
    }

    public static void addServerInfo(ServerInfo serverInfo) {
        info.put(serverInfo.getName(), serverInfo);
    }

    public static void removeServerInfo(String name) {
        info.remove(name);
    }

    public String getName() {
        return name;
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    public String getMotd() {
        return motd;
    }

    public List<String> getPlayers() {
        return players;
    }

}
