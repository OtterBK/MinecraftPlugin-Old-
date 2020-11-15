package GoldBigDragon_RPG.GUI;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import GoldBigDragon_RPG.Main.ServerOption;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class DeathGUI extends GUIutil
{
	public void SetNormal(String path, String normalValue)
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
	  	YamlManager Config = YC.getNewConfig("config.yml");
	  	if(Config.getString(path)==null)
	  	{
  			Config.set(path, normalValue);
  			Config.saveConfig();
  			return;
	  	}
	  	if(Config.getString(path).contains("%"))
	  	{
	  		try
	  		{
	  			byte value = Byte.parseByte(Config.getString(path).replace("%", ""));
	  			if(value > 100||value<0)
	  			{
		  			Config.set(path, normalValue);
		  			Config.saveConfig();
	  			}
	  		}
	  		catch(Exception e)
	  		{
	  			Config.set(path, normalValue);
	  			Config.saveConfig();
	  		}
	  	}
	}
	
	
	public void OpenReviveSelectGUI(Player player)
	{
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.BLACK + "�ൿ�Ҵ� ���� -��Ȱ ��� ����-");

	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
	  	YamlManager Config = YC.getNewConfig("config.yml");

	  	SetNormal("Death.Spawn_Home.SetHealth", "100%");
	  	SetNormal("Death.Spawn_Home.PenaltyEXP", "10%");
	  	SetNormal("Death.Spawn_Home.PenaltyMoney", "0%");

	  	SetNormal("Death.Spawn_Here.SetHealth", "1%");
	  	SetNormal("Death.Spawn_Here.PenaltyEXP", "15%");
	  	SetNormal("Death.Spawn_Here.PenaltyMoney", "10%");
	  	
	  	SetNormal("Death.Spawn_Help.SetHealth", "1%");
	  	SetNormal("Death.Spawn_Help.PenaltyEXP", "5%");
	  	SetNormal("Death.Spawn_Help.PenaltyMoney", "0%");
	  	
	  	SetNormal("Death.Spawn_Item.SetHealth", "100%");
	  	SetNormal("Death.Spawn_Item.PenaltyEXP", "0%");
	  	SetNormal("Death.Spawn_Item.PenaltyMoney", "0%");
	  	
	  	try
	  	{
			if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getETC_LastVisited()==null||GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getETC_LastVisited().compareTo("null")==0)
				Stack2(ChatColor.GOLD + "" + ChatColor.BOLD + "[����� �������� ��Ȱ]", 345,0,1,Arrays.asList(ChatColor.GRAY + "�ֱ� �湮�� ������ �����ϴ�.",ChatColor.GRAY + "�� ����� ������ ���,",ChatColor.GRAY + player.getLocation().getWorld().getName()+"���忡 ������",ChatColor.GRAY + "�⺻ ���� �������� ��Ȱ�մϴ�.","",ChatColor.GREEN+" + "+Config.getString("Death.Spawn_Home.SetHealth")+" �����",ChatColor.RED+" - ����ġ "+Config.getString("Death.Spawn_Home.PenaltyEXP")+" ����",ChatColor.RED+" - ������ "+Config.getString("Death.Spawn_Home.PenaltyMoney")+" ����"), 10, inv);
			else
				Stack2(ChatColor.GOLD + "" + ChatColor.BOLD + "[����� �������� ��Ȱ]", 345,0,1,Arrays.asList(ChatColor.YELLOW + GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getETC_LastVisited()+ChatColor.GRAY +"���� ��Ȱ�մϴ�.","",ChatColor.GREEN+" + "+Config.getString("Death.Spawn_Home.SetHealth")+" �����",ChatColor.RED+" - ����ġ "+Config.getString("Death.Spawn_Home.PenaltyEXP")+" ����",ChatColor.RED+" - ������ "+Config.getString("Death.Spawn_Home.PenaltyMoney")+" ����"), 10, inv);
	  	}
	  	catch(NullPointerException e)
	  	{
	  		Stack2(ChatColor.GOLD + "" + ChatColor.BOLD + "[����� �������� ��Ȱ]", 345,0,1,Arrays.asList(ChatColor.GRAY + "�ֱ� �湮�� ������ �����ϴ�.",ChatColor.GRAY + "�� ����� ������ ���,",ChatColor.GRAY + player.getLocation().getWorld().getName()+"���忡 ������",ChatColor.GRAY + "�⺻ ���� �������� ��Ȱ�մϴ�.","",ChatColor.GREEN+" + "+Config.getString("Death.Spawn_Home.SetHealth")+" �����",ChatColor.RED+" - ����ġ "+Config.getString("Death.Spawn_Home.PenaltyEXP")+" ����",ChatColor.RED+" - ������ "+Config.getString("Death.Spawn_Home.PenaltyMoney")+" ����"), 10, inv);
	  	}
		if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_EXP()<0)
			Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[�ٽ� �Ͼ��]", 166,0,1,Arrays.asList(ChatColor.GRAY + "����ġ�� �����Ͽ� ���ڸ�",ChatColor.GRAY + "��Ȱ�� �Ұ��� �մϴ�."), 12, inv);
		else if(Config.getBoolean("Death.DistrictDirectRevive"))
			Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[�ٽ� �Ͼ��]", 166,0,1,Arrays.asList(ChatColor.GRAY + "���ڸ� ��Ȱ�� �Ұ����մϴ�."), 12, inv);
		else
			Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[�ٽ� �Ͼ��]", 2266,0,1,Arrays.asList(ChatColor.GRAY + "������ ���� �ٽ� �Ͼ�ϴ�.",ChatColor.GRAY + "���� ���´� ����ϱ� ����ϴ�.","",ChatColor.GREEN+" + "+Config.getString("Death.Spawn_Here.SetHealth")+" �����",ChatColor.RED+" - ����ġ "+Config.getString("Death.Spawn_Here.PenaltyEXP")+" ����",ChatColor.RED+" - ������ "+Config.getString("Death.Spawn_Here.PenaltyMoney")+" ����"), 12, inv);
		
		ItemStack item = Config.getItemStack("Death.RescueItem");
		
		if(item == null)
			Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[������ ��ٸ���]", 397,3,1,Arrays.asList(ChatColor.GRAY + "�ٸ� ����� ������ ��û�մϴ�.",ChatColor.GRAY + "������ ����� �ִ��� ���캸����.","",ChatColor.GREEN+" + "+Config.getString("Death.Spawn_Help.SetHealth")+" �����",ChatColor.RED+" - ����ġ "+Config.getString("Death.Spawn_Help.PenaltyEXP")+" ����",ChatColor.RED+" - ������ "+Config.getString("Death.Spawn_Help.PenaltyMoney")+" ����"), 14, inv);
		else
			Stack2(ChatColor.RED + "" + ChatColor.BOLD + "[������ ��ٸ���]", item.getTypeId(),item.getData().getData(),item.getAmount(),Arrays.asList(ChatColor.GRAY + "�ٸ� ����� ������ ��û�մϴ�.",ChatColor.GRAY + "������ ����� �ִ��� ���캸����.","",ChatColor.GREEN+" + "+Config.getString("Death.Spawn_Help.SetHealth")+" �����",ChatColor.RED+" - ����ġ "+Config.getString("Death.Spawn_Help.PenaltyEXP")+" ����",ChatColor.RED+" - ������ "+Config.getString("Death.Spawn_Help.PenaltyMoney")+" ����"), 14, inv);
		
		item = Config.getItemStack("Death.ReviveItem");
		if(item == null)
			Stack2(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[��Ȱ�� ���]", 399,0,1,Arrays.asList(ChatColor.GRAY + "���ڸ� ��Ȱ �������� ����մϴ�.","",ChatColor.GREEN+" + "+Config.getString("Death.Spawn_Item.SetHealth")+" �����",ChatColor.RED+" - ����ġ "+Config.getString("Death.Spawn_Item.PenaltyEXP")+" ����",ChatColor.RED+" - ������ "+Config.getString("Death.Spawn_Item.PenaltyMoney")+" ����"), 16, inv);
		else if(item.hasItemMeta()==false)
			Stack2(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[��Ȱ�� ���]", item.getTypeId(),item.getData().getData(),item.getAmount(),Arrays.asList(ChatColor.GRAY + "���ڸ� ��Ȱ �������� ����մϴ�.","",ChatColor.GREEN+" + "+Config.getString("Death.Spawn_Item.SetHealth")+" �����",ChatColor.RED+" - ����ġ "+Config.getString("Death.Spawn_Item.PenaltyEXP")+" ����",ChatColor.RED+" - ������ "+Config.getString("Death.Spawn_Item.PenaltyMoney")+" ����"), 16, inv);
		else if(item.getItemMeta().hasDisplayName()==false)
			Stack2(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[��Ȱ�� ���]", item.getTypeId(),item.getData().getData(),item.getAmount(),Arrays.asList(ChatColor.GRAY + "���ڸ� ��Ȱ �������� ����մϴ�.","",ChatColor.GREEN+" + "+Config.getString("Death.Spawn_Item.SetHealth")+" �����",ChatColor.RED+" - ����ġ "+Config.getString("Death.Spawn_Item.PenaltyEXP")+" ����",ChatColor.RED+" - ������ "+Config.getString("Death.Spawn_Item.PenaltyMoney")+" ����"), 16, inv);
		else
			Stack2(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "["+item.getItemMeta().getDisplayName()+ChatColor.DARK_AQUA + "" + ChatColor.BOLD +" ���]", item.getTypeId(),item.getData().getData(),item.getAmount(),Arrays.asList(ChatColor.GRAY + "���ڸ� ��Ȱ �������� ����մϴ�.","",ChatColor.GREEN+" + "+Config.getString("Death.Spawn_Item.SetHealth")+" �����",ChatColor.RED+" - ����ġ "+Config.getString("Death.Spawn_Item.PenaltyEXP")+" ����",ChatColor.RED+" - ������ "+Config.getString("Death.Spawn_Item.PenaltyMoney")+" ����"), 16, inv);
		player.openInventory(inv);
	}
	
	public void ReviveSelectClick(InventoryClickEvent event)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		Player player = (Player) event.getWhoClicked();
		switch(event.getSlot())
		{
		case 10:
			s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
			ReviveAtLastVisitedArea(player);
			new GoldBigDragon_RPG.Effect.Corpse().RemoveCorpse(player.getName());
	    	if(Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI") == true)
	    		new OtherPlugins.NoteBlockAPIMain().Stop(player);
			break;
		case 12:
		{
			if(event.getCurrentItem().getTypeId()==166)
			{
				s.SP(player, Sound.ANVIL_LAND, 1.0F, 1.0F);
				return;
			}
			else
			{
				s.SP(player, Sound.ITEM_PICKUP, 1.0F, 1.0F);
				new GoldBigDragon_RPG.Effect.Corpse().RemoveCorpse(player.getName());
				ReviveAtDeadPoint(player);
		    	if(Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI") == true)
		    		new OtherPlugins.NoteBlockAPIMain().Stop(player);
			}
		}
			break;
		case 14:
			{
				if(ServerOption.PartyJoiner.containsKey(player))
				{
					Player[] partyMember = ServerOption.Party.get(ServerOption.PartyJoiner.get(player)).getMember();
					for(int count = 0; count < partyMember.length; count++)
						if(player != partyMember[count])
						{
							s.SP(partyMember[count], Sound.VILLAGER_DEATH, 0.4F, 0.5F);
							partyMember[count].sendMessage(ChatColor.LIGHT_PURPLE+"[���� ��û] : "+ChatColor.YELLOW+player.getName()+ChatColor.LIGHT_PURPLE+"������ ���� ���� ��û�� ���Խ��ϴ�! (���� : "+player.getLocation().getWorld().getName() + ", XYZ : " + (int)(player.getLocation().getX())+","+(int)(player.getLocation().getY())+","+(int)(player.getLocation().getZ())+")");
						}
					s.SP(player, Sound.SKELETON_WALK, 1.0F, 1.0F);
					player.sendMessage(ChatColor.LIGHT_PURPLE+"[���� ��û] : ��Ƽ ����鿡�� ���� ��û ��ȣ�� ���½��ϴ�!");
				}
				else
				{
				  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
					YamlManager FriendsList  = YC.getNewConfig("Friend/"+player.getUniqueId().toString()+".yml");
					if(FriendsList.contains("Name")==false)
					{
						s.SP(player, Sound.VILLAGER_NO, 1.0F, 1.0F);
						player.sendMessage(ChatColor.LIGHT_PURPLE+"[���� ��û] : ���� ��û ��ȣ�� ���� �� �ִ� ģ���� �����ϴ�!");
					}
					else
					{
						Object[] FList = FriendsList.getConfigurationSection("Friends").getKeys(false).toArray();
						if(FList.length == 0)
						{
							s.SP(player, Sound.VILLAGER_NO, 1.0F, 1.0F);
							player.sendMessage(ChatColor.LIGHT_PURPLE+"[���� ��û] : ���� ��û ��ȣ�� ���� �� �ִ� ģ���� �����ϴ�!");
						}
						else
						{
							boolean exitFriend = false;
							for(int count = 0; count < FList.length; count++)
							{
								Player friend = Bukkit.getPlayer(FList[count].toString());
								if(friend!=null)
									if(friend.isOnline())
									{
										s.SP(friend, Sound.VILLAGER_DEATH, 0.4F, 0.5F);
										friend.sendMessage(ChatColor.LIGHT_PURPLE+"[���� ��û] : "+ChatColor.YELLOW+player.getName()+ChatColor.LIGHT_PURPLE+"������ ���� ���� ��û�� ���Խ��ϴ�! (���� : "+player.getLocation().getWorld().getName() + ", XYZ : " + (int)(player.getLocation().getX())+","+(int)(player.getLocation().getY())+","+(int)(player.getLocation().getZ())+")");
										exitFriend = true;
									}
							}
							if(exitFriend)
							{
								s.SP(player, Sound.SKELETON_WALK, 1.0F, 1.0F);
								player.sendMessage(ChatColor.LIGHT_PURPLE+"[���� ��û] : ������ ģ���鿡�� ���� ��û ��ȣ�� ���½��ϴ�!");
							}
							else
							{
								s.SP(player, Sound.VILLAGER_NO, 1.0F, 1.0F);
								player.sendMessage(ChatColor.LIGHT_PURPLE+"[���� ��û] : ���� ��û ��ȣ�� ���� �� �ִ� ģ���� �����ϴ�!");
							}
						}
					}
				}
			}
			break;
		case 16:
			{
			  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager Config =YC.getNewConfig("config.yml");
				ItemStack item = Config.getItemStack("Death.ReviveItem");
				if(item == null)
				{
					s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage(ChatColor.RED+"[SYSTEM] : ��Ȱ �������� ���� ��Ȱ�� �� �����ϴ�!");
					OpenReviveSelectGUI(player);
				}
				else
				{
					if(new GoldBigDragon_RPG.Util.PlayerUtil().deleteItem(player, item, item.getAmount()))
					{
						new GoldBigDragon_RPG.Effect.Corpse().RemoveCorpse(player.getName());
						player.setGameMode(GameMode.SURVIVAL);
						player.closeInventory();
						Location l = player.getLocation();
						l.add(0, 1, 0);
						player.teleport(l);
						for(short countta=0;countta<210;countta++)
							new GoldBigDragon_RPG.Effect.Particle().PL(player.getLocation(), org.bukkit.Effect.SMOKE, new GoldBigDragon_RPG.Util.Number().RandomNum(0, 14));
						s.SL(player.getLocation(), Sound.BLAZE_BREATH, 0.5F, 1.8F);
				    	if(Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI") == true)
				    		new OtherPlugins.NoteBlockAPIMain().Stop(player);
						Penalty(player, Config.getString("Death.Spawn_Item.SetHealth"), Config.getString("Death.Spawn_Item.PenaltyEXP"), Config.getString("Death.Spawn_Item.PenaltyMoney"));
						return;
					}
					else
					{
						s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage(ChatColor.RED+"[SYSTEM] : ��Ȱ �������� �����Ͽ� ��Ȱ�� �� �����ϴ�!");
						return;
					}
				}
			}
			break;
		}
		player.closeInventory();
		return;
	}
	
	public void ReviveAtLastVisitedArea(Player player)
	{
		player.setGameMode(GameMode.SURVIVAL);
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		new GoldBigDragon_RPG.Util.ETC().UpdatePlayerHPMP(player);
		
		YamlManager AreaList = YC.getNewConfig("Area/AreaList.yml");
		if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getETC_LastVisited() != null)
		{
			if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getETC_LastVisited().compareTo("null")==0)
				player.teleport(player.getWorld().getSpawnLocation());
			else
			{
				String respawnCity = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getETC_LastVisited();
				Object[] arealist = AreaList.getConfigurationSection("").getKeys(false).toArray();
				for(short count =0; count <arealist.length;count++)
				{
					if(arealist[count].toString().compareTo(respawnCity) == 0)
					{
						if(AreaList.getBoolean(arealist[count].toString()+".SpawnPoint") == true)
						{
							String world = AreaList.getString(arealist[count].toString()+".World");
							double X = AreaList.getDouble(arealist[count].toString()+".SpawnLocation.X");
							float Y = (float) AreaList.getDouble(arealist[count].toString()+".SpawnLocation.Y");
							double Z = AreaList.getDouble(arealist[count].toString()+".SpawnLocation.Z");
							double Pitch = AreaList.getDouble(arealist[count].toString()+".SpawnLocation.Pitch");
							double Yaw = AreaList.getDouble(arealist[count].toString()+".SpawnLocation.Yaw");

						  	YamlManager Config = YC.getNewConfig("config.yml");
							Penalty(player, Config.getString("Death.Spawn_Home.SetHealth"), Config.getString("Death.Spawn_Home.PenaltyEXP"), Config.getString("Death.Spawn_Home.PenaltyMoney"));
							new GoldBigDragon_RPG.Event.Teleport().TeleportPlayer(player, world, X, Y, Z, (float)Yaw, (float)Pitch);
							return;
						}
					}
				}
				player.teleport(player.getWorld().getSpawnLocation());
			}
		}
		else
			player.teleport(player.getWorld().getSpawnLocation());
	  	YamlManager Config = YC.getNewConfig("config.yml");
		Penalty(player, Config.getString("Death.Spawn_Home.SetHealth"), Config.getString("Death.Spawn_Home.PenaltyEXP"), Config.getString("Death.Spawn_Home.PenaltyMoney"));
	}
	
	public void ReviveAtDeadPoint(Player player)
	{
		player.setGameMode(GameMode.SURVIVAL);
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		new GoldBigDragon_RPG.Util.ETC().UpdatePlayerHPMP(player);
	  	YamlManager Config = YC.getNewConfig("config.yml");
		Penalty(player, Config.getString("Death.Spawn_Here.SetHealth"), Config.getString("Death.Spawn_Here.PenaltyEXP"), Config.getString("Death.Spawn_Here.PenaltyMoney"));
	}
	
	public void Penalty(Player player, String Health, String EXP, String Money)
	{
		byte HealthPercent = Byte.parseByte(Health.replace("%", ""));
		byte EXPPercent = Byte.parseByte(EXP.replace("%", ""));
		byte MoneyPercent = Byte.parseByte(Money.replace("%", ""));
		if(HealthPercent < 0)
			HealthPercent = 1;
		if(HealthPercent > 100)
			HealthPercent = 100;
		if(EXPPercent < 0)
			EXPPercent = 0;
		if(EXPPercent > 100)
			EXPPercent = 100;
		if(MoneyPercent < 0)
			MoneyPercent = 0;
		if(MoneyPercent > 100)
			MoneyPercent = 100;
		player.setHealth((player.getMaxHealth()/100)*HealthPercent);
		long pEXP = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_EXP();
		long pMaxEXP = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_MaxEXP();
	  	if(pEXP - ((pMaxEXP/100)*EXPPercent)<pMaxEXP*-1)
	  		GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_EXP(-1 * pMaxEXP);
	  	else
	  		GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setStat_EXP(pEXP - ((pMaxEXP/100)*EXPPercent));
	  	long pMoney = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money();
  		GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP((((pMoney/100)*MoneyPercent) * -1), 0, false);
  		GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setDeath(false);
	}
}
