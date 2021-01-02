package com.xSavior_of_God.HappyNewYear;

import java.util.List;

public interface WorldManagerInterface {

  public void setBlacklist(boolean blacklist);

  public boolean getBlacklist();

  public void setOnlyNightEnabled(boolean onEnabled);

  public boolean getOnlyNightEnabled();

  public void setInRealLifeEnabled(boolean irlEnabled);

  public boolean getInRealLifeEnabled();

  public void setTimezone(String timezone);

  public String getTimezone();

  public void setOnlyNightStarts(String starts);

  public String getOnlyNightStarts();

  public void setOnlyNightEnds(String ends);

  public String getOnlyNightEnds();

  public void setWorldsName(List<String> worlds);

  public void addWorldsName(String... world);

  public List<String> getWorldsName();
}
