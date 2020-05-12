package ru.alexeylesin.kingprefix;

import com.gmail.filoghost.coloredtags.ColoredTags;
import com.gmail.filoghost.coloredtags.ScoreboardHandler;
import com.gmail.filoghost.coloredtags.TeamData;
import java.util.List;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Util {
  public static void setPlayerPrefix(Player player, String s) {
    ((Chat)Bukkit.getServicesManager().getRegistration(Chat.class).getProvider()).setPlayerPrefix(player, s + " ");
    ColoredTags.playersConfig.set(player.getName(), s);
    ColoredTags.playersConfig.trySave();
    TeamData data = TeamData.fromString(s);
    ColoredTags.playersMap.put(player.getName().toLowerCase(), data);
    Player onlinePlayer = Bukkit.getPlayerExact(player.getName());
    if (onlinePlayer != null)
      ScoreboardHandler.setPrefixSuffix(onlinePlayer, data); 
    savePlayerPrefix(player.getName(), s);
  }
  
  public static void onOffPrefix(Player p, boolean onoff) {
    if (onoff) {
      setPlayerPrefix(p, Main.data.getString("prefixs." + p.getName() + ".on"));
      Main.data.set("prefixs." + p.getName() + ".on", null);
    } else {
      Main.data.set("prefixs." + p.getName() + ".on", getPlayerPrefix(p.getName()));
      setPlayerPrefix(p, Main.cnfg.getString("prefix.off.tab"));
      ((Chat)Bukkit.getServicesManager().getRegistration(Chat.class).getProvider()).setPlayerPrefix(p, Main.cnfg
          
          .getString("prefix.off.chat"));
    } 
    Main.reloadDataFile();
  }
  
  public static void clearPrefix(Player p) {
    String pref = getDefPrefix(p);
    setPlayerPrefix(p, pref);
    String s = ((Chat)Bukkit.getServicesManager().getRegistration(Chat.class).getProvider()).getGroupSuffix(p
        .getWorld(), (
        (Permission)Bukkit.getServer().getServicesManager().getRegistration(Permission.class).getProvider()).getPrimaryGroup(p));
    ((Chat)Bukkit.getServicesManager().getRegistration(Chat.class).getProvider()).setPlayerSuffix(p, s);
  }
  
  public static void savePlayerPrefix(String p, String prx) {
    Main.data.set("prefixs." + p + ".cut", prx);
    Main.reloadDataFile();
  }
  
  public static void saveDeffPlayerPrefix(String p, String prx) {
    Main.data.set("prefixs." + p + ".def", prx);
    Main.reloadDataFile();
  }
  
  public static String getPlayerPrefix(String p) {
    return Main.data.getString("prefixs." + p + ".cut", "");
  }
  
  public static String getDefPrefix(Player p) {
    String pref = Main.data.getString("prefixs." + p.getName() + ".def", ColoredTags.groupsConfig
        .getString((
          (Permission)Bukkit.getServer().getServicesManager().getRegistration(Permission.class).getProvider()).getPrimaryGroup(p)));
    return pref;
  }
  
  public static String[] getColored(String[] s) {
    for (int i = 0; i < s.length; i++)
      s[i] = ChatColor.translateAlternateColorCodes('&', s[i]); 
    return s;
  }
  
  public static void sendPlayerMessage(CommandSender p, String part) {
    List<String> stringList = Main.cnfg.getStringList(part);
    p.sendMessage(getColored(stringList.<String>toArray(new String[0])));
  }
  
  public static String[] replacePlaceholder(String[] in, String[] placeholder, String[] values) {
    if (placeholder.length == values.length)
      for (int i = 0; i < in.length; i++) {
        for (int ii = 0; ii < placeholder.length; ii++)
          in[i] = in[i].replace("{" + placeholder[ii] + "}", values[ii]); 
      }  
    return getColored(in);
  }
  
  public static int getPlayerLmit(Player p) {
    String group = ((Permission)Bukkit.getServer().getServicesManager().getRegistration(Permission.class).getProvider()).getPrimaryGroup(p);
    return Main.cnfg.getInt("prefix-limit." + group, 0);
  }
  
  public static int getIn(String s) throws NumberFormatException {
    return Integer.valueOf(s.replace("#", "")).intValue();
  }
  
  public static boolean checkPrefix(Player p, String prefix) {
    if (!p.hasPermission("kingprefix.format.l") && (prefix.contains("&l") || prefix.contains("&L")))
      return false; 
    if (!p.hasPermission("kingprefix.format.o") && (prefix.contains("&o") || prefix.contains("&O")))
      return false; 
    if (!p.hasPermission("kingprefix.format.m") && (prefix.contains("&m") || prefix.contains("&M")))
      return false; 
    if (!p.hasPermission("kingprefix.format.n") && (prefix.contains("&n") || prefix.contains("&N")))
      return false; 
    if (!p.hasPermission("kingprefix.format.k") && (prefix.contains("&k") || prefix.contains("&K")))
      return false; 
    return true;
  }
}
