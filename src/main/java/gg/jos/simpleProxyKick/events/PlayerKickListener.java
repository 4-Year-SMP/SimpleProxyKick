package gg.jos.simpleProxyKick.events;

import gg.jos.simpleProxyKick.SimpleProxyKick;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PlayerKickListener implements Listener {

    private final SimpleProxyKick plugin;

    public PlayerKickListener(SimpleProxyKick plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        String kickReason = event.getReason();
        for (String reason : plugin.getKickReasons()) {
            if (kickReason.contains(reason)) {
                kickPlayer(event.getPlayer(), kickReason);
                break;
            }
        }
    }

    /**
     * Kicks the player from the proxy with the given reason
     * @param p The player to kick
     * @param reason The reason to kick with
     */
    private void kickPlayer(Player p, String reason) {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(byteArray);

        if (p.getPlayer() == null) {
            plugin.getLogger().warning("Player Identifier: " + p + " is null and was not proxy kicked!");
            return;
        }

        try {
            out.writeUTF("KickPlayer");
            out.writeUTF(p.getPlayer().getName());
            out.writeUTF(reason);
        } catch (IOException e) {
            e.printStackTrace();
        }

        p.sendPluginMessage(plugin, "BungeeCord", byteArray.toByteArray());
    }
}