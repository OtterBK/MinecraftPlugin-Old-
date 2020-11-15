package me.bokum.randomweapon;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;



public class Main extends JavaPlugin implements Listener 
{
	
	public static Player[] gameonlineplayer;
	public static String[] playerab;
	public static boolean[] ablist;
	public static String PREFIX = "[RW]";
	public static boolean startcheck = false;

	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info(PREFIX + "������������ �÷����� �ε� �Ϸ�!");
	}

	public void onDisable()
	{
		getLogger().info(PREFIX + "������������ �÷����� ��ε� �Ϸ�!");
	}
	
	public String handgetname(Player player)
	{
		String name = "";
		if(player.getInventory().getItemInHand().hasItemMeta() == true)
		{
			name = player.getInventory().getItemInHand().getItemMeta().getDisplayName();
		}
		return name;
	}
	
	public boolean onCommand(CommandSender talker, Command command, String string,String[] args )
	{
		if(talker instanceof Player)
		{
			Player player = (Player) talker;
			
			if(string.equalsIgnoreCase("dirtest"))
			{
				Vector vd = player.getLocation().getDirection();
				player.sendMessage("vd:" + vd);
			}
			
			if(string.equalsIgnoreCase("rw"))
			{
				if(args.length >= 1)
				{
				if(args[0].equalsIgnoreCase("start"))
				{
					randomweaponstart(player);
				}
				if(args[0].equalsIgnoreCase("help"))
				{
					randomweaponhelp(player);
				}
				if(args[0].equalsIgnoreCase("clear"))
				{
					randomweaponclear();
				}
				if(args[0].equalsIgnoreCase("give"))
				{
					randomweapongive();
				}
				}
				else
				{
					randomweaponhelp(player);
				}
			}
		}
		return true;
	}

public void randomweapongive()
{
	Player[] players = Bukkit.getServer().getOnlinePlayers();
	ItemStack item = new ItemStack(Material.IRON_CHESTPLATE,1);
	for(int i = 0; i < players.length; i++)
	{
		players[i].getInventory().addItem(item);
		players[i].sendMessage(ChatColor.GRAY + Main.PREFIX + "�⺻��� �����̽��ϴ�!");
	}
}

public void randomweaponhelp(CommandSender playername)
{
	playername.sendMessage(ChatColor.GREEN + Main.PREFIX + ChatColor.YELLOW+"/rw start" +ChatColor.BLACK +"-"+ChatColor.GOLD+ "������ �����մϴ�.");
	playername.sendMessage(ChatColor.GREEN + Main.PREFIX + ChatColor.YELLOW+"/rw help" +ChatColor.BLACK +"-"+ChatColor.GOLD+ "��ɾ��� ������ ���ϴ�.");
	playername.sendMessage(ChatColor.GREEN + Main.PREFIX + ChatColor.YELLOW+"/rw clear" +ChatColor.BLACK +"-"+ChatColor.GOLD+ "������ �ʱ�ȭ �մϴ�. ( �κ��丮 �� ������ �ʱ�ȭ �˴ϴ� )");
	playername.sendMessage(ChatColor.GREEN + Main.PREFIX + ChatColor.YELLOW+"/rw give" +ChatColor.BLACK +"-"+ChatColor.GOLD+ "��ο��� �⺻��� �����մϴ�.");
}

public void randomweaponclear()
{
	Player[] players = Bukkit.getServer().getOnlinePlayers();
	startcheck = false;
	for(int i = 0; i < players.length; i++)
	{
		players[i].getInventory().clear();
		players[i].setLevel(0);
		players[i].sendMessage(ChatColor.GREEN+ Main.PREFIX + "Cleard Inventory and Level");
	}

}

public void randomweaponstart(CommandSender playername)
{
	Player startplayer = (Player) playername;
	gameonlineplayer = Bukkit.getServer().getOnlinePlayers();
	ablist = new boolean[5];
	for(int k = 0; k < 5; k++ )
	{
		ablist[k] = false;
	}
	int i = 0;
	for(i = 0; i < gameonlineplayer.length; i++)
	{
		gameonlineplayer[i].sendMessage(ChatColor.YELLOW + startplayer.getName() + "�Բ��� ������ ���� ���׽��ϴ�.");
	}
	playerab = new String[gameonlineplayer.length];
	startcheck = true;
	for(i=0; i <gameonlineplayer.length; i++)
	{
		String ability = setability(gameonlineplayer[i]);
		gameonlineplayer[i].sendMessage(ChatColor.YELLOW + "����� �ɷ���" + ability +  "�Դϴ�.");
	}
}

public String setability(Player player)
{
	boolean check = true;
	int callmethod = 0;
	String ability = "���þȵ�";
	while(check == true)
	{
		callmethod = (int)(Math.random()*6);
		player.sendMessage(callmethod+"��");
		if(ablist[callmethod]!= true)
		{
			check = false;
			ablist[callmethod] = true;
			ability = weapongive(callmethod, player);
			break;
		}
	}
	return ability;
}

