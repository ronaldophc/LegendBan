package com.Legendban.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private static Main main;

	public static Config bans;
	public static Config bansip;

	public static Main getInstance() {
		return main;
	}

	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("§b| LegendBans Iniciou com sucesso |");
		main = this;
		bans = new Config(null, "bans.yml", true);
		bans.save();
		bans.reload();
		bansip = new Config(null, "bansip.yml", true);
		bansip.save();
		bansip.reload();
		registro();
		RegistroComandos();
	}

	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("§b| LegendBans desligou com sucesso |");
	}

	public void registro() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new Eventos(), this);
	}

	public void RegistroComandos() {
		this.getCommand("ban").setExecutor(new Comandos());
		this.getCommand("unban").setExecutor(new Comandos());
		this.getCommand("infoban").setExecutor(new Comandos());
	}
}
