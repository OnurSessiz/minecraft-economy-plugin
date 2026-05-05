package com.onursessiz.economypluginbyoriginframes;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class EconomyPluginByOriginFrames extends JavaPlugin {
    public int getCoins(UUID uuid) {
        return coins.getOrDefault(uuid, 0);
    }

    public void addCoins(UUID uuid, int amount) {
        coins.put(uuid, getCoins(uuid) + amount);
        getConfig().set("coins." + uuid, getCoins(uuid));
        setCoinChangeActivity(true);
    }
    private HashMap<UUID, Integer> coins = new HashMap<>();

    public void loseCoins(UUID uuid, int amount) {
        coins.put(uuid, getCoins(uuid) - amount);
        getConfig().set("coins." + uuid, getCoins(uuid));
        setCoinChangeActivity(true);
    }
    public Map<UUID, Integer> getAllCoins() {
        return coins;
    }

    private boolean coinChangeActivity;

    public boolean isCoinChangeActivity() {
        return coinChangeActivity;
    }

    public void setCoinChangeActivity(boolean coinChangeActivity) {
        this.coinChangeActivity = coinChangeActivity;
    }

    private ScoreboardManager scoreboardManager;

    @Override
    public void onEnable() {
        new BukkitRunnable() {
            @Override
            public void run() {
               if(coinChangeActivity){
                   saveConfig();
                   coinChangeActivity=false;
               }
            }
        }.runTaskTimer(this, 0L, 200L);

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    scoreboardManager.setScoreboard(player);
                }
            }
        }.runTaskTimer(this, 0L, 20L);
        getCommand("pay").setExecutor(new PayCommand(this));
        getCommand("coin").setTabCompleter(new CoinTabCompleter());
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
        this.scoreboardManager = new ScoreboardManager(this);
        getServer().getPluginManager().registerEvents(new ScoreboardListener(scoreboardManager), this);

    }

    @Override
    public void onDisable() {
        getLogger().info("Ilk plugin kapandi!");
        saveConfig();
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
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
