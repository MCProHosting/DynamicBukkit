package com.mcprohosting.plugins.dynamicbukkit.config;

import com.gmail.favorlock.commonutils.configuration.ConfigModel;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.Plugin;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.File;

public class MainConfig extends ConfigModel {

    public MainConfig(Plugin plugin) {
        CONFIG_HEADER = "Dynamic Bukkit Configuration!";
        CONFIG_FILE = new File(plugin.getDataFolder(), "config.yml");

        invoke();
    }

    public void invoke() {
        try {
            this.init();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public String server_name = "dynserv_1";
    public String server_externalip = "";
    public String jedis_host = "127.0.0.1";
    public int jedis_port = 6379;
    public int jedis_timeout = 10000;
    public String jedis_password = "";

    public JedisPool getJedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setTestOnBorrow(true);

        if (jedis_password.equals("") || jedis_password == null) {
            return new JedisPool(config, jedis_host, jedis_port, jedis_timeout);
        } else {
            return new JedisPool(config, jedis_host, jedis_port, jedis_timeout, jedis_password);
        }
    }

}
