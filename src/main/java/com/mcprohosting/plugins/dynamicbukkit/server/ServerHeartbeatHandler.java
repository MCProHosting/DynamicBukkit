package com.mcprohosting.plugins.dynamicbukkit.server;

import com.mcprohosting.plugins.dynamicbukkit.DynamicBukkit;
import org.bukkit.Bukkit;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class ServerHeartbeatHandler implements Runnable {

    /**
     * The amount of time that a server has to send a heartbeat before it's removed.
     */
    private static final Long TIME_EXPIRE = TimeUnit.SECONDS.toMillis(30);

    /**
     * Stores previous heartbeats.
     */
    private Map<ServerInfo, Heartbeat> heartbeats;

    /**
     * Creates a new handler, and schedules it in the BungeeCord scheduler.
     */
    public ServerHeartbeatHandler() {
        this.heartbeats = new HashMap<>();
        schedule();
    }

    /**
     * Call this method when a server sends a heartbeat.
     * @param info The server that a heartbeat was received for.
     */
    public void heartbeatReceived(ServerInfo info, List playerList) {
        ArrayList<String> players = new ArrayList<>();
        for (Object p : playerList) {
            if ((p instanceof String) == false) {
                continue;
            }
            players.add((String) p);
        }
        this.heartbeats.put(info, new Heartbeat(info, Calendar.getInstance().getTimeInMillis(), players));
    }

    @Override
    public void run() {
        ConcurrentMap<String, ServerInfo> allServerInfo = DynamicBukkit.getPlugin().getServerInfo();
        for (ServerInfo info : allServerInfo.values()) {
            Heartbeat heartbeat = heartbeats.get(info);
            if (heartbeat == null ||
                    Calendar.getInstance().getTimeInMillis() - heartbeat.getTimeHeartbeat()
                            > ServerHeartbeatHandler.TIME_EXPIRE) {
                ServerInfo.removeServerInfo(info.getName());
                this.heartbeats.remove(info);
            }
        }
        schedule();
    }

    /**
     * Reschedule this in the scheduler for execution.
     */
    public void schedule() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(DynamicBukkit.getPlugin(), this, 30 * 20);
    }

}
