package net.zelif.DeathTimeout;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DeathTimeoutCommand implements CommandExecutor
{
	private DeathTimeout plugin;
	
	public DeathTimeoutCommand(DeathTimeout plugin)
	{
		this.plugin = plugin;
	}
	 
	@SuppressWarnings("rawtypes")
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if(commandLabel.equalsIgnoreCase("dt-reload") && sender.hasPermission("DeathTimeout.reload"))
		{   //command to reload config, can also be used in console
			plugin.reloadConfig();
			sender.sendMessage("DeathTimeout configuration file reloaded.");
			return true;
		}
		else if(commandLabel.equalsIgnoreCase("dt-edit") && sender.hasPermission("DeathTimeout.edit"))
		{   
			if(args.length > 0)
			{   //ingame command to edit config, can also be used in console
				int changeTime = Integer.parseInt(args[0]);
				plugin.getConfig().set("SecondsBanned",changeTime);
				sender.sendMessage("Changed ban time to "+changeTime/60+"mins "+changeTime% 60+"sec");
				plugin.saveConfig();
				plugin.setBanTime(changeTime);
				return true;
			}
		}
		else if(commandLabel.equalsIgnoreCase("dt-unban") && sender.hasPermission("DeathTimeout.unban"))
		{   
			if(args.length > 0)
			{   //ingame command to unban players, can also be used in console
				OfflinePlayer targetPlayer = Bukkit.getServer().getOfflinePlayer(args[0]);
				if(targetPlayer.hasPlayedBefore() && timeCal.checkName(args[0].toLowerCase()) == true)
				{
					timeCal.removeBanID(args[0].toLowerCase());
					sender.sendMessage("Removed " + args[0] + " from banlist.");
					return true;
				}
				sender.sendMessage(args[0] + " is either not on the banlist or has not played on the server.");
				return false;
			}
			sender.sendMessage("Needs a valid player name.");
		}
		else if(commandLabel.equalsIgnoreCase("dt-list") && sender.hasPermission("DeathTimeout.list"))
		{
			Set banList = timeCal.getBanID().entrySet();
			Iterator i = banList.iterator();
			// Display elements
			while(i.hasNext()){
				Map.Entry me = (Map.Entry)i.next();
				long timz = (Long) me.getValue();
				long now = System.currentTimeMillis();
				long diff = timz - now;
				sender.sendMessage(me.getKey() + " is banned for: " + (diff/1000)/60 + "min " + (diff/1000)%60 + "sec.");
				return true;
			}
		}
		return false;
	}
}

