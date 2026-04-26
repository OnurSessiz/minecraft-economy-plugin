package com.onursessiz.economypluginbyoriginframes;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CoinTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            suggestions.add("give");
            suggestions.add("remove");
            suggestions.add("set");
            suggestions.add("get");
        }

        if (args.length == 2) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                suggestions.add(p.getName());
            }
        }

        return suggestions;
    }
}