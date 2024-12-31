package com.xSavior_of_God.HappyNewYear.commands;

import com.xSavior_of_God.HappyNewYear.HappyNewYear;
import com.xSavior_of_God.HappyNewYear.utils.Reload;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class Command implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String commandLabel, String[] args) {
        if (args != null && args.length > 0) {

            switch (args[0].toLowerCase()) {
                case "start":
                    if (sender instanceof ConsoleCommandSender || sender.isOp() || sender.hasPermission("happynewyear.forcestart")) {
                        HappyNewYear.forceStart = !HappyNewYear.forceStart;
                        HappyNewYear.forceStop = false;
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eFireworks successfully started!"));
                        return true;
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou don't have permission to use this command!"));
                    }
                    break;
                case "stop":
                    if (sender instanceof ConsoleCommandSender || sender.isOp() || sender.hasPermission("happynewyear.forcestop")) {
                        HappyNewYear.forceStop = true;
                        HappyNewYear.forceStart = false;
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eFireworks successfully stopped!"));
                        return true;
                    }
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou don't have permission to use this command!"));
                    break;
                case "reload":
                    if (sender instanceof ConsoleCommandSender || sender.isOp() || sender.hasPermission("happynewyear.reload")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Reloading..."));
                        HappyNewYear.instance.reloadConfig();
                        Reload.reload();
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aReloaded!"));
                        return true;
                    }
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou don't have permission to use this command!"));
                    break;
                default:
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUnknown command! Usage: /" + commandLabel + " <" + (
                            sender instanceof ConsoleCommandSender || sender.isOp() || sender.hasPermission("happynewyear.forcestart") ? "start, " : ""
                    ) + (
                            sender instanceof ConsoleCommandSender || sender.isOp() || sender.hasPermission("happynewyear.forcestop") ? "stop, " : ""
                    ) + (
                            sender instanceof ConsoleCommandSender || sender.isOp() || sender.hasPermission("happynewyear.reload") ? "reload" : ""
                    ) + ">"));
                    break;
            }
        }
        return true;
    }

}
