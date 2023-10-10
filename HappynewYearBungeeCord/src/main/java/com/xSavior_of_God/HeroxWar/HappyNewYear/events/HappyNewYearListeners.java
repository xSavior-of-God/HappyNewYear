package com.xSavior_of_God.HeroxWar.HappyNewYear.events;

import com.xSavior_of_God.HeroxWar.HappyNewYear.HappyNewYear;
import com.xSavior_of_God.HeroxWar.HappyNewYear.utils.Utils;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class HappyNewYearListeners implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(net.md_5.bungee.api.event.PostLoginEvent event) {
        // Notify the player that the plugin is disabled
        if (!HappyNewYear.Enabled && (event.getPlayer().hasPermission("happynewyear.op"))) {
            Utils.sendMessage(event.getPlayer(), "&e[HappyNewYear] &4&lHEY! &cDon't forget to enable the plugin in the config.yml if you want to use it!");
        }
    }

}
