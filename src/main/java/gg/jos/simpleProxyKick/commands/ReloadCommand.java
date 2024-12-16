package gg.jos.simpleProxyKick.commands;

import gg.jos.simpleProxyKick.SimpleProxyKick;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {

    private final SimpleProxyKick plugin;

    public ReloadCommand(SimpleProxyKick plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && !sender.hasPermission("simpleproxykick.reload")) {
            sender.sendMessage(plugin.getMessage("no-permission"));
            return true;
        }

        plugin.reloadConfig();
        plugin.getKickReasons().clear();
        plugin.getKickReasons().addAll(plugin.getConfig().getStringList("kick-reasons"));

        sender.sendMessage(plugin.getMessage("reload-command-success"));
        return true;
    }
}