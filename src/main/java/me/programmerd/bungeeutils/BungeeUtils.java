package me.programmerd.bungeeutils;

import me.programmerd.bungeeutils.commands.ListCommand;
import me.programmerd.bungeeutils.data.BUFile;
import me.programmerd.bungeeutils.data.ServerData;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class BungeeUtils extends Plugin {

    private BUFile messages;
    private BUFile config;

    private List<ServerData> serverDataList;
    private ScheduledTask task;

    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) getDataFolder().mkdir();

        messages = new BUFile("messages.yml");
        config = new BUFile("config.yml");

        serverDataList = new ArrayList<>();
        for (ServerInfo info : ProxyServer.getInstance().getServers().values()) {
            serverDataList.add(new ServerData(info));
        }

        task = ProxyServer.getInstance().getScheduler().schedule(getInstance(), new Runnable() {
            @Override
            public void run() {
                for (ServerData data : serverDataList) {
                    data.update();
                }
            }
        }, getConfig().getConfiguration().getInt("server-pinger-interval"), getConfig().getConfiguration().getInt("server-pinger-interval"), TimeUnit.SECONDS);

        ProxyServer.getInstance().getPluginManager().registerCommand(getInstance(), new ListCommand());

    }

    @Override
    public void onDisable() {
        if (task != null) task.cancel();
    }

    public static BungeeUtils getInstance() {
        Plugin plugin = ProxyServer.getInstance().getPluginManager().getPlugin("BungeeUtils");
        return (BungeeUtils) plugin;
    }

    public BUFile getMessages() {
        return messages;
    }

    public BUFile getConfig() {
        return config;
    }

    public List<ServerData> getServerDataList() {
        return serverDataList;
    }
}
