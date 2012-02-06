package net.Zelif.DeathTimeout;

import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


public class DeathTimeout extends JavaPlugin{
	Logger log = Logger.getLogger("Minecraft");

	public  void onEnable(){
		final FileConfiguration config = this.getConfig();
		config.addDefault("SecondsBanned", 300);
		new DeathTListener(this);
		config.options().copyDefaults(true);
		saveConfig();
	}

	public void onDisable(){
		reloadConfig();
	}
		
}
