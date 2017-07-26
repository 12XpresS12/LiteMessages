package sk.xpress.lite;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;




public class messages extends JavaPlugin implements Listener {

	FileConfiguration config = getConfig();
	ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
	
	public void onEnable() 
	{

		config.addDefault("Player-Join.Active-Text", true);
	    config.addDefault("Player-Join.Text", "&7» &7Player &f&l%playername% &7has joined on server");
	    config.addDefault("Player-Join.Default-Text", false);
	    
		config.addDefault("Player-Quit.Active-Text", true);
	    config.addDefault("Player-Quit.Text", "&7» &7Player &f&l%playername% &7has left the game");
	    config.addDefault("Player-Quit.Default-Text", false);
	    
	    config.addDefault("Console.ToConsole", false);
	    config.addDefault("Console.DisableCommands", false);
	    
	    config.addDefault("Console.UnknownCommand", "&fUnknown command. type ''/help'' for help.");
	    
	    config.options().copyDefaults(true);
	    saveConfig();
	    

	   getServer().getPluginManager().registerEvents(this, this);
	   
	}

	@EventHandler
	public void OnPlayerJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		String s1 = config.getString("Player-Join.Text");
		
		s1 = s1.replaceAll("%playername%", player.getName());
		
		if(config.getBoolean("Player-Join.Active-Text"))
		{
			msg(s1); // Každému hráèovy ( farebne )
		}
		
		if(config.getBoolean("ToConsole"))
		{
			print(s1); // Do konzole iba ( farebne )
		}
		
		if(!config.getBoolean("Player-Join.Default-Text"))
		{
			event.setJoinMessage(null);
		}
	}
	
	@EventHandler
	public void OnPlayerDisconnect(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		String s1 = config.getString("Player-Quit.Text");
		
		s1 = s1.replaceAll("%playername%", player.getName());
		
		if(config.getBoolean("Player-Quit.Active-Text"))
		{
			msg(s1);
			// Every Player
		}
		if(config.getBoolean("ToConsole"))
		{
			print(s1);
		// To Console
		}
		if(!config.getBoolean("Player-Quit.Default-Text"))
		{
			event.setQuitMessage(null);
		}
	}
	
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
    {
    	if(config.getBoolean("Console.DisableCommands"))
    	{
    		String s1 = config.getString("Console.UnknownCommand"); 
    		msg(s1);
    		return true;
    	}else{	
		   	if(cmd.getName().equalsIgnoreCase("lm"))
		   	{
		   		if(args.length != 1)
		   		{ 
		   			if(!(sender instanceof Player))
		   			{
		   				print("§cSyntax Error: Please use /lm help");
		   			}else{
		   				sender.sendMessage("§cSyntax Error: Please use /lm help");
		   			}
		   		}else{
		
			   		if(args[0].equalsIgnoreCase("help"))
			   		{
			   			String version = getDescription().getVersion();
			   			
			            sender.sendMessage("§7§m=========]§r §aLiteMessages Help §7§m[==========");
			            sender.sendMessage("§8»§7 /lm help - Display this help message");
			            sender.sendMessage("§8»§7 /lm reload - Reload Configuration // Doesn work");
			            sender.sendMessage("§8»§7This will be added in other version of plugin !");
			            sender.sendMessage("§8»§7Version: " + version);
			    		return true;
			   		}
			    		
			   		if(args[0].equalsIgnoreCase("reload"))
			   		{		   			
			    		print("&7[LM] &aPlugin has been reloaded !");
			    		sender.sendMessage("§7LM §8» §a§lPlugin has been reloaded !");
			    		
			    		reload();
			    	
			    		return true;
			    	}
		   		}
		    }
    	}
        return false;
    }
    

    
	public void msg(String message)
	{
		for(Player allPlayers : Bukkit.getOnlinePlayers()) 
		{
			message = message.replaceAll("&","§");
			
			allPlayers.sendMessage(message);
		}
	}
	public void print(String text){
		text = text.replaceAll("&", "§");
		//this.getServer().broadcastMessage(text); -- VSETKYM
		getServer().getConsoleSender().sendMessage(text);
	}
	public void reload() {
		reloadConfig();
	}
}