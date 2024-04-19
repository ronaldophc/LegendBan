package com.Legendban.main;

import java.util.Date;
import java.net.InetSocketAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Comandos implements CommandExecutor {

	public static String prefix = "§f[§b§lLegendPvP§f] >> ";

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
		CommandSender p = sender;
		if (cmd.getName().equalsIgnoreCase("ban")) {
			if (p.hasPermission("legend.ban.ban")) {
				if (args.length == 0) {
					p.sendMessage(prefix + "§bUse §f/Ban <player> <motivo>§b.");
				} else if (args.length == 1) {
					p.sendMessage(prefix + "§bUse §f/Ban <player> <motivo>§b.");
				} else if (args.length > 1) {
					String t = args[0];
					if (t == null) {
						p.sendMessage(prefix + "§bO jogador está offline!");
						return true;
					}
					if (taBanido(t) == true) {
						p.sendMessage(prefix + "§7O Jogador §f" + t + " §7já está §cBanido§7.");
						return true;
					}
					if (t.equalsIgnoreCase("RonaldoPHC")) {
						p.sendMessage(prefix + "§7O Jogador §f" + t + " §7tem §cOP§7.");
						return true;
					}
					String mot = "";
					for (int i = 1; i < args.length; i++) {
						mot = String.valueOf(mot) + args[i] + " ";
					}
					Player a = Bukkit.getServer().getPlayer(args[0]);
					if (a == null || !(a instanceof Player)) {
						if (!(sender instanceof Player)) {
							setarBan("Sender", t, mot, "Banido Offline", true);
						} else {
							Player b = (Player) sender;
							setarBan(b.getName(), t, mot, "Banido Offline", true);
						}
						Bukkit.broadcastMessage(
								"§b§l " + t + " foi banido por §c" + p.getName() + " §bpelo motivo §f'" + mot + "'§b.");
						return true;
					} else {
						if (!(sender instanceof Player)) {
							setarBan("Sender", a, mot, a.getAddress(), true);
						} else {
							Player b = (Player) sender;
							setarBan(b.getName(), a, mot, a.getAddress(), true);
						}
						a.kickPlayer("[§b§lLegendPvP§f]\n" + " §bVocê foi banido por §c" + p.getName()
								+ " §bpelo motivo §f'" + mot + "'§b." + "\n§7Compre Unban em: LegendPvP.com.br");
						Bukkit.broadcastMessage("§b§l " + a.getName() + " foi banido por §c" + p.getName()
								+ " §bpelo motivo §f'" + mot + "'§b.");
					}
				}
			}

		} else if (cmd.getName().equalsIgnoreCase("unban")) {
			if (p.hasPermission("legend.ban.unban")) {
				if (args.length == 0) {
					p.sendMessage(prefix + "§bUse §f/UnBan <player>§b.");
				} else if (args.length == 1) {
					String t = args[0];
					if (jafoiBanido(t) == false) {
						p.sendMessage(prefix + "§7O Jogador §f" + t + " §7nunca foi §cBanido§7.");
						return true;
					}
					if (taBanido(t) == false) {
						p.sendMessage(prefix + "§7O Jogador §f" + t + " §7não está §cBanido§7.");
						return true;
					}
					tirarBan(t, false);
					p.sendMessage(prefix + "§bVocê tirou o ban do jogador §f" + t + "§b.");
				}
			}
		} else if (cmd.getName().equalsIgnoreCase("infoban")) {
			if (p.hasPermission("legend.ban.info")) {
				if (args.length == 0) {
					p.sendMessage(prefix + "§bUse §f/infoBan <player>§b.");
				} else if (args.length == 1) {
					String t = args[0];
					if (jafoiBanido(t)) {
						p.sendMessage("§f--------§bINFO BANS§f---------");
						p.sendMessage("§f| §fPlayer: §7" + t);
						p.sendMessage("§f| §fIP: §7" + getIP(t));
						p.sendMessage("§f| §fMotivo: §7" + getMotivo(t));
						p.sendMessage("§f| §fBanido: §7" + taBanidoString(t));
						p.sendMessage("§f| §fQuem baniu: §7" + getQuem(t));
						p.sendMessage("§f| §fHorario: §7" + getData(t));
						p.sendMessage("§f-------§bLegendPvP§f-------");
					} else {
						p.sendMessage(prefix + "§7Esse jogador nunca foi §cBanido§7.");
					}
				}
			}
		}
		return false;
	}

	public static String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static void setarBan(String a, Player p, String motivo, InetSocketAddress ip, boolean banido) {
		Main.bans.getConfig().set(String.valueOf(p.getName()) + ".UUID", p.getUniqueId().toString());
		Main.bans.getConfig().set(String.valueOf(p.getName()) + ".Ip", String.valueOf(ip));
		Main.bans.getConfig().set(String.valueOf(p.getName()) + ".Motivo", String.valueOf(motivo));
		Main.bans.getConfig().set(String.valueOf(p.getName()) + ".Banido", Boolean.valueOf(banido));
		Main.bans.getConfig().set(String.valueOf(p.getName()) + ".Quem", String.valueOf(a));
		Main.bans.getConfig().set(String.valueOf(p.getName()) + ".Horario", String.valueOf(getDateTime()));
		Main.bans.save();
	}

	public static void setarBan(String a, String p, String motivo, String ip, boolean banido) {
		Main.bans.getConfig().set(String.valueOf(p) + ".UUID", String.valueOf(ip));
		Main.bans.getConfig().set(String.valueOf(p) + ".Ip", String.valueOf(ip));
		Main.bans.getConfig().set(String.valueOf(p) + ".Motivo", String.valueOf(motivo));
		Main.bans.getConfig().set(String.valueOf(p) + ".Banido", Boolean.valueOf(banido));
		Main.bans.getConfig().set(String.valueOf(p) + ".Quem", String.valueOf(a));
		Main.bans.getConfig().set(String.valueOf(p) + ".Horario", String.valueOf(getDateTime()));
		Main.bans.save();
	}

	public static void tirarBan(String p, boolean banido) {
		Main.bans.getConfig().set(String.valueOf(p) + ".Banido", Boolean.valueOf(banido));
		Main.bans.save();
	}

	public static String getMotivo(String p) {
		return Main.bans.getConfig().getString(String.valueOf(p) + ".Motivo");
	}

	public static String getQuem(String p) {
		return Main.bans.getConfig().getString(String.valueOf(p) + ".Quem");
	}

	public static String getData(String p) {
		return Main.bans.getConfig().getString(String.valueOf(p) + ".Horario");
	}

	public static String getIP(String p) {
		return Main.bans.getConfig().getString(String.valueOf(p) + ".Ip");
	}

	public static String taBanidoString(String p) {
		if (jafoiBanido(p)) {
			if (taBanido(p)) {
				return "§7Está §cBanido§7.";
			} else {
				return "§7Está §cDesbanido§7.";
			}
		} else {
			return "§7Nunca foi §cBanido§7.";
		}
	}

	public static boolean taBanido(String p) {
		if (Main.bans.getConfig().getString(String.valueOf(p) + ".UUID") == null) {
			return false;
		} else if (Main.bans.getConfig().getBoolean(String.valueOf(p) + ".Banido") == false) {
			return false;
		}
		return true;
	}

	public static boolean jafoiBanido(String p) {
		if (Main.bans.getConfig().getString(String.valueOf(p) + ".UUID") == null) {
			return false;
		} else if (Main.bans.getConfig().getBoolean(String.valueOf(p) + ".Banido") == false) {
			return true;
		}
		return true;
	}

}
