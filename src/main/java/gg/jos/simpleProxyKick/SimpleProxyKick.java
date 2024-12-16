package gg.jos.simpleProxyKick;

import gg.jos.simpleProxyKick.commands.ReloadCommand;
import gg.jos.simpleProxyKick.events.PlayerKickListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;

public final class SimpleProxyKick extends JavaPlugin {

    private List<String> kickReasons;

    @Override
    public void onEnable() {
        // Config
        saveDefaultConfig();
        kickReasons = getConfig().getStringList("kick-reasons");

        // Messaging Channels
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        // Events
        Bukkit.getPluginManager().registerEvents(new PlayerKickListener(this), this);

        // Commands
        Objects.requireNonNull(this.getCommand("simpleproxykickreload")).setExecutor(new ReloadCommand(this));

        // Logging
        getLogger().info("SimpleProxyKick is enabled!");
    }

    @Override
    public void onDisable() {
        // Messaging Channels
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);

        // Logging
        getLogger().info("SimpleProxyKick is disabled!");
    }

    /**
     * Returns the list of kick reasons within the config
     * @return List of kick reasons
     */
    public List<String> getKickReasons() {
        return kickReasons;
    }

    /**
     * Returns a message from the config
     * @param key The key of the message
     * @return The message
     */
    public String getMessage(String key) {
        String message = getConfig().getString(key);

        if (message == null) {
            return key;
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }
}