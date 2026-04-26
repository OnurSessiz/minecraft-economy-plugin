package com.onursessiz.economypluginbyoriginframes;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import org.bukkit.ChatColor;

public class ScoreboardManager {

    private final EconomyPluginByOriginFrames plugin;

    public ScoreboardManager(EconomyPluginByOriginFrames plugin) {
        this.plugin = plugin;
    }

    public void setScoreboard(Player player) {
        org.bukkit.scoreboard.ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();

        Objective objective = board.registerNewObjective(
                "sidebar", "dummy",
                ChatColor.AQUA + "" + ChatColor.BOLD + "Sonar Server"
        );
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        int score = 9;

        setLine(board, objective, ChatColor.GRAY + "───────────────", score--);
        setLine(board, objective, ChatColor.WHITE + "👤 " + player.getName(), score--);
        setLine(board, objective, ChatColor.YELLOW + "💰 Coin: " + ChatColor.GOLD + plugin.getCoins(player.getUniqueId()), score--);
        setLine(board, objective, ChatColor.DARK_GRAY + " ───────────────", score--);
        setLine(board, objective, ChatColor.DARK_GRAY + "Plugin by: " + ChatColor.GREEN + "OriginFrames", score--);

        player.setScoreboard(board);
    }

    private void setLine(Scoreboard board, Objective objective, String text, int score) {
        objective.getScore(text).setScore(score);
    }
}