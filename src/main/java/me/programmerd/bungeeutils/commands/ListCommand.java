package me.programmerd.bungeeutils.commands;

import me.programmerd.bungeeutils.BungeeUtils;
import me.programmerd.bungeeutils.data.ServerData;
import me.programmerd.bungeeutils.utils.ColorUtil;
import net.md_5.bungee.Util;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListCommand extends Command {

    public ListCommand() {
        super("glist", "bungeecord.command.list");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Configuration messages = BungeeUtils.getInstance().getMessages().getConfiguration();
        TextComponent component = new TextComponent();
        component.setText(ColorUtil.get(messages.getString("glist-header").replace("%connected-players%", String.valueOf(ProxyServer.getInstance().getOnlineCount()))));
        sender.sendMessage(component);

        for (ServerData data : BungeeUtils.getInstance().getServerDataList()) {
            if (!data.getServerInfo().canAccess(sender)) continue;
            if (data.isOnline()) {
                int onlinePlayers = ProxyServer.getInstance().getOnlineCount() == 0 ? 1 : ProxyServer.getInstance().getOnlineCount();
                double percentage = data.getOnlinePlayers() / onlinePlayers * 100;

                List<String> players = new ArrayList<>();
                for (ProxiedPlayer player : data.getServerInfo().getPlayers()) {
                    players.add(player.getDisplayName());
                }

                String online = ColorUtil.get(messages.getString("no-players-connected"));

                if (!players.isEmpty()) {
                    Collections.sort(players, String.CASE_INSENSITIVE_ORDER);
                    online = Util.format(players, ", ");
                }

                component.setText(ColorUtil.get(messages.getString("glist-format-online").replace("%server-name%", data.getServerInfo().getName()).replace("%connected-players%", String.valueOf(data.getOnlinePlayers())).replace("%max-players%", String.valueOf(data.getMaxPlayers())).replace("%percentage%", new DecimalFormat("##.##").format(percentage)).replace("%players%", online)));
                sender.sendMessage(component);
            } else {
                component.setText(ColorUtil.get(messages.getString("glist-format-offline").replace("%server-name%", data.getServerInfo().getName())));
                sender.sendMessage(component);
            }
        }

        component.setText(ColorUtil.get(messages.getString("glist-footer").replace("%connected-players%", String.valueOf(ProxyServer.getInstance().getOnlineCount()))));
        sender.sendMessage(component);
    }
}
