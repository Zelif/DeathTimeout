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
    		if(timeCal.checkName(pName) == true)  //Checks to see if name is in the hashmap on login
    		{
    			if (timeCal.checkBan(event) == true)   //Checks to see if the player is banned and/or the time on a ban is up
    			{
    				event.setResult(Result.KICK_OTHER); //Kicks if player is still banned
    			}
    			else
    			{
    				timeCal.removeBanID(pName); //Removes the name if the time has expired
    			}
    		}
	}


	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityDeathEvent(EntityDeathEvent event)
   	{
    		if (event.getEntity() instanceof Player)  //only gathers a players death
    		{    		
			Player p = (Player)event.getEntity();
    			if(!(p.hasPermission("deathtimeout.bypass"))) 		//bypass the ban if user has this node
			{ 								
				timeCal.deathCheck(event);  //checks to see if player can be banned.
			}
    		}
   	}
	
}