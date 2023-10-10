package com.xSavior_of_God.HeroxWar.HappyNewYear.manager;

import java.util.Arrays;
import java.util.List;

public class WorldManager implements WorldManagerInterface {
    private boolean blacklist,
            onEnabled,
            irlEnabled,

    anEnabled;
    private int month;
    private String timezone,
            starts,
            ends;
    private List<String> worlds;

    @Override
    public void setBlacklist(boolean blacklist) {
        this.blacklist = blacklist;
    }

    @Override
    public boolean getBlacklist() {
        return blacklist;
    }

    @Override
    public void setOnlyNightEnabled(boolean onEnabled) {
        this.onEnabled = onEnabled;
    }

    @Override
    public boolean getOnlyNightEnabled() {
        return onEnabled;
    }

    @Override
    public void setAlwaysNightEnabled(boolean anEnabled) {
        this.anEnabled = anEnabled;
    }

    @Override
    public boolean getAlwaysNightEnabled() {
        return anEnabled;
    }

    @Override
    public void setInRealLifeEnabled(boolean irlEnabled) {
        this.irlEnabled = irlEnabled;
    }

    @Override
    public boolean getInRealLifeEnabled() {
        return irlEnabled;
    }

    @Override
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @Override
    public String getTimezone() {
        return timezone;
    }

    @Override
    public int getMonth() {
        return month;
    }

    @Override
    public void setMonth(int month) {
        this.month = month;
    }

    @Override
    public void setOnlyNightStarts(String starts) {
        this.starts = starts;
    }

    @Override
    public String getOnlyNightStarts() {
        return starts;
    }

    @Override
    public void setOnlyNightEnds(String ends) {
        this.ends = ends;
    }

    @Override
    public String getOnlyNightEnds() {
        return ends;
    }

    @Override
    public void setWorldsName(List<String> worlds) {
        this.worlds = worlds;
    }

    @Override
    public void addWorldsName(String... world) {
        this.worlds.addAll(Arrays.asList(world));
    }

    @Override
    public List<String> getWorldsName() {
        return worlds;
    }

}
