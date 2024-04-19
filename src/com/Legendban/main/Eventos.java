package com.Legendban.main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPreLoginEvent;

@SuppressWarnings("deprecation")
public class Eventos implements Listener {

	@EventHandler
	public void SamesNicks(PlayerPreLoginEvent e) {
		String name = e.getName();
		if (Comandos.taBanido(name)) {
			e.disallow(org.bukkit.event.player.PlayerPreLoginEvent.Result.KICK_OTHER,
					"[§b§lLegendPvP§f]\n" + "§bVocê está banido, motivo: §f'" + Comandos.getMotivo(name) + "'§b."
							+ "\n§7Compre Unban em: LegendPvP.com.br");
			return;
		}
	}
}
