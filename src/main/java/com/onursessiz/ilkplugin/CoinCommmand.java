package com.onursessiz.ilkplugin;


import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;




public class CoinCommmand implements CommandExecutor {

    private boolean yetkiKontrol(CommandSender sender){
        if (!sender.hasPermission("coin.admin")) {
            sender.sendMessage("Yetkin yok.");
            return false;
        }

        return true;
    }

    private OfflinePlayer getTarget(String name, CommandSender sender) {
        OfflinePlayer target = Bukkit.getOfflinePlayer(name);

        if (target.getName() == null) {
            sender.sendMessage("Oyuncu bulunamadı!");
            return null;
        }

        return target;
    }


    private final IlkPlugin plugin;

    public CoinCommmand(IlkPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        OfflinePlayer targetPlayer = null;
        int amount;

        if (args.length == 0) {
            sender.sendMessage("Kullanım: /coin <give/remove/set/get>");
            return true;
        }

        String sub = args[0].toLowerCase();

        switch (sub) {
            case "give":
                if (args.length!=3){
                    sender.sendMessage("Hatalı komut / eksik komut girişi yapıldı !");
                    return true;
                }
                targetPlayer=getTarget(args[1],sender);
                if (targetPlayer == null) return true;

                if(yetkiKontrol(sender)) {

                    try {
                        amount = Integer.parseInt(args[2]);
                    } catch (NumberFormatException e) {
                        sender.sendMessage("Geçerli bir sayı gir.");
                        return true;
                    }
                    if (amount <=0){
                        sender.sendMessage("Eklenecek coin miktarı pozitif bir değer olmalıdır !!");
                        return true;
                    }
                        plugin.addCoins(targetPlayer.getUniqueId(), amount);
                        sender.sendMessage(targetPlayer.getName() + "'e " + amount + " coins gönderildi !!");

                }

                break;

            case "remove":

                if (args.length!=3){
                    sender.sendMessage("Hatalı komut / eksik komut girişi yapıldı !");
                    return true;
                }
                targetPlayer=getTarget(args[1],sender);
                if (targetPlayer == null) return true;
                if(yetkiKontrol(sender)) {
                    try {
                        amount = Integer.parseInt(args[2]);
                    } catch (NumberFormatException e) {
                        sender.sendMessage("Geçerli bir sayı gir.");
                        return true;
                    }
                    if (amount <=0){
                        sender.sendMessage("Azaltılacak coin miktarı pozitif bir değer olmalıdır !!");
                        return true;
                    }
                    plugin.loseCoins(targetPlayer.getUniqueId(), amount );
                    sender.sendMessage(targetPlayer.getName() + "'dan " + amount + " coins yok edildi !!");
                }
                break;

            case "set":
                if (args.length!=3){
                    sender.sendMessage("Hatalı komut / eksik komut girişi yapıldı !");
                    return true;
                }
                targetPlayer=getTarget(args[1],sender);
                if (targetPlayer == null) return true;
                if(yetkiKontrol(sender)){

                        try {
                            amount = Integer.parseInt(args[2]);
                        } catch (NumberFormatException e) {
                            sender.sendMessage("Geçerli bir sayı gir.");
                            return true;
                        }

                    plugin.loseCoins(targetPlayer.getUniqueId(),plugin.getCoins(targetPlayer.getUniqueId()));
                    plugin.addCoins(targetPlayer.getUniqueId(),amount);
                    sender.sendMessage(targetPlayer.getName()+"'in coini "+amount+" yapıldı !");
                }
                break;

            case "get":
                if (args.length!=2){
                    sender.sendMessage("Hatalı komut / eksik komut girişi yapıldı !");
                    return true;
                }
                targetPlayer=getTarget(args[1],sender);
                if (targetPlayer == null) return true;
                sender.sendMessage(targetPlayer.getName()+"'in sahip olduğu coin miktarı : "+plugin.getCoins(targetPlayer.getUniqueId()));
                break;
            default:
                sender.sendMessage("Geçersiz komut.");
        }

        return true;
    }

}
