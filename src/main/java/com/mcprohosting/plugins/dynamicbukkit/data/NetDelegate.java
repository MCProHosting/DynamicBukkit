package com.mcprohosting.plugins.dynamicbukkit.data;

import com.mcprohosting.plugins.dynamicbukkit.DynamicBukkit;
import org.bukkit.Bukkit;
import org.json.JSONException;
import org.json.JSONObject;
import redis.clients.jedis.JedisPubSub;

import java.lang.Override;import java.lang.Runnable;import java.lang.String;import java.util.concurrent.CopyOnWriteArrayList;

public class NetDelegate extends JedisPubSub implements Runnable {
    public static NetDelegate instance;
    public static CopyOnWriteArrayList<String> channels;

    public NetDelegate() {
        instance = this;
        channels = new CopyOnWriteArrayList<>();

        Bukkit.getScheduler().runTaskAsynchronously(DynamicBukkit.getPlugin(), new ChannelSubscriber("heartbeat"));
        Bukkit.getScheduler().runTaskAsynchronously(DynamicBukkit.getPlugin(), new ChannelSubscriber("dynamicbukkit"));
    }

    @Override
    public void onMessage(String channel, String data) {
        if (channels.contains(channel) == false) {
            return;
        }

        JSONObject object;
        try {
            object = new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        if (DynamicBukkit.getPlugin().getDispatch().handleMessage(object) == false) {
            DynamicBukkit.getPlugin().getLogger().warning("Failed to handle redis message!");
        }
    }

    public void onPMessage(String s, String s2, String s3) {}

    public void onSubscribe(String s, int i) {}

    public void onUnsubscribe(String s, int i) {}

    public void onPUnsubscribe(String s, int i) {}

    public void onPSubscribe(String s, int i) {}

    public void run() {
    }
}
