package com.xSavior_of_God.HappyNewYear.events;

import com.xSavior_of_God.HappyNewYear.HappyNewYear;
import com.xSavior_of_God.HappyNewYear.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class HappyNewYearListeners implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(org.bukkit.event.player.PlayerJoinEvent event) {
        // Notify the player that the plugin is disabled
        if (!HappyNewYear.enabled && (event.getPlayer().isOp() || event.getPlayer().hasPermission("happynewyear.reload"))) {
            Utils.sendMessage(event.getPlayer(), "&e[HappyNewYear] &4&lHEY! &cDon't forget to enable the plugin in the config.yml if you want to use it!");
        }
    }

}
