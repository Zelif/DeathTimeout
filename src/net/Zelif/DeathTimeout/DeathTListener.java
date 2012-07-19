package net.zelif.DeathTimeout;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class DeathTListener implements Listener
{
	DeathTimeout plugin;
	public DeathTListener(DeathTimeout instance)
	{
		plugin = instance;
    }
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerLogin(PlayerLoginEvent event)
	{
		String pName = event.getPlayer().getName().toString();
    	if(timeCal.checkName(pName) == true)
    	{
    		if (timeCal.checkBan(event) == true)
    		{
    			event.setResult(Result.KICK_OTHER);
    		}
    		else
    		{
    			timeCal.removeBanID(pName);
    		}
    	}
	}


	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityDeathEvent(EntityDeathEvent event)
   	{
    	if (event.getEntity() instanceof Player)
    	{    		
			Player p = (Player)event.getEntity();
    		if(!(p.hasPermission("deathtimeout.bypass"))) 		//bypass the ban if user has this node
			{ 								
				timeCal.deathCheck(event);
			}
    	}
   	}
	
}