public String weapongive(int num, Player player )
{
	ItemStack item = new ItemStack(Material.AIR, 1);
	ItemMeta meta;
	ArrayList<String> lore = new ArrayList<String>();
	String ability = "���þȵ�";
	switch(num)
	{

	case 0:
		item = new ItemStack(Material.WOOD_SWORD, 1);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.RESET + "��������");
		lore.add("���� ������ ����� �� �ֽ��ϴ�.");
		lore.add("����: ����Ű�� �ι� ��������");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.DURABILITY, 5, true);
		item.setItemMeta(meta);
		player.getInventory().addItem(item);
		ability = ChatColor.GREEN +" ��������";
		break;
	case 1:
		item = new ItemStack(Material.DIAMOND_AXE, 1);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.RESET+ "�ڷ���Ʈ");
		lore.add("�ڷ���Ʈ�� ����� �� �ֽ��ϴ�.");
		lore.add("����: �ڷ���Ʈ �� ������ �ٶ󺸰� ��Ŭ��");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.DURABILITY, 5, true);
		item.setItemMeta(meta);
		player.getInventory().addItem(item);
		ability = ChatColor.GREEN + " �ڷ���Ʈ";
		break;
	case 2:
		item = new ItemStack(Material.DIAMOND_PICKAXE, 1);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.RESET + "��������");
		lore.add("���� ��ų�� ����� �� �ֽ��ϴ�.");
		lore.add("����: ����� �÷��̾ �ٶ󺸰� ��Ŭ�� ( �Ÿ� 20ĭ ����)");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.DURABILITY, 5, true);
		item.setItemMeta(meta);
		player.getInventory().addItem(item);
		ability = ChatColor.GREEN + "���� ����";
		break;
	case 3:
		item = new ItemStack(Material.DIAMOND_SWORD, 1);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.RESET+"����");
		lore.add("��븦 �� �� �ֽ��ϴ�.");
		lore.add("����: ����� �÷��̾ �ٶ󺸰� ��Ŭ�� ( �Ÿ� 20ĭ ���� )");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.DURABILITY, 5, true);
		item.setItemMeta(meta);
		ability = ChatColor.GREEN + "����";
		player.getInventory().addItem(item);
		break;
	case 4:
		item = new ItemStack(Material.DIAMOND_SPADE, 1);
		ability = ChatColor.GREEN + "���ɷ�";
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.RESET+"���ɷ�");
		lore.add("���ɷ� �մϴ�.");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.DURABILITY, 5, true);
		item.setItemMeta(meta);
		player.getInventory().addItem(item);
		break;
	case 5:
		item = new ItemStack(Material.STONE_PICKAXE, 1);
		ability = "�� ���";
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.RESET+"���� ����");
		lore.add("���� ������ ����� �� �ֽ��ϴ�.");
		lore.add("����: ����Ű�� �ι� ��������");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.DURABILITY, 5, true);
		item.setItemMeta(meta);
		player.getInventory().addItem(item);
		break;
	}
	return ability;
}
@EventHandler
public void onMove(PlayerMoveEvent event)
{
	if(startcheck && !(event.getPlayer().isDead()))
	{
	if(handgetname(event.getPlayer()).equalsIgnoreCase(ChatColor.RESET+"��������") &&  event.getPlayer().getGameMode() != GameMode.CREATIVE && event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType()!= Material.AIR)
	{
		event.getPlayer().setAllowFlight(true);
	}
	}

}

@EventHandler
public void onFly(PlayerToggleFlightEvent event)
{
	Player player = event.getPlayer();
	if(startcheck)
	{
	if(handgetname(player).equalsIgnoreCase(ChatColor.RESET+"��������") && player.getGameMode() != GameMode.CREATIVE)
	{
		event.setCancelled(true);
		player.setAllowFlight(false);
		player.setFlying(false);
		player.setVelocity(player.getLocation().getDirection().multiply(0.6D).setY(0.5D));;
		
	}
	}


}


@EventHandler
public void onRightClick(PlayerInteractEvent event)
{
	if(startcheck)
	{
		boolean tpcheck = false;
		Location tploc = null;
	Player player = event.getPlayer();
	if((event.getAction()==Action.RIGHT_CLICK_AIR||event.getAction()==Action.RIGHT_CLICK_BLOCK) && handgetname(player).equalsIgnoreCase(ChatColor.RESET+"�ڷ���Ʈ"))
	{
		tploc = player.getLocation();
		tpcheck = true;
		player.sendMessage(ChatColor.GREEN+Main.PREFIX+ChatColor.GRAY+"������ġ ("+(int)tploc.getX()+","+(int)tploc.getY()+","+(int)tploc.getZ()+") �� �����Ͽ����ϴ�.");
	}
	else if((event.getAction()==Action.LEFT_CLICK_AIR||event.getAction()==Action.LEFT_CLICK_BLOCK) && handgetname(player).equalsIgnoreCase(ChatColor.RESET+"�ڷ���Ʈ"))
	{
		if(tpcheck)
		{
			player.teleport(tploc);
			player.sendMessage(ChatColor.GREEN+Main.PREFIX+ChatColor.GRAY+"�����ص� ��ġ�� �̵� �Ͽ����ϴ�.");
		}
	}
	}
}
}
