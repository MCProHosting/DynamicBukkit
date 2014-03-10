package com.mcprohosting.plugins.dynamicbukkit.data;

import com.mcprohosting.plugins.dynamicbukkit.DynamicBukkit;
import redis.clients.jedis.Jedis;

public class ChannelSubscriber implements Runnable {

    final String channel;

    public ChannelSubscriber(final String channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        subscribe();
    }

    public void subscribe() {
        NetDelegate.channels.add(channel);

        Jedis jedis = DynamicBukkit.getPlugin().getJedisResource();
        jedis.subscribe(NetDelegate.instance, channel);
        DynamicBukkit.getPlugin().returnJedisResource(jedis);
    }
}
