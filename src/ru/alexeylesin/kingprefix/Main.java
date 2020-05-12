package ru.alexeylesin.kingprefix;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
  private static Main inst;
  
  public static FileConfiguration cnfg;
  
  public static FileConfiguration data;
  
  public void onEnable() {
    inst = this;
    Bukkit.getPluginManager().registerEvents(this, (Plugin)this);
    if (Bukkit.getPluginManager().getPlugin("ColoredTags") == null) {
      getLogger().warning("Плагин ColoredTags не найден!");
      getLogger().warning("Выключаю плагин...");
      setEnabled(false);
    } 
    load();
    new CPrefix();
  }
  
  public void load() {
    saveDefaultConfig();
    reloadConfig();
    cnfg = getConfig();
    File f = new File(getDataFolder(), "data.yml");
    if (!f.exists())
      saveResource("data.yml", false); 
    data = (FileConfiguration)YamlConfiguration.loadConfiguration(f);
  }
  
  public static void reloadDataFile() {
    File f = new File(get().getDataFolder(), "data.yml");
    if (!f.exists()) {
      get().saveResource("data.yml", false);
    } else {
      try {
        data.save(f);
      } catch (Exception exception) {}
    } 
    data = (FileConfiguration)YamlConfiguration.loadConfiguration(f);
  }
  
  public void onDisable() {
    inst = null;
  }
  
  public static Main get() {
    return inst;
  }
}
