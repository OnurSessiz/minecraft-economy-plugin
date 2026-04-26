package com.onursessiz.economypluginbyoriginframes;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ScoreboardListener implements Listener {

    private final ScoreboardManager scoreboardManager;

    public ScoreboardListener(ScoreboardManager scoreboardManager) {
        this.scoreboardManager = scoreboardManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        scoreboardManager.setScoreboard(event.getPlayer());
    }
}