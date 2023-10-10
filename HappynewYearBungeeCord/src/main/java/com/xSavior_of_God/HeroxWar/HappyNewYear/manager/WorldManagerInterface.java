package com.xSavior_of_God.HeroxWar.HappyNewYear.manager;

import java.util.List;

public interface WorldManagerInterface {

    void setBlacklist(boolean blacklist);

    boolean getBlacklist();

    void setOnlyNightEnabled(boolean onEnabled);

    boolean getOnlyNightEnabled();

    void setAlwaysNightEnabled(boolean anEnabled);

    boolean getAlwaysNightEnabled();

    void setInRealLifeEnabled(boolean irlEnabled);

    boolean getInRealLifeEnabled();

    void setTimezone(String timezone);

    String getTimezone();

    void setMonth(int month);

    int getMonth();

    void setOnlyNightStarts(String starts);

    String getOnlyNightStarts();

    void setOnlyNightEnds(String ends);

    String getOnlyNightEnds();

    void setWorldsName(List<String> worlds);

    void addWorldsName(String... world);

    List<String> getWorldsName();
}
