package com.mcprohosting.plugins.dynamicbukkit.utils;

import com.mcprohosting.plugins.dynamicbukkit.data.NetTask;
import org.bukkit.entity.Player;

public class DynamicUtils {

    public static void sendPlayerToServer(Player player, String server) {
        NetTask.withName("send")
                .withArg("player", player.getName())
                .withArg("server", server).send("dynamicbungee");
    }

}
