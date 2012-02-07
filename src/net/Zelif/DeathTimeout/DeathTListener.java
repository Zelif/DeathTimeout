package net.Zelif.DeathTimeout;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class DeathTListener implements Listener
{
	public static HashMap<String, Long> BanID= new HashMap<String, Long>();
	public int banTime = 1;
	public DeathTListener(DeathTimeout plugin)
	{
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    	banTime = plugin.getConfig().getInt("SecondsBanned");
    }

	public boolean checkBan(PlayerLoginEvent event , Player p )
	{
		if(BanID.containsKey(p.getName().toLowerCase()))
		{
			long tempTime = BanID.get(p.getName().toLowerCase());
			long now = System.currentTimeMillis();
			long diff = tempTime - now;
			if(diff <= 0)
			{
				return false;
			}
			else
			{
				event.setKickMessage("You are still banned for "+(diff/1000)/60+"mins "+(diff/1000)% 60+"sec");
				return true;
			}	
		}
		return false;		
	}
			
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerLogin(PlayerLoginEvent event)
    {
		Player p = event.getPlayer();
    	if(BanID.get(p.getName().toLowerCase()) != null)
    	{
    		if (checkBan(event, p)==true)
    		{
    			event.setResult(Result.KICK_OTHER);
    		}
    		else
    		{
    			BanID.remove(p.getName().toLowerCase());
    		}
    	}
	}
	
    @EventHandler(priority = EventPriority.HIGH)
	public void onEntityDeathEvent(EntityDeathEvent event)
    {
    	if (event.getEntity() instanceof Player)
    	{
    		Player pl = (Player)event.getEntity();		
    		long tempT1 = System.currentTimeMillis()+(banTime*1000);
    		BanID.put(pl.getName().toLowerCase(),tempT1);
    		pl.kickPlayer("You are temp banned for "+banTime/60+"mins "+banTime%60+"sec");
    	}
    }
}
