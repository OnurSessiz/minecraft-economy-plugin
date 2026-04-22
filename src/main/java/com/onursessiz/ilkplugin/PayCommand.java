package com.onursessiz.ilkplugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand implements CommandExecutor {

    private final IlkPlugin plugin;

    public PayCommand(IlkPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player targetPlayer = null;
        Player donater = null;
        int amount;
        if (args.length == 2) {
            if (sender instanceof Player) {
                targetPlayer = Bukkit.getPlayer(args[0]);
                donater = (Player) sender;

                if (targetPlayer == null) {
                    donater.sendMessage("Oyuncu bulunamadı babuş");
                } else {
                    if (targetPlayer.getUniqueId().equals(donater.getUniqueId())) {
                        donater.sendMessage("Kendine coin gönderemezsin !!");
                    } else {
                        try {
                            amount = Integer.parseInt(args[1]);
                            if (amount <= plugin.getCoins(donater.getUniqueId()) && amount >= 1) {
                                plugin.addCoins(targetPlayer.getUniqueId(), amount);
                                plugin.loseCoins(donater.getUniqueId(), amount);
                                targetPlayer.sendMessage(donater.getName() + " sana tam olarak " + amount + " coin gönderdi !");
                                donater.sendMessage(targetPlayer.getName() + "'ya tam olarak " + amount + " coin gönderildi !");
                            } else {
                                donater.sendMessage("Hedefe göndermek istediğin miktar senin parandan fazla olamaz ya da 1 den küçük olamaz !");
                            }
                        } catch (NumberFormatException e) {
                            donater.sendMessage("Geçersiz giriş yaptınız miktar sayı olmalıdır !");
                        }

                    }
                }
            }
            else {
                sender.sendMessage("Bu komudu kullanabilmeniz için önce bir oyuncu olmanız gerekiyor ...");
            }
        }

        return true;
    }
}
