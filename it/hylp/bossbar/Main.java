package it.hylp.BossBar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements Listener{
	
	static ArrayList<String> a = new ArrayList();

	public void onEnable()
	  {
	    PluginDescriptionFile pdfile = getDescription();
	    Logger logger = getLogger();
	    
	    Bukkit.getPluginManager().registerEvents(this, this);
	    saveDefaultConfig();
	    
	    File c = new File(this.getDataFolder() + "");
	    if(!c.exists()){
	    	c.mkdirs();
	    }
	    
	    logger.info(pdfile.getName() + " Attivato (v." + pdfile.getVersion() + ")");
	  }
	  
	  public void onDisable()
	  {
	    PluginDescriptionFile pdfile = getDescription();
	    Logger logger = getLogger();
	    
	    logger.info(pdfile.getName() + " Disattivato (v." + pdfile.getVersion() + ")");
	  }
	  @SuppressWarnings("deprecation")
	public void add(final Player p) {
		  

		  //Create a BossBar, with first Title, Color and Style of Bar
		  final BossBar bar = Bukkit.getServer().createBossBar(this.getConfig().getString(Format("Title-1")), BarColor.BLUE, BarStyle.SOLID, new BarFlag[] { BarFlag.PLAY_BOSS_MUSIC });
	      //Clear the ArrayList a, to the start animation
	      a.clear();
	      a.add("1");
	      Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new BukkitRunnable()
	      {
	    	  public void run()
		        {
	    		  int i = Main.a.size();
		          if (i == 1)
		          {
		           //Create a second title of BossBar, change this with config
		            bar.setTitle(Main.this.getConfig().getString(Format("Title-2")));
		           //Set a Color of BossBar in this case is YELLOW
		            bar.setColor(BarColor.YELLOW);
		            Main.a.add("A");
		          }
		          if (i == 2)
		          {
		            bar.setTitle(Main.this.getConfig().getString(Format("Title-3")));
		            bar.setColor(BarColor.RED);
		            Main.a.add("B");
		          }
		          if (i == 3)
		          {
		            bar.setTitle(Main.this.getConfig().getString(Format("Title-4")));
		            bar.setColor(BarColor.GREEN);
		            Main.a.add("C");
		          }
		          if (i == 3)
		          {
		           //Reset of ArrayList
		            Main.a.clear();
		            bar.addPlayer(p);
		            Main.a.add("1");
		          }
		        }
		        //Time when the bar changes
	      }, 0L, Main.this.getConfig().getInt("delay"));
	    }
	    //Add a BossBar when player join in the server
	  @EventHandler
	  public void onPlayerJoin(PlayerJoinEvent e){
		  add(e.getPlayer());
	  }
	  //Replace color format this "§" with this "&"
		public static String Format(String text) {
			return text.replaceAll("&", "§");
		}
		//To remove the BossBar
		  @SuppressWarnings("deprecation")
			public void remove(final Player p) {
			  final BossBar bar = Bukkit.getServer().createBossBar(this.getConfig().getString("Titolo-1"), BarColor.BLUE, BarStyle.SOLID, new BarFlag[] { BarFlag.PLAY_BOSS_MUSIC });
			  bar.removeFlag(BarFlag.PLAY_BOSS_MUSIC);
		  }
		  //This command is for reload the config of bossbar
		@Override
		public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			Player p = (Player) sender;
			if(p.hasPermission("bossbar.reload")){
				if(cmd.getName().equalsIgnoreCase("bossbar")){
					if (args.length == 0){
						
					}else if (args.length == 1){
						if(args[0].equalsIgnoreCase("reload")){
							this.reloadConfig();
							this.saveConfig();
							p.sendMessage(Format("&7BossBar &creloaded!"));
							remove(p);
						}
					}
				}
			}
			return false;
			
		}
}
