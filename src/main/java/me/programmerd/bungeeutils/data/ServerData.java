package me.programmerd.bungeeutils.data;

import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.config.ServerInfo;

public class ServerData {

    private ServerInfo info;
    private int maxPlayers;
    private boolean online;

    public ServerData(ServerInfo info) {
        this.info = info;

        if (info != null) {
            info.ping(new Callback<ServerPing>() {
                @Override
                public void done(ServerPing result, Throwable error) {
                    if (error == null) {
                        maxPlayers = result.getPlayers().getMax();
                        online = true;
                    } else {
                        maxPlayers = -1;
                        online = false;
                    }
                }
            });
        } else {
            online = false;
        }

    }

    public boolean isOnline() {
        return online;
    }

    public int getOnlinePlayers() {
        return info.getPlayers().size();
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void update() {
        if (info != null) {
            info.ping(new Callback<ServerPing>() {
                @Override
                public void done(ServerPing result, Throwable error) {
                    if (error == null) {
                        maxPlayers = result.getPlayers().getMax();
                        online = true;
                    } else {
                        maxPlayers = -1;
                        online = false;
                    }
                }
            });
        }
    }

    public ServerInfo getServerInfo() {
        return info;
    }

}
