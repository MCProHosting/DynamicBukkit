package com.mcprohosting.plugins.dynamicbukkit;

import com.mcprohosting.plugins.dynamicbukkit.config.MainConfig;
import com.mcprohosting.plugins.dynamicbukkit.data.NetHandler;
import com.mcprohosting.plugins.dynamicbukkit.server.HeartbeatTask;
import com.mcprohosting.plugins.dynamicbukkit.server.ServerHandler;
import com.mcprohosting.plugins.dynamicbukkit.server.ServerHeartbeatHandler;
import com.mcprohosting.plugins.dynamicbukkit.server.ServerInfo;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DynamicBukkit extends JavaPlugin {

    private static DynamicBukkit plugin;
    private MainConfig config;
    private JedisPool jedis;
    private NetHandler dispatch;
    private ServerHeartbeatHandler beatHandler;

    private DynamicPluginLoader pluginLoader;

    public void onEnable() {
        plugin = this;
        config = new MainConfig(this);

        initJedis();

        pluginLoader = new DynamicPluginLoader();
    }

    public static DynamicBukkit getPlugin() {
        return plugin;
    }

    public void initJedis() {
        jedis = config.getJedisPool();

        Jedis connection = getJedisResource();
        if (connection == null) {
            return;
        } else {
            getLogger().info("Connected to Jedis Server!");
            returnJedisResource(connection);
        }

        dispatch = new NetHandler();
        getDispatch().registerTasks(new ServerHandler());
        beatHandler = new ServerHeartbeatHandler();
        Bukkit.getScheduler().runTask(this, new HeartbeatTask());
    }

    public Jedis getJedisResource() {
        try {
            return jedis.getResource();
        } catch (JedisConnectionException e) {
            e.printStackTrace();
            getLogger().warning("Unable to acquire connection from Redis server!");
        }

        return null;
    }

    public void returnJedisResource(Jedis jedis) {
        this.jedis.returnResource(jedis);
    }

    public NetHandler getDispatch() {
        return dispatch;
    }

    public MainConfig getConf() {
        return config;
    }

    public ConcurrentMap<String, ServerInfo> getServerInfo() {
        return new ConcurrentHashMap<>(ServerInfo.getServerInfos());
    }

    public ServerHeartbeatHandler getBeatHandler() {
        return beatHandler;
    }

}
