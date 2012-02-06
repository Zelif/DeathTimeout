package net.Zelif.DeathTimeout;

import java.util.HashMap;


import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class DeathTListener implements Listener{
	public static HashMap<String, Long> Banned= new HashMap<String, Long>();
	public int banTime = 1;
	public DeathTListener(DeathTimeout plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    	banTime = plugin.getConfig().getInt("SecondsBanned");
    }

    
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event){
		Player p = event.getPlayer();
		if(Banned.containsKey(p.getName().toLowerCase())){
			if(Banned.get(p.getName().toLowerCase()) != null){
				event.setJoinMessage(null);
				long tempTime = Banned.get(p.getName().toLowerCase());
				long now = System.currentTimeMillis();
				long diff = tempTime - now;
				if(diff <= 0){
					Banned.remove(p.getName().toLowerCase());
				}
				else{
					p.kickPlayer("You are still banned for "+(diff/1000)/60+"mins "+(diff/1000)% 60+"sec");
				}
			}
		}			
	}
	
    @EventHandler(priority = EventPriority.HIGH)
	public void onEntityDeathEvent(EntityDeathEvent event) {
    	if (event.getEntity() instanceof Player) {
    		Player pl = (Player)event.getEntity();		
    		long tempTime1 = System.currentTimeMillis()+(banTime*1000);
    		Banned.put(pl.getName().toLowerCase(),tempTime1);
    		pl.kickPlayer("You are temp banned for "+(tempTime1/1000)/60+"mins "+(tempTime1/1000)% 60+"sec");
    	}
    }
}
