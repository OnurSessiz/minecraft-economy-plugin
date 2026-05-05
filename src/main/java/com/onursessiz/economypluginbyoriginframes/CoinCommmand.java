package com.onursessiz.economypluginbyoriginframes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.*;

@SuppressWarnings("deprecation")
public class CoinCommmand implements CommandExecutor {

    private final EconomyPluginByOriginFrames plugin;

    public CoinCommmand(EconomyPluginByOriginFrames plugin) {
        this.plugin = plugin;
    }


    private boolean yetkiKontrol(CommandSender sender) {
        if (!sender.hasPermission("coin.admin")) {
            sender.sendMessage(ChatColor.RED + "Yetkin yok.");
            return false;
        }
        return true;
    }

    private OfflinePlayer getTarget(String name, CommandSender sender) {
        OfflinePlayer target = Bukkit.getOfflinePlayer(name);
        if (target.getName() == null) {
            sender.sendMessage(ChatColor.RED + "Oyuncu bulunamadı!");
            return null;
        }
        return target;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage(ChatColor.YELLOW + "Kullanım: /coin <give/remove/set/get/top>");
            return true;
        }

        String sub = args[0].toLowerCase();

        switch (sub) {


            case "give": {
                if (!yetkiKontrol(sender)) return true;
                if (args.length != 3) {
                    sender.sendMessage(ChatColor.RED + "Kullanım: /coin give <oyuncu> <miktar>");
                    return true;
                }
                OfflinePlayer target = getTarget(args[1], sender);
                if (target == null) return true;

                int amount;
                try {
                    amount = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "Geçerli bir sayı gir.");
                    return true;
                }
                if (amount <= 0) {
                    sender.sendMessage(ChatColor.RED + "Eklenecek coin miktarı pozitif bir değer olmalıdır!");
                    return true;
                }

                plugin.addCoins(target.getUniqueId(), amount);
                sender.sendMessage(ChatColor.GREEN + target.getName() + "'e " + amount + " coin verildi!");
                break;
            }


            case "remove": {
                if (!yetkiKontrol(sender)) return true;
                if (args.length != 3) {
                    sender.sendMessage(ChatColor.RED + "Kullanım: /coin remove <oyuncu> <miktar>");
                    return true;
                }
                OfflinePlayer target = getTarget(args[1], sender);
                if (target == null) return true;

                int amount;
                try {
                    amount = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "Geçerli bir sayı gir.");
                    return true;
                }
                if (amount <= 0) {
                    sender.sendMessage(ChatColor.RED + "Azaltılacak coin miktarı pozitif bir değer olmalıdır!");
                    return true;
                }

                plugin.loseCoins(target.getUniqueId(), amount);
                sender.sendMessage(ChatColor.GREEN + target.getName() + "'den " + amount + " coin alındı!");
                break;
            }


            case "set": {
                if (!yetkiKontrol(sender)) return true;
                if (args.length != 3) {
                    sender.sendMessage(ChatColor.RED + "Kullanım: /coin set <oyuncu> <miktar>");
                    return true;
                }
                OfflinePlayer target = getTarget(args[1], sender);
                if (target == null) return true;

                int amount;
                try {
                    amount = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "Geçerli bir sayı gir.");
                    return true;
                }

                plugin.loseCoins(target.getUniqueId(), plugin.getCoins(target.getUniqueId()));
                plugin.addCoins(target.getUniqueId(), amount);
                sender.sendMessage(ChatColor.GREEN + target.getName() + "'in coini " + amount + " yapıldı!");
                break;
            }


            case "get": {
                if (args.length != 2) {
                    sender.sendMessage(ChatColor.RED + "Kullanım: /coin get <oyuncu>");
                    return true;
                }
                OfflinePlayer target = getTarget(args[1], sender);
                if (target == null) return true;

                int coins = plugin.getCoins(target.getUniqueId());
                sender.sendMessage(ChatColor.YELLOW + target.getName() + " → " + ChatColor.GOLD + coins + " coin");
                break;
            }


            case "top": {
                Map<UUID, Integer> allCoins = plugin.getAllCoins();

                if (allCoins == null || allCoins.isEmpty()) {
                    sender.sendMessage(ChatColor.RED + "Henüz kayıtlı oyuncu verisi yok.");
                    return true;
                }

                List<Map.Entry<UUID, Integer>> sorted = new ArrayList<>(allCoins.entrySet());
                sorted.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));

                sender.sendMessage(ChatColor.GOLD + "» " + ChatColor.YELLOW + "En Zengin 10 Oyuncu" + ChatColor.GOLD + " «");

                int limit = Math.min(10, sorted.size());
                for (int i = 0; i < limit; i++) {
                    Map.Entry<UUID, Integer> entry = sorted.get(i);
                    String playerName = Bukkit.getOfflinePlayer(entry.getKey()).getName();
                    if (playerName == null) playerName = "Bilinmeyen";

                    String medal = i == 0 ? "🥇" : i == 1 ? "🥈" : i == 2 ? "🥉" : "#" + (i + 1);

                    sender.sendMessage(
                            ChatColor.YELLOW + medal + " " +
                                    ChatColor.WHITE + playerName +
                                    ChatColor.GRAY + " - " +
                                    ChatColor.GOLD + entry.getValue() + " coin"
                    );
                }
                break;
            }

            default:
                sender.sendMessage(ChatColor.RED + "Geçersiz komut. Kullanım: /coin <give/remove/set/get/top>");
        }

        return true;
    }
}