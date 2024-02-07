package net.mpoisv.unicode;

import ch.njol.skript.Skript;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static int MaxUnicodeLength = 20;
    private static String BlockMessageFormat;
    private static Main instance;
    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        MaxUnicodeLength = getConfig().getInt("max_unicode_length");
        BlockMessageFormat = getConfig().getString("block_message", "%message% over than %max unicode%.");
        Bukkit.getPluginManager().registerEvents(new EventManager(), this);
        registerAddon();

        VersionChecker.getLatestVersionFromServer();

        Bukkit.getConsoleSender().sendMessage(String.format("§b:§r %s §b:§r Plugin Loading finished. Current version: %s.", getDescription().getName(), getDescription().getVersion()));
        if(!VersionChecker.isLatestVersion(getDescription().getVersion())) {
            Bukkit.getConsoleSender().sendMessage(String.format("§b:§r %s §b:§e Latest version: %s. Update please.", getDescription().getName(), VersionChecker.getVersionCode()));
            Bukkit.getConsoleSender().sendMessage(String.format("§b:§r %s §b:§e https://www.spigotmc.org/resources/passwordlocker.113386/", getDescription().getName()));
        }
    }

    public static Main getInstance() {
        return instance;
    }

    public static int getMaxUnicodeLength() { return MaxUnicodeLength; }

    public static boolean detectOverflowUnicodeLength(String message) {
        int count = 0;
        for(char c : message.toCharArray()) {
            if(c <= 31 || c >= 127) {
                count++;
                if (count > MaxUnicodeLength) break;
            }
        }
        return count > MaxUnicodeLength;
    }

    public static String detectOverflowFormat(String message) {
        return BlockMessageFormat.replaceAll("%message%", message).replaceAll("%max unicode%", String.valueOf(MaxUnicodeLength));
    }

    public void registerAddon() {
        if(getServer().getPluginManager().getPlugin("Skript") != null) {
            try {
                var addon = Skript.registerAddon(this);
                addon.loadClasses("net.mpoisv.unicode.skript", "expression", "condition");
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}