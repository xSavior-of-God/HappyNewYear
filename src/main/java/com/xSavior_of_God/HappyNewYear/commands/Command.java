package com.xSavior_of_God.HappyNewYear.commands;

import com.xSavior_of_God.HappyNewYear.HappyNewYear;
import com.xSavior_of_God.HappyNewYear.presets.PresetManager;
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
                        HappyNewYear.forceStart = true;
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
                        HappyNewYear.instance.softReload();
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aReloaded!"));
                        return true;
                    }
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou don't have permission to use this command!"));
                    break;
                case "preset":
                    if (sender instanceof ConsoleCommandSender || sender.isOp() || sender.hasPermission("happynewyear.preset") || sender.hasPermission("happynewyear.reload")) {
                        if (args.length < 2) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsage: /" + commandLabel + " preset <name> [confirm]"));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Available presets: " + String.join(", ", PresetManager.getPresetNames())));
                            return true;
                        }
                        String presetName = args[1];
                        if (!PresetManager.presetExists(presetName)) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPreset '" + presetName + "' not found!"));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Available presets: " + String.join(", ", PresetManager.getPresetNames())));
                            return true;
                        }
                        if (args.length < 3 || !args[2].equalsIgnoreCase("confirm")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eAre you sure you want to apply preset '" + presetName + "'?"));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eThis will overwrite your current config.yml and create a backup."));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Run: /" + commandLabel + " preset " + presetName + " confirm"));
                            return true;
                        }
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Creating backup and applying preset '" + presetName + "'..."));
                        if (PresetManager.applyPreset(presetName)) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Reloading..."));
                            HappyNewYear.instance.softReload();
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aPreset '" + presetName + "' applied and reloaded!"));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cFailed to apply preset '" + presetName + "'!"));
                        }
                        return true;
                    }
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou don't have permission to use this command!"));
                    break;
                default:
                    this.sendHelpMessage(sender, commandLabel);
                    break;
            }
        } else {
            this.sendHelpMessage(sender, commandLabel);
        }
        return true;
    }

    public void sendHelpMessage(CommandSender sender, String commandLabel) {
        if (sender instanceof ConsoleCommandSender || sender.isOp() || sender.hasPermission("happynewyear.help")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&lHappyNewYear &7● &7Version " + HappyNewYear.instance.getDescription().getVersion() + " created by xSavior_of_God"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
            boolean hasOnePermission = false;

            if (sender instanceof ConsoleCommandSender || sender.isOp() || sender.hasPermission("happynewyear.forcestart")) {
                hasOnePermission = true;
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f/" + commandLabel + " start   &7Force start the fireworks show"));
            }
            if (sender instanceof ConsoleCommandSender || sender.isOp() || sender.hasPermission("happynewyear.forcestop")) {
                hasOnePermission = true;
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f/" + commandLabel + " stop   &7Force stop the fireworks show"));
            }
            if (sender instanceof ConsoleCommandSender || sender.isOp() || sender.hasPermission("happynewyear.reload")) {
                hasOnePermission = true;
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f/" + commandLabel + " reload   &7Reload plugin configuration"));
            }
            if (sender instanceof ConsoleCommandSender || sender.isOp() || sender.hasPermission("happynewyear.preset") || sender.hasPermission("happynewyear.reload")) {
                hasOnePermission = true;
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f/" + commandLabel + " preset <name> [confirm]   &7Apply a preset and reload"));
            }

            if (!hasOnePermission) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HappyNewYear.instance.messages.get("Prefix") + HappyNewYear.instance.messages.get("Errors.NoPermissionSubCommands")));
            }

            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', HappyNewYear.instance.messages.get("Prefix") + HappyNewYear.instance.messages.get("Errors.NoPermission")));
        }

    }

}
