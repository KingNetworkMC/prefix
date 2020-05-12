package ru.alexeylesin.kingprefix;

import java.util.List;
import net.md_5.bungee.chat.ComponentSerializer;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CPrefix implements CommandExecutor {
  public CPrefix() {
    Bukkit.getPluginCommand("prefix").setExecutor(this);
  }
  
  public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {
    String prefix, pref;
    List<String> list;
    int limit;
    List<String> list1, prefixs;
    int i, it;
    List<String> pre;
    String ss;
    List<String> stringList;
    int j;
    List<String> prefixss;
    String frf;
    if (!(sender instanceof Player)) {
      sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Префиксы &8| &fКоманда доступна только игрокам."));
      return true;
    } 
    Player p = (Player)sender;
    if (!sender.hasPermission("kingprefix.use")) {
      Util.sendPlayerMessage((CommandSender)p, "messages.no-perm");
      return true;
    } 
    if (args.length == 0) {
      Util.sendPlayerMessage((CommandSender)p, "messages.prefix-help");
      return true;
    } 
    switch (args[0]) {
      case "set":
        if (!p.hasPermission("kingprefix.command.set")) {
          Util.sendPlayerMessage((CommandSender)p, "messages.no-perm");
          return true;
        } 
        if (args.length < 2) {
          Util.sendPlayerMessage((CommandSender)p, "messages.prefix-help");
          return true;
        } 
        prefix = StringUtils.join((Object[])args, " ", 1, args.length);
        if (prefix.length() > 16) {
          Util.sendPlayerMessage((CommandSender)p, "messages.prefix-size");
          return true;
        } 
        if (!Util.checkPrefix(p, prefix)) {
          Util.sendPlayerMessage((CommandSender)p, "messages.prefix-format");
          return true;
        } 
        p.sendMessage(Util.replacePlaceholder((String[])Main.cnfg.getStringList("messages.successful-change").toArray(
                (Object[])new String[Main.cnfg.getStringList("messages.successful-change").size()]), new String[] { "prefix" }, new String[] { prefix }));
        Util.setPlayerPrefix(p, prefix);
        return true;
      case "reset":
        if (!p.hasPermission("kingprefix.command.reset")) {
          Util.sendPlayerMessage((CommandSender)p, "messages.no-perm");
          return true;
        } 
        Util.sendPlayerMessage((CommandSender)p, "messages.prefix-reset");
        Util.clearPrefix(p);
        return true;
      case "on":
        if (!p.hasPermission("kingprefix.command.on")) {
          Util.sendPlayerMessage((CommandSender)p, "messages.no-perm");
          return true;
        } 
        Util.sendPlayerMessage((CommandSender)p, "messages.prefix-on");
        Util.onOffPrefix(p, true);
        return true;
      case "off":
        if (!p.hasPermission("kingprefix.command.off")) {
          Util.sendPlayerMessage((CommandSender)p, "messages.no-perm");
          return true;
        } 
        Util.sendPlayerMessage((CommandSender)p, "messages.prefix-off");
        Util.onOffPrefix(p, false);
        return true;
      case "save":
        if (!p.hasPermission("kingprefix.command.save")) {
          Util.sendPlayerMessage((CommandSender)p, "messages.no-perm");
          return true;
        } 
        if (args.length < 2) {
          Util.sendPlayerMessage((CommandSender)p, "messages.prefix-help");
          return true;
        } 
        pref = StringUtils.join((Object[])args, " ", 1, args.length);
        if (pref.length() > 16) {
          Util.sendPlayerMessage((CommandSender)p, "messages.prefix-size");
          return true;
        } 
        if (!Util.checkPrefix(p, pref)) {
          Util.sendPlayerMessage((CommandSender)p, "messages.prefix-format");
          return true;
        } 
        list = Main.data.getStringList("prefixs." + p.getName() + ".saves");
        limit = Util.getPlayerLmit(p);
        if (list.size() >= limit && limit >= 0) {
          Util.sendPlayerMessage((CommandSender)p, "messages.prefix-limit");
          return true;
        } 
        list.add(pref);
        Main.data.set("prefixs." + p.getName() + ".saves", list);
        Main.reloadDataFile();
        list1 = Main.cnfg.getStringList("messages.prefix-save");
        p.sendMessage(Util.replacePlaceholder(list1.<String>toArray(new String[list1.size()]), new String[] { "prefix", "n" }, new String[] { pref, String.valueOf(list.size()) }));
        return true;
      case "list":
        if (!p.hasPermission("kingprefix.command.list")) {
          Util.sendPlayerMessage((CommandSender)p, "messages.no-perm");
          return true;
        } 
        Util.sendPlayerMessage((CommandSender)p, "messages.prefix-list.header");
        prefixs = Main.data.getStringList("prefixs." + p.getName() + ".saves");
        for (i = 0; i < prefixs.size(); i++) {
          String gg = "{\"text\":\"" + Main.cnfg.getString("messages.prefix-list.foreach").replace("{prefix}", prefixs.get(i)).replace("{n}", String.valueOf(i + 1)).replace('&', '§') + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/prefix load " + String.valueOf(i + 1) + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + Main.cnfg.getString("messages.prefix-list.hover").replace('&', '§').replace("{n}", String.valueOf(i + 1)) + "\"}}";
          p.spigot().sendMessage(
              ComponentSerializer.parse(gg));
        } 
        return true;
      case "load":
        if (!p.hasPermission("kingprefix.command.load")) {
          Util.sendPlayerMessage((CommandSender)p, "messages.no-perm");
          return true;
        } 
        if (args.length < 2) {
          Util.sendPlayerMessage((CommandSender)p, "messages.prefix-help");
          return true;
        } 
        it = -1;
        pre = Main.data.getStringList("prefixs." + p.getName() + ".saves");
        try {
          it += Util.getIn(args[1]);
        } catch (NumberFormatException e) {
          Util.sendPlayerMessage((CommandSender)p, "messages.prefix-not-found");
          return true;
        } 
        if (it + 1 > pre.size() || it + 1 < 0) {
          Util.sendPlayerMessage((CommandSender)p, "messages.prefix-not-found");
          return true;
        } 
        ss = pre.get(it);
        Util.setPlayerPrefix(p, ss);
        stringList = Main.cnfg.getStringList("messages.prefix-load");
        p.sendMessage(Util.replacePlaceholder(stringList.<String>toArray(new String[stringList.size()]), new String[] { "prefix", "n" }, new String[] { ss, String.valueOf(it + 1) }));
        return true;
      case "delet":
      case "del":
      case "remove":
        if (!p.hasPermission("kingprefix.command.remove")) {
          Util.sendPlayerMessage((CommandSender)p, "messages.no-perm");
          return true;
        } 
        if (args.length < 2) {
          Util.sendPlayerMessage((CommandSender)p, "messages.prefix-help");
          return true;
        } 
        j = -1;
        prefixss = Main.data.getStringList("prefixs." + p.getName() + ".saves");
        try {
          j += Util.getIn(args[1]);
        } catch (NumberFormatException e) {
          Util.sendPlayerMessage((CommandSender)p, "messages.prefix-not-found");
          return true;
        } 
        if (j + 1 > prefixss.size() || j + 1 < 0) {
          Util.sendPlayerMessage((CommandSender)p, "messages.prefix-not-found");
          return true;
        } 
        prefixss.remove(prefixss.get(j));
        Main.data.set("prefixs." + p.getName() + ".saves", prefixss);
        Main.reloadDataFile();
        Util.sendPlayerMessage((CommandSender)p, "messages.prefix-deleted");
        return true;
      case "help":
        if (!p.hasPermission("kingprefix.command.help")) {
          Util.sendPlayerMessage((CommandSender)p, "messages.no-perm");
          return true;
        } 
        Util.sendPlayerMessage((CommandSender)p, "messages.prefix-help");
        return true;
      case "info":
        if (!p.hasPermission("kingprefix.command.info")) {
          Util.sendPlayerMessage((CommandSender)p, "messages.no-perm");
          return true;
        } 
        if (Main.data.getString("prefixs." + p.getName() + ".cut").equals(Main.cnfg.getString("prefix.off.tab"))) {
          frf = "&cвыключен";
        } else if (Util.getDefPrefix(p).equals(Util.getPlayerPrefix(p.getName()))) {
          frf = "&7стандартный";
        } else {
          frf = Util.getPlayerPrefix(p.getName());
        } 
        p.sendMessage(
            Util.replacePlaceholder((String[])Main.cnfg
              .getStringList("messages.prefix-info").toArray((Object[])new String[Main.cnfg.getStringList("messages.prefix-info").size()]), new String[] { "prefix", "n", "max" }, new String[] { frf, 
                
                String.valueOf(Main.data
                  .getStringList("prefixs." + p.getName() + ".saves").size()), 
                String.valueOf(Util.getPlayerLmit(p)) }));
        return true;
    } 
    return true;
  }
}
