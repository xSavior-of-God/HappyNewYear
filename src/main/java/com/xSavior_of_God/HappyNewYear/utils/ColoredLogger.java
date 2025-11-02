package com.xSavior_of_God.HappyNewYear.utils;

import org.bukkit.Bukkit;
import org.fusesource.jansi.AnsiConsole;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Extends the normal Bukkit Logger to write Colors using ANSI escape codes,
 * with Jansi support for Windows terminals.
 * Minecraft color codes (e.g. &a, &c) are mapped to ANSI equivalents.
 */
public class ColoredLogger {

    private static final Logger LOGGER = Bukkit.getLogger();
    private final String prefix;
    private final char colorFormatter;

    private static final String ANSI_RESET = "\u001B[0m";

    private static final Map<Character, String> ANSI_COLOR_MAP = new HashMap<>();

    static {
        // Install Jansi to enable ANSI colors in Windows consoles
        AnsiConsole.systemInstall();

        // Minecraft-style color code to ANSI escape code mappings
        ANSI_COLOR_MAP.put('0', "\u001B[30m"); // Black
        ANSI_COLOR_MAP.put('1', "\u001B[34m"); // Dark Blue
        ANSI_COLOR_MAP.put('2', "\u001B[32m"); // Dark Green
        ANSI_COLOR_MAP.put('3', "\u001B[36m"); // Dark Aqua
        ANSI_COLOR_MAP.put('4', "\u001B[31m"); // Dark Red
        ANSI_COLOR_MAP.put('5', "\u001B[35m"); // Dark Purple
        ANSI_COLOR_MAP.put('6', "\u001B[33m"); // Gold
        ANSI_COLOR_MAP.put('7', "\u001B[37m"); // Gray
        ANSI_COLOR_MAP.put('8', "\u001B[90m"); // Dark Gray
        ANSI_COLOR_MAP.put('9', "\u001B[94m"); // Blue
        ANSI_COLOR_MAP.put('a', "\u001B[92m"); // Green
        ANSI_COLOR_MAP.put('b', "\u001B[96m"); // Aqua
        ANSI_COLOR_MAP.put('c', "\u001B[91m"); // Red
        ANSI_COLOR_MAP.put('d', "\u001B[95m"); // Light Purple
        ANSI_COLOR_MAP.put('e', "\u001B[93m"); // Yellow
        ANSI_COLOR_MAP.put('f', "\u001B[97m"); // White
    }

    public ColoredLogger(String prefix) {
        this(prefix, '&');
    }

    public ColoredLogger(String prefix, char colorFormatter) {
        this.prefix = prefix;
        this.colorFormatter = colorFormatter;
    }

    // Basic log with prefix
    public void log(Level level, String message) {
        LOGGER.log(level, () -> prefix + convertStringMessage(message));
    }

    // Log with optional prefix
    public void log(Level level, String message, boolean usePrefix) {
        LOGGER.log(level, () -> (usePrefix ? prefix : "") + convertStringMessage(message));
    }

    // Log with exception
    public void log(Level level, String message, Throwable e) {
        LOGGER.log(level, prefix + convertStringMessage(message), e);
    }

    // Log using a Supplier
    public void log(Level level, Supplier<String> msgSupplier) {
        LOGGER.log(level, () -> prefix + convertStringMessage(msgSupplier.get()));
    }

    // Convert Minecraft-style color codes (&a, &c, etc.) to ANSI escape sequences
    private String convertStringMessage(String message) {
        if (message == null) return "";
        StringBuilder result = new StringBuilder();
        char[] chars = message.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == colorFormatter && i + 1 < chars.length) {
                char code = Character.toLowerCase(chars[i + 1]);
                String ansi = ANSI_COLOR_MAP.get(code);
                if (ansi != null) {
                    result.append(ansi);
                    i++; // Skip next char (color code)
                    continue;
                }
            }
            result.append(chars[i]);
        }

        result.append(ANSI_RESET); // Reset at end
        return result.toString();
    }

    /**
     * Call this when your plugin disables to clean up Jansi.
     */
    public static void cleanup() {
        AnsiConsole.systemUninstall();
    }
}
