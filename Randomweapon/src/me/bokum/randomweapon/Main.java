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
		getLogger().info(PREFIX + "랜덤무기전쟁 플러그인 로드 완료!");
	}

	public void onDisable()
	{
		getLogger().info(PREFIX + "랜덤무기전쟁 플러그인 언로드 완료!");
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
		players[i].sendMessage(ChatColor.GRAY + Main.PREFIX + "기본장비 받으셨습니다!");
	}
}

public void randomweaponhelp(CommandSender playername)
{
	playername.sendMessage(ChatColor.GREEN + Main.PREFIX + ChatColor.YELLOW+"/rw start" +ChatColor.BLACK +"-"+ChatColor.GOLD+ "게임을 시작합니다.");
	playername.sendMessage(ChatColor.GREEN + Main.PREFIX + ChatColor.YELLOW+"/rw help" +ChatColor.BLACK +"-"+ChatColor.GOLD+ "명령어의 설명을 봅니다.");
	playername.sendMessage(ChatColor.GREEN + Main.PREFIX + ChatColor.YELLOW+"/rw clear" +ChatColor.BLACK +"-"+ChatColor.GOLD+ "게임을 초기화 합니다. ( 인벤토리 및 레벨이 초기화 됩니다 )");
	playername.sendMessage(ChatColor.GREEN + Main.PREFIX + ChatColor.YELLOW+"/rw give" +ChatColor.BLACK +"-"+ChatColor.GOLD+ "모두에게 기본장비를 지급합니다.");
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
		gameonlineplayer[i].sendMessage(ChatColor.YELLOW + startplayer.getName() + "님께서 게임을 시작 시켰습니다.");
	}
	playerab = new String[gameonlineplayer.length];
	startcheck = true;
	for(i=0; i <gameonlineplayer.length; i++)
	{
		String ability = setability(gameonlineplayer[i]);
		gameonlineplayer[i].sendMessage(ChatColor.YELLOW + "당신의 능력은" + ability +  "입니다.");
	}
}

public String setability(Player player)
{
	boolean check = true;
	int callmethod = 0;
	String ability = "선택안됨";
	while(check == true)
	{
		callmethod = (int)(Math.random()*6);
		player.sendMessage(callmethod+"번");
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
	String ability = "선택안됨";
	switch(num)
	{

	case 0:
		item = new ItemStack(Material.WOOD_SWORD, 1);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.RESET + "더블점핑");
		lore.add("더블 점프를 사용할 수 있습니다.");
		lore.add("사용법: 점프키를 두번 누르세요");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.DURABILITY, 5, true);
		item.setItemMeta(meta);
		player.getInventory().addItem(item);
		ability = ChatColor.GREEN +" 더블점핑";
		break;
	case 1:
		item = new ItemStack(Material.DIAMOND_AXE, 1);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.RESET+ "텔레포트");
		lore.add("텔레포트를 사용할 수 있습니다.");
		lore.add("사용법: 텔레포트 할 방향을 바라보고 우클릭");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.DURABILITY, 5, true);
		item.setItemMeta(meta);
		player.getInventory().addItem(item);
		ability = ChatColor.GREEN + " 텔레포트";
		break;
	case 2:
		item = new ItemStack(Material.DIAMOND_PICKAXE, 1);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.RESET + "번개술사");
		lore.add("번개 스킬을 사용할 수 있습니다.");
		lore.add("사용법: 사용할 플레이어를 바라보고 우클릭 ( 거리 20칸 내로)");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.DURABILITY, 5, true);
		item.setItemMeta(meta);
		player.getInventory().addItem(item);
		ability = ChatColor.GREEN + "번개 술사";
		break;
	case 3:
		item = new ItemStack(Material.DIAMOND_SWORD, 1);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.RESET+"끌기");
		lore.add("상대를 끌 수 있습니다.");
		lore.add("사용법: 사용할 플레이어를 바라보고 우클릭 ( 거리 20칸 내로 )");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.DURABILITY, 5, true);
		item.setItemMeta(meta);
		ability = ChatColor.GREEN + "끌기";
		player.getInventory().addItem(item);
		break;
	case 4:
		item = new ItemStack(Material.DIAMOND_SPADE, 1);
		ability = ChatColor.GREEN + "무능력";
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.RESET+"무능력");
		lore.add("무능력 합니다.");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.DURABILITY, 5, true);
		item.setItemMeta(meta);
		player.getInventory().addItem(item);
		break;
	case 5:
		item = new ItemStack(Material.STONE_PICKAXE, 1);
		ability = "돌 곡괭이";
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.RESET+"더블 점핑");
		lore.add("더블 점프를 사용할 수 있습니다.");
		lore.add("사용법: 점프키를 두번 누르세요");
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
	if(handgetname(event.getPlayer()).equalsIgnoreCase(ChatColor.RESET+"더블점핑") &&  event.getPlayer().getGameMode() != GameMode.CREATIVE && event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType()!= Material.AIR)
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
	if(handgetname(player).equalsIgnoreCase(ChatColor.RESET+"더블점핑") && player.getGameMode() != GameMode.CREATIVE)
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
	if((event.getAction()==Action.RIGHT_CLICK_AIR||event.getAction()==Action.RIGHT_CLICK_BLOCK) && handgetname(player).equalsIgnoreCase(ChatColor.RESET+"텔레포트"))
	{
		tploc = player.getLocation();
		tpcheck = true;
		player.sendMessage(ChatColor.GREEN+Main.PREFIX+ChatColor.GRAY+"현재위치 ("+(int)tploc.getX()+","+(int)tploc.getY()+","+(int)tploc.getZ()+") 로 지정하였습니다.");
	}
	else if((event.getAction()==Action.LEFT_CLICK_AIR||event.getAction()==Action.LEFT_CLICK_BLOCK) && handgetname(player).equalsIgnoreCase(ChatColor.RESET+"텔레포트"))
	{
		if(tpcheck)
		{
			player.teleport(tploc);
			player.sendMessage(ChatColor.GREEN+Main.PREFIX+ChatColor.GRAY+"지정해둔 위치로 이동 하였습니다.");
		}
	}
	}
}
}
