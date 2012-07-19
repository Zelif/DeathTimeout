package net.zelif.DeathTimeout;

import java.util.logging.Logger;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class DeathTimeout extends JavaPlugin
{
	private static int banTime;
	private static String kickMessage;
	private static String kickJoinMessage;
	
	Logger log = Logger.getLogger("Minecraft");

	public  void onEnable()
	{
		this.getConfig().addDefault("SecondsBanned", 300);
		banTime = this.getConfig().getInt("SecondsBanned");
		this.getConfig().addDefault("Kickmsg", "You are temp banned for time");
		kickMessage = this.getConfig().getString("Kickmsg");
		this.getConfig().addDefault("Kickrejoin", "You are still banned for time");		
		kickJoinMessage = this.getConfig().getString("Kickrejoin");
		this.getConfig().options().copyDefaults(true);
		saveConfig();
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new DeathTListener(this), this);
		
		CommandExecutor myExecutor = new DeathTimeoutCommand(this);
		getCommand("dt-reload").setExecutor(myExecutor);
		getCommand("dt-edit").setExecutor(myExecutor);
		getCommand("dt-unban").setExecutor(myExecutor);
		getCommand("dt-list").setExecutor(myExecutor);
	}

	public void onDisable()
	{
		reloadConfig();
	}
	
	public static int getBanTime(){
		return banTime;
	}
	public static String getKickMessage(){
		return kickMessage;
	}
	public static String getKickJoinMessage(){
		return kickJoinMessage;
	}
	public void setBanTime(int changeTime){
		banTime = changeTime;
	}
	public void setKickMessage(String changeKickMsg){
		kickMessage = changeKickMsg;
	}
	public void setKickJoinMessage(String changeKickJoinMsg){
		kickJoinMessage = changeKickJoinMsg;
	}
	
}