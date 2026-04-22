package com.onursessiz.ilkplugin;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CoinCommmand implements CommandExecutor {

    private final IlkPlugin plugin;

    public CoinCommmand(IlkPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){

        Player targetPlayer = null;
        if (args.length == 0){
            if (sender instanceof Player){
                targetPlayer = (Player) sender;
                targetPlayer.sendMessage("Sahip olduğunuz coin : "+plugin.getCoins(targetPlayer.getUniqueId()));
            }
            else {
                sender.sendMessage("Bu komudu kullanabilmeniz için önce bir oyuncu olmanız gerekiyor ...");
            }
        }
        else if (args.length == 1){

            if (args[0].trim().toLowerCase().equals("top")){
                UUID topUUID = plugin.getTopPlayer();

                if (topUUID == null) {
                    sender.sendMessage("Coini kayıtlı oyuncu yok");
                    return true;
                }

                OfflinePlayer topPlayer = Bukkit.getOfflinePlayer(topUUID);

                sender.sendMessage(
                        "En zengin oyuncu: " + topPlayer.getName() +
                                " | Coin: " + plugin.getCoins(topUUID)
                );
            }
            else{

                targetPlayer = Bukkit.getPlayer(args[0]);
                if (targetPlayer != null){

                    sender.sendMessage(targetPlayer.getName()+"'nın sahip olduğu coin : "+plugin.getCoins(targetPlayer.getUniqueId()));
                }
                else {
                    sender.sendMessage("Bu isimde bir oyuncu bulunamadı.");
                }
            }
            }


        return true;
    }

}
