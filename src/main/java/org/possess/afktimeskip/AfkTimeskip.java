package org.possess.afktimeskip;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AfkTimeskip extends JavaPlugin implements Listener {

    private Map<UUID, Boolean> afkPlayers = new HashMap<>();
    private static final int NORMAL_TICK_RATE = 20;
    private int afkTickRate;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        loadConfig();
    }

    private void loadConfig() {
        FileConfiguration config = getConfig();
        config.addDefault("afk-tick-rate", 400);
        config.options().copyDefaults(true);
        saveConfig();

        afkTickRate = config.getInt("afk-tick-rate");
        getLogger().info("AFK Tick Rate set to: " + afkTickRate);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("afk")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be used by players.");
                return true;
            }
            toggleAFK((Player) sender);
            return true;
        } else if (command.getName().equalsIgnoreCase("afkreload")) {
            if (!sender.hasPermission("afktimeskip.reload")) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            }
            reloadConfig();
            loadConfig();
            sender.sendMessage(ChatColor.GREEN + "AFK Plugin configuration reloaded.");
            return true;
        }
        return false;
    }

    private void toggleAFK(Player player) {
        UUID playerId = player.getUniqueId();
        boolean isNowAFK = !afkPlayers.getOrDefault(playerId, false);
        afkPlayers.put(playerId, isNowAFK);

        if (isNowAFK) {
            player.setInvulnerable(true);
            if (Bukkit.getOnlinePlayers().size() == 1) {
                player.sendMessage(ChatColor.YELLOW + "You are now AFK. Attempting to optimize server performance.");
                setTickRate(afkTickRate);
            } else {
                player.sendMessage(ChatColor.YELLOW + "You are now AFK.");
                Bukkit.broadcastMessage(ChatColor.YELLOW + player.getName() + " is now AFK.");
            }
        } else {
            player.setInvulnerable(false);
            if (Bukkit.getOnlinePlayers().size() == 1) {
                player.sendMessage(ChatColor.GREEN + "You are no longer AFK. Restoring normal server performance.");
                setTickRate(NORMAL_TICK_RATE);
            } else {
                player.sendMessage(ChatColor.GREEN + "You are no longer AFK.");
                Bukkit.broadcastMessage(ChatColor.GREEN + player.getName() + " is no longer AFK.");
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        checkPlayerAction(event.getPlayer());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        checkPlayerAction(event.getPlayer());
    }

    private void checkPlayerAction(Player player) {
        if (afkPlayers.getOrDefault(player.getUniqueId(), false)) {
            toggleAFK(player);
        }
    }

    private void setTickRate(int tickRate) {
        String command = "tick rate " + tickRate;
        getServer().dispatchCommand(getServer().getConsoleSender(), command);
        getLogger().info("Attempted to set server tick rate to: " + tickRate);
    }
}