package me.Bokum.Shield;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
	public static String MS = "§f[ §aBJ뱀사 §f] ";
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("방패 플러그인이 로드 되었습니다.");
	}
	
	public void onDisable()
	{
		getLogger().info("방패 플러그인이 언로드 되었습니다.");
	}
	
	public boolean onCommand(CommandSender talker, Command command, String str, String[] args)
	{
		if(talker instanceof Player)
		{
			Player p = (Player) talker;
			if(str.equalsIgnoreCase("shield") && p.isOp())
			{
				if(args.length <= 0)
				{
					Helpmessages(p);
					return true;
				}
				else
				{
					Setshield(p,args);
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean Checkitem(Player p)
	{
		ItemStack item = p.getItemInHand();
		if(item == null || item.getItemMeta() == null)
		{
			return false;
		}
		return true;
	}
	
	public void Helpmessages(Player p)
	{
		p.sendMessage(MS+"/shield set <하급/중급/상급>");
	}
	
	public void Setshield(Player p, String[] args)
	{
		if(args[0].equalsIgnoreCase("set"))
		{
			if(args.length <= 1)
			{
				Helpmessages(p);
			}
			else if(p.getItemInHand() == null || !(p.getItemInHand().getType() == Material.IRON_SWORD 
					|| p.getItemInHand().getType() == Material.WOOD_SWORD || p.getItemInHand().getType() == Material.GOLD_SWORD
					|| p.getItemInHand().getType() == Material.STONE_SWORD || p.getItemInHand().getType() == Material.DIAMOND_SWORD)){
				p.sendMessage(MS+"검 종류만 방패로 설정이 가능합니다."); return;
			}
			else if(args[1].equalsIgnoreCase("상급"))
			{
				if(Checkitem(p))
				{
					MakeShield(p, "상급");
				}
			}
			else if(args[1].equalsIgnoreCase("중급"))
			{
				if(Checkitem(p))
				{
					MakeShield(p, "중급");
				}
			}
			else if(args[1].equalsIgnoreCase("하급"))
			{
				if(Checkitem(p))
				{
					MakeShield(p, "하급");
				}
			}
		}
	}
	
	public void MakeShield(Player p, String str){
		ItemStack shield = p.getItemInHand();
		ItemMeta smeta = shield.getItemMeta();
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("§7타입 §f: §6방패");
		lorelist.add("§7등급 §f: §c"+str);
		smeta.setLore(lorelist);
		shield.setItemMeta(smeta);
		p.setItemInHand(shield);
	}
	
	@EventHandler
	public void onHit(EntityDamageEvent e){
		if(e.getEntity() instanceof Player){
			Player p = (Player) e.getEntity();
			if(p.getItemInHand() != null){
				ItemStack item = p.getItemInHand();
				if(item.hasItemMeta() && item.getItemMeta().hasLore()){
					List<String> lorelist = item.getItemMeta().getLore();
					if(lorelist.contains("§7타입 §f: §6방패") && p.isBlocking()){
						for(String s : lorelist){
							if(s.contains("§7등급 §f: §c")){
								String rank = s.substring(11,13);
								if(rank != null){
									switch(rank){
									case "상급": e.setDamage((int)(e.getDamage()*0.1)); return;
									case "중급": e.setDamage((int)(e.getDamage()*0.4)); return;
									case "하급": e.setDamage((int)(e.getDamage()*0.7)); return;
									
									default: return;
									}
								}
							}
						}
					}
					
				}
			}
		}
	}
}
