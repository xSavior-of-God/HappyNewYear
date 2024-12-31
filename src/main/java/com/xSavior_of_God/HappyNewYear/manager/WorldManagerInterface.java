package com.xSavior_of_God.HappyNewYear.manager;

import java.util.List;

public interface WorldManagerInterface {

    void setBlacklist(boolean blacklist);

    boolean getBlacklist();

    void setOnNightEnabled(boolean onEnabled);

    boolean getOnNightEnabled();

    void setAlwaysNightEnabled(boolean anEnabled);

    boolean getAlwaysNightEnabled();

    void setInRealLifeEnabled(boolean irlEnabled);

    boolean getInRealLifeEnabled();

    void setTimezone(String timezone);

    String getTimezone();

    void setMonth(int month);

    int getMonth();

    void setOnNightStarts(String starts);

    String getOnNightStarts();

    void setOnNightEnds(String ends);

    String getOnNightEnds();

    void setWorldsName(List<String> worlds);

    void addWorldsName(String... world);

    List<String> getWorldsName();
}
