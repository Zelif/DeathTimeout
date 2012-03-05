package net.Zelif.DeathTimeout;

import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class DeathTimeout extends JavaPlugin
{
	Logger log = Logger.getLogger("Minecraft");

	public  void onEnable()
	{
		final FileConfiguration config = this.getConfig();
		config.addDefault("SecondsBanned", 300);
		config.addDefault("Kickmsg", "You are temp banned for time");
		config.addDefault("Kickrejoin", "You are still banned for time");		
		config.options().copyDefaults(true);
		saveConfig();
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new DeathTListener(this), this);
	}

	public void onDisable()
	{
		reloadConfig();
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(commandLabel.equalsIgnoreCase("dt-reload") && sender.hasPermission("DeathTimeout.reload"))
		{   //command to reload config, can also be used in console
			  reloadConfig();
			  sender.sendMessage("DeathTimeout configuration file reloaded.");
			  return true;
		}
		else if(commandLabel.equalsIgnoreCase("dt-edit") && sender.hasPermission("DeathTimeout.edit"))
		{   
			if(!(args[1]==null))
			{   //ingame command to edit config, can also be used in console
				int changeTime = Integer.parseInt(args[1]);
				this.getConfig().addDefault("SecondsBanned",changeTime);
				sender.sendMessage("Changed ban time to "+(changeTime/1000)/60+"mins "+(changeTime/1000)% 60+"sec");
				saveConfig();
			return true;
			}
		}
		return false;
	}
}
