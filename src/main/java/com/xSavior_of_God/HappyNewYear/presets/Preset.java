package com.xSavior_of_God.HappyNewYear.presets;

import com.xSavior_of_God.HappyNewYear.utils.CommentedConfiguration;

public interface Preset {
    String getName();
    void apply(CommentedConfiguration cfg);
}
