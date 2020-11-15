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
	public static String MS = "��f[ ��aBJ��� ��f] ";
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("���� �÷������� �ε� �Ǿ����ϴ�.");
	}
	
	public void onDisable()
	{
		getLogger().info("���� �÷������� ��ε� �Ǿ����ϴ�.");
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
		p.sendMessage(MS+"/shield set <�ϱ�/�߱�/���>");
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
				p.sendMessage(MS+"�� ������ ���з� ������ �����մϴ�."); return;
			}
			else if(args[1].equalsIgnoreCase("���"))
			{
				if(Checkitem(p))
				{
					MakeShield(p, "���");
				}
			}
			else if(args[1].equalsIgnoreCase("�߱�"))
			{
				if(Checkitem(p))
				{
					MakeShield(p, "�߱�");
				}
			}
			else if(args[1].equalsIgnoreCase("�ϱ�"))
			{
				if(Checkitem(p))
				{
					MakeShield(p, "�ϱ�");
				}
			}
		}
	}
	
	public void MakeShield(Player p, String str){
		ItemStack shield = p.getItemInHand();
		ItemMeta smeta = shield.getItemMeta();
		List<String> lorelist = new ArrayList<String>();
		lorelist.add("��7Ÿ�� ��f: ��6����");
		lorelist.add("��7��� ��f: ��c"+str);
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
					if(lorelist.contains("��7Ÿ�� ��f: ��6����") && p.isBlocking()){
						for(String s : lorelist){
							if(s.contains("��7��� ��f: ��c")){
								String rank = s.substring(11,13);
								if(rank != null){
									switch(rank){
									case "���": e.setDamage((int)(e.getDamage()*0.1)); return;
									case "�߱�": e.setDamage((int)(e.getDamage()*0.4)); return;
									case "�ϱ�": e.setDamage((int)(e.getDamage()*0.7)); return;
									
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
