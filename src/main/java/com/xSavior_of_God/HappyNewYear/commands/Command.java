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
            if ((sender instanceof ConsoleCommandSender || sender.isOp() || sender.hasPermission("happynewyear.forcestart")) && args[0].equalsIgnoreCase("start")) {
                HappyNewYear.ForceStart = !HappyNewYear.ForceStart;
                HappyNewYear.ForceStop = false;
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eFireworks successfully started!"));
                return true;
            }
            if ((sender instanceof ConsoleCommandSender || sender.isOp() || sender.hasPermission("happynewyear.forcestop")) && args[0].equalsIgnoreCase("stop")) {
                HappyNewYear.ForceStop = true;
                HappyNewYear.ForceStart = false;
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eFireworks successfully stopped!"));
                return true;
            }
            if ((sender instanceof ConsoleCommandSender || sender.isOp() || sender.hasPermission("happynewyear.reload")) && args[0].equalsIgnoreCase("reload")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Reloading..."));
                HappyNewYear.instance.reloadConfig();
                Reload.reload();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aReloaded!"));
                return true;
            }
        }

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUnknown command! Usage: /"+commandLabel+" <start|stop|reload>"));
        return true;
    }

}
