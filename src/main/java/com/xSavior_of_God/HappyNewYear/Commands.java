package com.xSavior_of_God.HappyNewYear;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;


public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (args != null && args.length > 0) {
            if ((sender instanceof ConsoleCommandSender || sender.isOp() || sender.hasPermission("happynewyear.forcestart")) && ( args[0].equalsIgnoreCase("toggle") || args[0].equalsIgnoreCase("start") )) {
                Main.ForceStart = !Main.ForceStart;
                Main.ForceStop = false;
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eFireworks successfully toggled!"));
                return true;
            }
            if ((sender instanceof ConsoleCommandSender || sender.isOp() || sender.hasPermission("happynewyear.forcestop")) && args[0].equalsIgnoreCase("stop")) {
                Main.ForceStop = true;
                Main.ForceStart = false;
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eFireworks successfully stopped!"));
                return true;
            }
        }

        if (sender instanceof ConsoleCommandSender || sender.isOp() || sender.hasPermission("happynewyear.reload")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Reloading..."));
            Main.instance.reloadConfig();
            ReloadUtil.reload();
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aReloaded!"));
            return true;
        }
        return true;
    }

}
