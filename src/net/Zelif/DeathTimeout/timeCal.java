package net.zelif.DeathTimeout;

import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerLoginEvent;


public class timeCal
{ 
	private static HashMap<String, Long> BanID = new HashMap<String, Long>();
			
	public static HashMap<String, Long> getBanID() {
		return BanID;
	}

	public static void removeBanID(String name) {
		BanID.remove(name);
	}

	public static boolean checkName(String name)
	{
		if(BanID.get(name.toLowerCase()) == null)
		{
			return false;
 		}
		return true;
	}
	
	public static boolean checkBan(PlayerLoginEvent event)
	{		
		Player p = event.getPlayer();
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
				String caltime = ((diff/1000)/60+"mins "+(diff/1000)% 60+"sec");
				String timeleft = DeathTimeout.getKickJoinMessage().replace("time", caltime);
				event.setKickMessage(timeleft);
				return true;
			}
		}
		return false;
	}

	public static void deathCheck(EntityDeathEvent event)
   	{
    	if (event.getEntity() instanceof Player)
    	{    		
    		Player p = (Player)event.getEntity();
    		if(!(p.hasPermission("deathtimeout.bypass"))) 			//bypass the ban if user has this node
    		{
    			p.getInventory().setHelmet(null);
    			p.getInventory().setChestplate(null);
    			p.getInventory().setLeggings(null);
    			p.getInventory().setBoots(null);
    			p.getInventory().clear();
    				
    			int banTime = DeathTimeout.getBanTime();
    			long tempT1 = System.currentTimeMillis()+(banTime*1000);
    				
    			BanID.put(p.getName().toLowerCase(),tempT1);
    			String timecalc = (banTime/60+"mins "+banTime%60+"sec");
    			String timevar = DeathTimeout.getKickMessage().replace("time", timecalc );
    			p.kickPlayer(timevar);
    			}
    		}
	}
	
}

