package com.onursessiz.ilkplugin;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class IlkPlugin extends JavaPlugin {
    public int getCoins(UUID uuid) {
        return coins.getOrDefault(uuid, 0);
    }

    public void addCoins(UUID uuid, int amount) {
        coins.put(uuid, getCoins(uuid) + amount);
    }
    private HashMap<UUID, Integer> coins = new HashMap<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new CoinSystem(this), this);
        getLogger().info("Plugin aktif!");
        getCommand("coin").setExecutor(new CoinCommmand(this));
        saveDefaultConfig();
        if (getConfig().getConfigurationSection("coins") != null) {
            for (String uuidStr : getConfig().getConfigurationSection("coins").getKeys(false)) {
                UUID uuid = UUID.fromString(uuidStr);
                int coin = getConfig().getInt("coins." + uuidStr);

                coins.put(uuid, coin);
            }
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("Ilk plugin kapandi!");
        saveConfig();
    }

    public UUID getTopPlayer() {
        UUID top = null;

        if(coins.isEmpty()){
            return null;
        }
    else {
            for (UUID uuid : coins.keySet()) {
                if (top == null) {
                    top = uuid;
                } else {
                    if (coins.get(top) < coins.get(uuid)) {
                        top = uuid;
                    }
                }
            }
        }
        return top;
    }
}
