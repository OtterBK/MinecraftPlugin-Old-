package GoldBigDragon_RPG.Dungeon;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import GoldBigDragon_RPG.Main.ServerOption;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class DungeonWork
{

	public void DungeonClear(Player player, Location BossLoc)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getDungeon_Enter() +"/Option.yml");
		
		int Reward_M = DungeonConfig.getInt("Reward.Money");
		int Reward_E = DungeonConfig.getInt("Reward.EXP");
		
		ItemStack item = new ItemStack(292);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.GREEN+""+ChatColor.BLACK+""+ChatColor.GREEN+""+ChatColor.WHITE+""+ChatColor.BOLD+"[���� ���� ����]");
		im.setLore(Arrays.asList("",ChatColor.WHITE+"���� ���ڸ� �� �� �ִ�",ChatColor.WHITE+"���� �����̴�."));
		im.addEnchant(Enchantment.DURABILITY, 6000, true);
		item.setItemMeta(im);

		if(ServerOption.PartyJoiner.containsKey(player))
		{
			Player[] partyMember = ServerOption.Party.get(ServerOption.PartyJoiner.get(player)).getMember();
			for(short count = 0; count < partyMember.length; count++)
			{
				Long target = ServerOption.PlayerList.get(partyMember[count].getUniqueId().toString()).getDungeon_UTC();
				if(target.equals(ServerOption.PlayerList.get(player.getUniqueId().toString()).getDungeon_UTC()))
				{
					{
						new GoldBigDragon_RPG.Effect.Sound().SP(partyMember[count], Sound.LEVEL_UP, 1.0F, 1.8F);
						new GoldBigDragon_RPG.Util.PlayerUtil().giveItemDrop(partyMember[count], item, partyMember[count].getLocation());
						new GoldBigDragon_RPG.Util.PlayerUtil().DungeonClear(partyMember[count], Reward_M, Reward_E);
					}
				}
			}
		}
		else
		{
			new GoldBigDragon_RPG.Effect.Sound().SP(player, Sound.LEVEL_UP, 1.0F, 1.8F);
			new GoldBigDragon_RPG.Util.PlayerUtil().giveItemDrop(player, item, player.getLocation());
			new GoldBigDragon_RPG.Util.PlayerUtil().DungeonClear(player, Reward_M, Reward_E);
		}
		
		BossLoc.add(3, -1, -30);
		new GoldBigDragon_RPG.Effect.Sound().IronDoorOpening(BossLoc);
		Block block = null;
    	for(int count = 0; count < 7; count++)
    	{
    		for(int count2 = 0; count2 < 5; count2++)
    		{
    			block = BossLoc.add(-1, 0, 0).getBlock();
				block.setType(Material.AIR,true);
    		}
			block = BossLoc.add(5, 1, 0).getBlock();
    	}
		BossLoc.add(0, -7, -1);
    	for(int count = 0; count < 7; count++)
    	{
    		for(int count2 = 0; count2 < 5; count2++)
    		{
    			block = BossLoc.add(-1, 0, 0).getBlock();
				block.setType(Material.AIR,true);
    		}
			block = BossLoc.add(5, 1, 0).getBlock();
    	}
	}
	
	public void BossRoomOpen(Player player, Location BossLoc, String DungeonName)
	{
		YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager DungeonConfig = YC.getNewConfig("Dungeon/Dungeon/"+GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getDungeon_Enter() +"/Option.yml");
		int SoundTrack = DungeonConfig.getInt("BGM.BOSS");
		
		if(Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI") == true)
		{
			if(ServerOption.PartyJoiner.containsKey(player))
			{
				Player[] partyMember = ServerOption.Party.get(ServerOption.PartyJoiner.get(player)).getMember();
				for(short count = 0; count < partyMember.length; count++)
				{
					if(ServerOption.PlayerList.get(partyMember[count].getUniqueId().toString()).isBgmOn())
					{
						Long target = ServerOption.PlayerList.get(partyMember[count].getUniqueId().toString()).getDungeon_UTC();
						if(target.equals(ServerOption.PlayerList.get(player.getUniqueId().toString()).getDungeon_UTC()))
						{
		        			new OtherPlugins.NoteBlockAPIMain().Stop(partyMember[count]);
		        			new OtherPlugins.NoteBlockAPIMain().Play(partyMember[count], SoundTrack);	
						}
					}
				}
			}
			else if(ServerOption.PlayerList.get(player.getUniqueId().toString()).isBgmOn())
			{
    			new OtherPlugins.NoteBlockAPIMain().Stop(player);
    			new OtherPlugins.NoteBlockAPIMain().Play(player, SoundTrack);	
			}
		}
		
		YamlManager MonsterConfig = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName +"/Monster.yml");
    	Object[] MobList = null;
		if(MonsterConfig.getConfigurationSection("Boss").getKeys(false).size()!=0)
			MobList = MonsterConfig.getConfigurationSection("Boss").getKeys(false).toArray();

		int XYZloc[] = new int[3];
		XYZloc[0] = (int) BossLoc.getX();
		XYZloc[1] = (int) BossLoc.getY();
		XYZloc[2] = (int) BossLoc.getZ();
		byte GroupNumber = (byte) new GoldBigDragon_RPG.Util.Number().RandomNum(0, 15);
		char Group = '0';
		switch(GroupNumber)
		{
			case 0 : Group = '0'; break;
			case 1 : Group = '1'; break;
			case 2 : Group = '2'; break;
			case 3 : Group = '3'; break;
			case 4 : Group = '4'; break;
			case 5 : Group = '5'; break;
			case 6 : Group = '6'; break;
			case 7 : Group = '7'; break;
			case 8 : Group = '8'; break;
			case 9 : Group = '9'; break;
			case 10 : Group = 'a'; break;
			case 11 : Group = 'b'; break;
			case 12 : Group = 'c'; break;
			case 13 : Group = 'd'; break;
			case 14 : Group = 'e'; break;
			case 15 : Group = 'f'; break;
		}
		
    	if(MobList ==null || MobList.length == 0)
    		DungeonClear(player, BossLoc);
    	else
    	{
    		GoldBigDragon_RPG.Monster.MonsterSpawn MC = new GoldBigDragon_RPG.Monster.MonsterSpawn();
    		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
    		for(int count = 0; count < MobList.length; count++)
    		{
    			BossLoc.add(0, 0.2, 0);
    			s.SL(BossLoc, Sound.WITHER_DEATH, 1.3F, 1.8F);
    			MC.SpawnMob(BossLoc, MonsterConfig.getString("Boss."+MobList[count].toString()),(byte) 4, XYZloc, Group, true);
    		}
		}
	}
	
	public void EraseAllDungeonKey(Player player, boolean isDrop)
	{
		ItemStack item = new ItemStack(292);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.GREEN+""+ChatColor.BLACK+""+ChatColor.GREEN+""+ChatColor.WHITE+""+ChatColor.BOLD+"[���� �� ����]");
		im.setLore(Arrays.asList("",ChatColor.WHITE+"���� ���� �� �� �ִ�",ChatColor.WHITE+"���� �����̴�."));
		im.addEnchant(Enchantment.DURABILITY, 6000, true);
		item.setItemMeta(im);
		if(isDrop)
			new GoldBigDragon_RPG.Util.PlayerUtil().dropItem(player, item, true);
		else
			new GoldBigDragon_RPG.Util.PlayerUtil().deleteItem(player, item, -1);
		
		item = new ItemStack(292);
		im = item.getItemMeta();
		im.setDisplayName(ChatColor.GREEN+""+ChatColor.BLACK+""+ChatColor.GREEN+""+ChatColor.WHITE+""+ChatColor.BOLD+"[���� ���� ����]");
		im.setLore(Arrays.asList("",ChatColor.WHITE+"���� ���ڸ� �� �� �ִ�",ChatColor.WHITE+"���� �����̴�."));
		im.addEnchant(Enchantment.DURABILITY, 6000, true);
		item.setItemMeta(im);
		new GoldBigDragon_RPG.Util.PlayerUtil().dropItem(player, item, true);
		
		if(isDrop)
			new GoldBigDragon_RPG.Util.PlayerUtil().dropItem(player, item, true);
		else
			new GoldBigDragon_RPG.Util.PlayerUtil().deleteItem(player, item, -1);
	}

	public void MonsterSpawn(Location loc)
	{
		Block SB = new Location(loc.getWorld(),loc.getX(),loc.getY()+12,loc.getZ()).getBlock();
        Sign SignBlock = (Sign) SB.getState();
        String DungeonName = SignBlock.getLine(2);

	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager MonsterConfig = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Monster.yml");
    	Object[] MobList = null;
    	String ListName = "Middle";
    	byte randomLevel = (byte) new GoldBigDragon_RPG.Util.Number().RandomNum(0, 3);
    	if(randomLevel<=2)
    	{
    		if(MonsterConfig.getConfigurationSection("Middle").getKeys(false).size()==0)
    			if(MonsterConfig.getConfigurationSection("High").getKeys(false).size()==0)
	        		if(MonsterConfig.getConfigurationSection("Normal").getKeys(false).size()==0)
	        			MobList = null;
		        	else
		        	{
		        		ListName = "Normal";
		        		MobList = MonsterConfig.getConfigurationSection("Normal").getKeys(false).toArray();
		        	}
    			else
    			{
	        		ListName = "High";
    				MobList = MonsterConfig.getConfigurationSection("High").getKeys(false).toArray();
    			}
    		else
    		{
        		ListName = "Middle";
    			MobList = MonsterConfig.getConfigurationSection("Middle").getKeys(false).toArray();
    		}
    	}
    	else
    	{
    		if(MonsterConfig.getConfigurationSection("High").getKeys(false).size()==0)
    			if(MonsterConfig.getConfigurationSection("Middle").getKeys(false).size()==0)
	        		if(MonsterConfig.getConfigurationSection("Normal").getKeys(false).size()==0)
	        			MobList = null;
		        	else
		        	{
		        		ListName = "Normal";
		        		MobList = MonsterConfig.getConfigurationSection("Normal").getKeys(false).toArray();
		        	}
    			else
    			{
	        		ListName = "Middle";
    				MobList = MonsterConfig.getConfigurationSection("Middle").getKeys(false).toArray();
    			}
    		else
    		{
        		ListName = "High";
    			MobList = MonsterConfig.getConfigurationSection("High").getKeys(false).toArray();
    		}
    	}
    	if(MobList!=null)
    	{
        	String Mob = MobList[new GoldBigDragon_RPG.Util.Number().RandomNum(0, MobList.length-1)].toString();
    		if(Mob != null)
    		{
        		randomLevel = (byte) new GoldBigDragon_RPG.Util.Number().RandomNum(0, 5);
        		switch(randomLevel)
        		{
        		case 0://2ȸ ����
        			break;
        		default://1ȸ ����
        			break;
        		}
        		
    			int[] XYZLoc = new int[3];
    			XYZLoc[0] = (int) loc.getX();
    			XYZLoc[1] = (int) loc.getY();
    			XYZLoc[2] = (int) loc.getZ();
    			byte GroupNumber = (byte) new GoldBigDragon_RPG.Util.Number().RandomNum(0, 15);
    			char Group = '0';
    			switch(GroupNumber)
    			{
    				case 0 : Group = '0'; break;
    				case 1 : Group = '1'; break;
    				case 2 : Group = '2'; break;
    				case 3 : Group = '3'; break;
    				case 4 : Group = '4'; break;
    				case 5 : Group = '5'; break;
    				case 6 : Group = '6'; break;
    				case 7 : Group = '7'; break;
    				case 8 : Group = '8'; break;
    				case 9 : Group = '9'; break;
    				case 10 : Group = 'a'; break;
    				case 11 : Group = 'b'; break;
    				case 12 : Group = 'c'; break;
    				case 13 : Group = 'd'; break;
    				case 14 : Group = 'e'; break;
    				case 15 : Group = 'f'; break;
    			}
    			GoldBigDragon_RPG.Monster.MonsterSpawn MC = new GoldBigDragon_RPG.Monster.MonsterSpawn();
    			GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
    			loc.add(0,1,0);
        		for(int count = 0; count < 7; count++)
        		{
        			s.SL(loc, Sound.WITHER_DEATH, 1.3F, 1.8F);
        			MC.SpawnMob(loc, MonsterConfig.getString(ListName+"."+Mob),(byte) 1, XYZLoc, Group, true);
        			loc.add(0, 0.2, 0);
        		}
    			s.SL(loc, Sound.WITHER_DEATH, 1.3F, 1.8F);
    			MC.SpawnMob(loc, MonsterConfig.getString(ListName+"."+Mob),(byte) 3, XYZLoc, Group, true);	
    		}
    		else
    		{
    			Location blockLoc = new Location(loc.getWorld(), loc.getX(), loc.getY()+1, loc.getZ());
				ItemStack item = new ItemStack(292);
				ItemMeta im = item.getItemMeta();
				im.setDisplayName(ChatColor.GREEN+""+ChatColor.BLACK+""+ChatColor.GREEN+""+ChatColor.WHITE+""+ChatColor.BOLD+"[���� �� ����]");
				im.setLore(Arrays.asList("",ChatColor.WHITE+"���� ���� �� �� �ִ�",ChatColor.WHITE+"���� �����̴�."));
				im.addEnchant(Enchantment.DURABILITY, 6000, true);
				item.setItemMeta(im);
				new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(blockLoc, item);
				new GoldBigDragon_RPG.Dungeon.DungeonWork().DungeonTrapDoorWorker(loc, false);
    		}
    	}
        return;
	}
	
	public void DungeonInteract(PlayerInteractEvent event)
	{
		//�絿�� ��� ���ϰ� �ϱ�
		if(event.getPlayer().getItemInHand().getTypeId()>=325&&
			event.getPlayer().getItemInHand().getTypeId()<=327)
		{
			event.setCancelled(true);
			new GoldBigDragon_RPG.Effect.Sound().SP(event.getPlayer(), Sound.ORB_PICKUP, 1.0F, 1.8F);
			new GoldBigDragon_RPG.Effect.PacketSender().sendActionBar(event.getPlayer(), ChatColor.RED+""+ChatColor.BOLD+"[���������� �絿�� ����� �Ұ����մϴ�!]");
			return;
		}
		Block block = event.getClickedBlock();
		if(block==null)
			return;
		if(block.getType()==Material.AIR)
			return;
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		
		if(block.getType().getId()==146)//�� ����
		{
			if(TrapChestOpen(block))
			{
				event.setCancelled(true);
				byte howMuch = (byte) new GoldBigDragon_RPG.Util.Number().RandomNum(0, 8);
				for(byte count = 0; count < howMuch; count++)
					new GoldBigDragon_RPG.Util.PlayerUtil().addMoneyAndEXP(event.getPlayer() , new GoldBigDragon_RPG.Util.Number().RandomNum(0, 500), 0, block.getLocation(), true, false);
			}
		}
		
		else if(block.getType().getId()==95) //������ ����
			TrapGlassTouch(block, event.getPlayer());
		
		else if(block.getType().getId()==138)//���� Ż��� ��ȣ��
		{
			event.setCancelled(true);
			new GoldBigDragon_RPG.Dungeon.DungeonGUI().DungeonEXIT(event.getPlayer());
		}
		
		else if(block.getType().getId()==23) //���� �� ���豸��
		{
			Block SB = new Location(block.getWorld(),block.getX(),block.getY()+10,block.getZ()).getBlock();
			if(SB.getType()!=Material.WALL_SIGN)
				return;
			event.setCancelled(true);
			if(event.getClickedBlock().getLocation().add(0, 10, 0).getBlock() !=null)
			{
				if(event.getClickedBlock().getLocation().add(0,10,0).getBlock().getType() == Material.WALL_SIGN)
				{
			        Sign SignBlock = (Sign) event.getClickedBlock().getLocation().add(0, 10, 0).getBlock().getState();
			        String GridImage = SignBlock.getLine(1);
					if(GridImage.compareTo("��")==0||GridImage.compareTo("��")==0||GridImage.compareTo("��")==0||GridImage.compareTo("��")==0||GridImage.compareTo("��")==0)
					{
						ItemStack item = new ItemStack(292);
						ItemMeta im = item.getItemMeta();
						im.setDisplayName(ChatColor.GREEN+""+ChatColor.BLACK+""+ChatColor.GREEN+""+ChatColor.WHITE+""+ChatColor.BOLD+"[���� �� ����]");
						im.setLore(Arrays.asList("",ChatColor.WHITE+"���� ���� �� �� �ִ�",ChatColor.WHITE+"���� �����̴�."));
						im.addEnchant(Enchantment.DURABILITY, 6000, true);
						item.setItemMeta(im);
						if(new GoldBigDragon_RPG.Util.PlayerUtil().deleteItem(event.getPlayer(), item, 1))
						{
				        	Location loc = event.getClickedBlock().getLocation();
							String Title = "\'"+ChatColor.BLUE+"\'";
							String SubTitle  = "\'" +ChatColor.WHITE +"���� �� ���踦 ����Ͽ� ���� ������.\'";
				        	new GoldBigDragon_RPG.Effect.PacketSender().sendTitleSubTitle(event.getPlayer(), Title, SubTitle, (byte)1, (byte)2, (byte)1);
							s.IronDoorOpening(loc);
					        switch(GridImage)
					        {
					        case "��":
					        case "��":
					        	loc.add(-2, -2, 0);
				    			block = loc.getBlock();
				    			block.setType(Material.AIR,true);
						    	for(int count = 0; count < 6; count++)
						    	{
						    		for(int count2 = 0; count2 < 5; count2++)
						    		{
						    			block.setType(Material.AIR,true);
						    			block = loc.add(1, 0, 0).getBlock();
						    		}
									block = loc.add(-5, 1, 0).getBlock();
						    	}
					        	break;
					        case "��":
					        	loc.add(-2, -2, 0);
				    			block = loc.getBlock();
				    			block.setType(Material.AIR,true);
						    	for(int count = 0; count < 6; count++)
						    	{
						    		for(int count2 = 0; count2 < 5; count2++)
						    		{
						    			block.setType(Material.AIR,true);
						    			block = loc.add(1, 0, 0).getBlock();
						    		}
									block = loc.add(-5, 1, 0).getBlock();
						    	}
						    	loc = event.getClickedBlock().getLocation().add(-2, -2, -1);
				    			block = loc.getBlock();
				    			block.setType(Material.AIR,true);
						    	for(int count = 0; count < 6; count++)
						    	{
						    		for(int count2 = 0; count2 < 5; count2++)
						    		{
						    			block.setType(Material.AIR,true);
						    			block = loc.add(1, 0, 0).getBlock();
						    		}
									block = loc.add(-5, 1, 0).getBlock();
						    	}
						    	loc = event.getClickedBlock().getLocation().add(0, -1 ,-33);
								BossRoomOpen(event.getPlayer(), loc, SignBlock.getLine(2));
					        	break;
					        case "��":
					        case "��":
					        	loc.add(0, -2, -2);
				    			block = loc.getBlock();
				    			block.setType(Material.AIR,true);
						    	for(int count = 0; count < 6; count++)
						    	{
						    		for(int count2 = 0; count2 < 5; count2++)
						    		{
						    			block.setType(Material.AIR,true);
						    			block = loc.add(0, 0, 1).getBlock();
						    		}
									block = loc.add(0, 1, -5).getBlock();
						    	}
					        	break;
					        }
					        return;
						}
						else
						{
							String Title = "\'"+ChatColor.BLUE+"\'";
							String SubTitle  = "\'" +ChatColor.WHITE +"���� ���� ���ؼ��� ���谡 �ʿ��� �� ����...\'";
				        	new GoldBigDragon_RPG.Effect.PacketSender().sendTitleSubTitle(event.getPlayer(), Title, SubTitle, (byte)1, (byte)2, (byte)1);
						}
					}
					else
					{
						String Title = "\'"+ChatColor.BLUE+"\'";
						String SubTitle  = "\'" +ChatColor.WHITE +"����� �� �� �ִ� ���� �ƴ� �� ����...\'";
			        	new GoldBigDragon_RPG.Effect.PacketSender().sendTitleSubTitle(event.getPlayer(), Title, SubTitle, (byte)1, (byte)2, (byte)1);
			        	return;
					}
				}
			}
		}
		else if(block.getType().getId() == 54) //�̹� �� �Ϲ� ����
		{
			s.SL(block.getLocation().add(0,2,0), Sound.CHEST_OPEN, 0.5F, 1.8F);
			event.setCancelled(true);
			block.setType(Material.AIR);
			ItemStack item = new ItemStack(292);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(ChatColor.GREEN+""+ChatColor.BLACK+""+ChatColor.GREEN+""+ChatColor.WHITE+""+ChatColor.BOLD+"[���� �� ����]");
			im.setLore(Arrays.asList("",ChatColor.WHITE+"���� ���� �� �� �ִ�",ChatColor.WHITE+"���� �����̴�."));
			im.addEnchant(Enchantment.DURABILITY, 6000, true);
			item.setItemMeta(im);
			s.SL(block.getLocation().add(0,2,0), Sound.ORB_PICKUP, 1.5F, 1.8F);
			new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(block.getLocation().add(0.5,1,0.5), item);
			byte howMuch = (byte) new GoldBigDragon_RPG.Util.Number().RandomNum(0, 4);
			for(byte count = 0; count < howMuch; count++)
				new GoldBigDragon_RPG.Util.PlayerUtil().addMoneyAndEXP(event.getPlayer() , new GoldBigDragon_RPG.Util.Number().RandomNum(0, 500), 0, block.getLocation(), true, false);
		}
		else if(block.getType().getId() == 130) //���� ����
		{
			Block SB = new Location(block.getWorld(),block.getX(),block.getY()+12,block.getZ()).getBlock();
			if(SB.getType()!=Material.WALL_SIGN)
				return;
			event.setCancelled(true);
	        Player player = event.getPlayer();
			ItemStack item = new ItemStack(292);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(ChatColor.GREEN+""+ChatColor.BLACK+""+ChatColor.GREEN+""+ChatColor.WHITE+""+ChatColor.BOLD+"[���� ���� ����]");
			im.setLore(Arrays.asList("",ChatColor.WHITE+"���� ���ڸ� �� �� �ִ�",ChatColor.WHITE+"���� �����̴�."));
			im.addEnchant(Enchantment.DURABILITY, 6000, true);
			item.setItemMeta(im);
			if(new GoldBigDragon_RPG.Util.PlayerUtil().deleteItem(player, item, 1))
			{
				event.getClickedBlock().setType(Material.AIR, true);
				s.SL(event.getClickedBlock().getLocation(), Sound.CHEST_OPEN, 1.0F, 1.0F);
		        Sign SignBlock = (Sign) SB.getState();
		        String DungeonName = SignBlock.getLine(2);
			  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager RewardConfig = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Reward.yml");
				
				boolean treasureGet = false;
				int luck = new GoldBigDragon_RPG.Util.Number().RandomNum(0, 7);
				item = RewardConfig.getItemStack("100."+luck);
				if(item!=null)
				{
					treasureGet = true;
					new GoldBigDragon_RPG.Util.PlayerUtil().giveItemDrop(player, item, event.getClickedBlock().getLocation());
				}
				luck = new GoldBigDragon_RPG.Util.Number().RandomNum(1, 10);
				if(luck != 10)
				{
					int count = new GoldBigDragon_RPG.Util.Number().RandomNum(0, 7);
					item = RewardConfig.getItemStack("90."+count);
					if(item!=null)
					{
						treasureGet = true;
						new GoldBigDragon_RPG.Util.PlayerUtil().giveItemDrop(player, item, event.getClickedBlock().getLocation());
					}
				}
				luck = new GoldBigDragon_RPG.Util.Number().RandomNum(1, 10);
				if(luck <= 5)
				{
					int count = new GoldBigDragon_RPG.Util.Number().RandomNum(0, 7);
					item = RewardConfig.getItemStack("50."+count);
					if(item!=null)
					{
						treasureGet = true;
						new GoldBigDragon_RPG.Util.PlayerUtil().giveItemDrop(player, item, event.getClickedBlock().getLocation());
					}
				}
				luck = new GoldBigDragon_RPG.Util.Number().RandomNum(1, 10);
				if(luck == 1)
				{
					int count = new GoldBigDragon_RPG.Util.Number().RandomNum(0, 7);
					item = RewardConfig.getItemStack("10."+count);
					if(item!=null)
					{
						treasureGet = true;
						new GoldBigDragon_RPG.Util.PlayerUtil().giveItemDrop(player, item, event.getClickedBlock().getLocation());
					}
				}
				luck = new GoldBigDragon_RPG.Util.Number().RandomNum(1, 100);
				if(luck == 1)
				{
					int count = new GoldBigDragon_RPG.Util.Number().RandomNum(0, 7);
					item = RewardConfig.getItemStack("1."+count);
					if(item!=null)
					{
						treasureGet = true;
						new GoldBigDragon_RPG.Util.PlayerUtil().giveItemDrop(player, item, event.getClickedBlock().getLocation());
					}
				}
				luck = new GoldBigDragon_RPG.Util.Number().RandomNum(1, 1000);
				if(luck == 1)
				{
					int count = new GoldBigDragon_RPG.Util.Number().RandomNum(0, 7);
					item = RewardConfig.getItemStack("0."+count);
					if(item!=null)
					{
						treasureGet = true;
						new GoldBigDragon_RPG.Util.PlayerUtil().giveItemDrop(player, item, event.getClickedBlock().getLocation());
					}
				}
				
				if(treasureGet==false)
					new GoldBigDragon_RPG.Effect.PacketSender().sendActionBar(player, ChatColor.RED+""+ChatColor.BOLD+"[��! ���� ��ȸ��...]");
			}
			else
			{
				s.SP(player, Sound.ZOMBIE_METAL, 1.0F, 0.5F);
				new GoldBigDragon_RPG.Effect.PacketSender().sendActionBar(player, ChatColor.WHITE+""+ChatColor.BOLD+"[���ڿ� �´� ���谡 �����ϴ�!]");
			}
		}
		return;
	}
	
	public boolean TrapChestOpen(Block block)
	{
		Block SB = new Location(block.getWorld(),block.getX(),block.getY()+12,block.getZ()).getBlock();
		if(SB.getType()!=Material.WALL_SIGN)
			return false;
		GoldBigDragon_RPG.Monster.MonsterSpawn MC = new GoldBigDragon_RPG.Monster.MonsterSpawn();
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
        Sign SignBlock = (Sign) SB.getState();
        String GridImage = SignBlock.getLine(1);
        String DungeonName = SignBlock.getLine(2);
        Location loc = new Location(block.getWorld(),block.getX(),block.getY(),block.getZ());

	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager MonsterConfig = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Monster.yml");
    	Object[] MobList = null;
    	String ListName = "Normal";
    	byte randomLevel = (byte) new GoldBigDragon_RPG.Util.Number().RandomNum(0, 3);
    	if(randomLevel <= 1)
    	{
    		if(MonsterConfig.getConfigurationSection("Normal").getKeys(false).size()==0)
        		if(MonsterConfig.getConfigurationSection("Middle").getKeys(false).size()==0)
        			MobList = null;
	        	else
	        	{
	        		ListName = "Middle";
	        		MobList = MonsterConfig.getConfigurationSection("Middle").getKeys(false).toArray();
	        	}
        	else
        	{
        		ListName = "Normal";
        		MobList = MonsterConfig.getConfigurationSection("Normal").getKeys(false).toArray();
        	}
    	}
    	else if(randomLevel==2)
    	{
    		if(MonsterConfig.getConfigurationSection("Middle").getKeys(false).size()==0)
    			if(MonsterConfig.getConfigurationSection("High").getKeys(false).size()==0)
	        		if(MonsterConfig.getConfigurationSection("Normal").getKeys(false).size()==0)
	        			MobList = null;
		        	else
		        		MobList = MonsterConfig.getConfigurationSection("Normal").getKeys(false).toArray();
    			else
    			{
	        		ListName = "High";
    				MobList = MonsterConfig.getConfigurationSection("High").getKeys(false).toArray();
    			}
    		else
    		{
        		ListName = "Middle";
    			MobList = MonsterConfig.getConfigurationSection("Middle").getKeys(false).toArray();
    		}
    	}
    	else
    	{
    		if(MonsterConfig.getConfigurationSection("High").getKeys(false).size()==0)
    			if(MonsterConfig.getConfigurationSection("Middle").getKeys(false).size()==0)
	        		if(MonsterConfig.getConfigurationSection("Normal").getKeys(false).size()==0)
	        			MobList = null;
		        	else
		        		MobList = MonsterConfig.getConfigurationSection("Normal").getKeys(false).toArray();
    			else
    			{
	        		ListName = "Middle";
    				MobList = MonsterConfig.getConfigurationSection("Middle").getKeys(false).toArray();
    			}
    		else
    		{
        		ListName = "High";
    			MobList = MonsterConfig.getConfigurationSection("High").getKeys(false).toArray();
    		}
    	}
    	if(MobList!=null && MobList.length != 0)
    	{
        	String Mob = MobList[new GoldBigDragon_RPG.Util.Number().RandomNum(0, MobList.length-1)].toString();
    		if(Mob != null)
    		{
            	if(GridImage.compareTo("��")==0)
            	{
        			s.SL(loc, Sound.CHEST_OPEN, 1.3F, 1.8F);
            		MC.SpawnMob(loc, MonsterConfig.getString(ListName+"."+Mob),(byte)-1,null, (char) -1, false);
            	}
            	else
            	{
            		randomLevel = (byte) new GoldBigDragon_RPG.Util.Number().RandomNum(0, 5);
            		switch(randomLevel)
            		{
            		case 0://2ȸ ����
            			break;
            		default://1ȸ ����
            			break;
            		}
            		
    				DungeonTrapDoorWorker(loc, true);
    				int[] XYZLoc = new int[3];
    				XYZLoc[0] = block.getX();
    				XYZLoc[1] = block.getY();
    				XYZLoc[2] = block.getZ();
    				byte RoomChallenge = (byte) new GoldBigDragon_RPG.Util.Number().RandomNum(1, 5);
    				byte GroupNumber = (byte) new GoldBigDragon_RPG.Util.Number().RandomNum(0, 15);
    				char Group = '0';
    				switch(GroupNumber)
    				{
        				case 0 : Group = '0'; break;
        				case 1 : Group = '1'; break;
        				case 2 : Group = '2'; break;
        				case 3 : Group = '3'; break;
        				case 4 : Group = '4'; break;
        				case 5 : Group = '5'; break;
        				case 6 : Group = '6'; break;
        				case 7 : Group = '7'; break;
        				case 8 : Group = '8'; break;
        				case 9 : Group = '9'; break;
        				case 10 : Group = 'a'; break;
        				case 11 : Group = 'b'; break;
        				case 12 : Group = 'c'; break;
        				case 13 : Group = 'd'; break;
        				case 14 : Group = 'e'; break;
        				case 15 : Group = 'f'; break;
    				}
        			loc.add(0, 1, 0);
    				if(RoomChallenge <= 2)
    				{
                		for(int count = 0; count < 8; count++)
                		{
                			s.SL(loc, Sound.WITHER_DEATH, 1.3F, 1.8F);
                			loc.add(0, 0.2, 0);
                			MC.SpawnMob(loc, MonsterConfig.getString(ListName+"."+Mob),(byte) 2, XYZLoc, Group, true);
                		}
    				}
    				else
    				{
                		for(int count = 0; count < 7; count++)
                		{
                			loc.add(0, 0.2, 0);
                			s.SL(loc, Sound.WITHER_DEATH, 1.3F, 1.8F);
                			MC.SpawnMob(loc, MonsterConfig.getString(ListName+"."+Mob),(byte) 1, XYZLoc, Group, true);
                		}
            			MC.SpawnMob(loc, MonsterConfig.getString(ListName+"."+Mob),(byte) 3, XYZLoc, Group, true);
    				}
            		/*
            		�Ϲ� �� ���� �� �����ǰ� �� ������ �ϱ�
            		���� ������ �ִ� ���Ϳ��Դ� �̸� �տ� Į�� �ڵ带 �ٿ� ǥ���Ѵ�.
            		Į���ڵ� ���� �迭�� ��-��-�� ���� �����Ѵ�.
            		��-��-��-�� = ���� ���� �༮
            		��-��-��-�� = �Ϲ� �༮
            		��-��-��-�� = ������ ���ϴ� ������, �ݰ� 25��� �̳��� ��˳���� ���� ���, ������ ���� ������.
            		������ ���� Ž���ϱ� ���ؼ� ����� öâ �߾��� ��ġ�� ���� ���Ǳ׿� ���� ��Ų��.
            		��-��-��-�� = ���� ���̺� ����
            		���� ���� & �ڵ�� ������ ���� �׷��� �����ϱ� ��������,
            		�� ���� �׷� �ڵ尡 ���� ���Ͱ� �ݰ� 20 �̳��� ���� ���, ���� ���̺갡 �����ų�
            		���� ������ �ȴ�. ���� �׷� �ڵ�� 0 ~ f ���� �����Ѵ�.
            		
            		���� ������ ���ؼ��� ���ʿ� ������ ���� ��ġ�� �˰� �־�� �ϴµ�,
            		X, Y, Z ��ǥ�� ���� �� �ξ�� �Ѵ�.
            		�����ϱ� ���� ���� ���Ͱ� ���� ���� �������ε�
            		�װ� ��ٷӴ�.
            		�տ� �������� ���ٸ� ���� ���� �ְ�, �ִٸ� �� �Ʒ��� ��ǥ�� ������ ��,
            		���� �� ����ϰ� �ٽ� ��ǥ�� ���ֹ����� ������, ������, ��, ���� ���� ���͵��� ������ �������� ����.
            		
            		
            		EntityDeath �̺�Ʈ����, ��ƼƼ�� ���� ���尡 Dungeon�� ��츸
            		���� ���� ������ ó���ϸ�, Ŀ���� ���� ���� �����͸� ã�� ����
            		���� 8ĳ���� ����Ʈ Ŀ���� ������ �����ϰ� �ѱ⵵�� �Ѵ�.
            		��� Entity Damage By Entity �̺�Ʈ������ �������� ���� ��츦 ��� �־�� �Ѵ�.
            		*/
            	}
    		}
    		else if(GridImage.compareTo("��")!=0)
    		{
    			Location blockLoc = new Location(block.getWorld(), block.getLocation().getX(), block.getLocation().getY()+1, block.getLocation().getZ());
				ItemStack item = new ItemStack(292);
				ItemMeta im = item.getItemMeta();
				im.setDisplayName(ChatColor.GREEN+""+ChatColor.BLACK+""+ChatColor.GREEN+""+ChatColor.WHITE+""+ChatColor.BOLD+"[���� �� ����]");
				im.setLore(Arrays.asList("",ChatColor.WHITE+"���� ���� �� �� �ִ�",ChatColor.WHITE+"���� �����̴�."));
				im.addEnchant(Enchantment.DURABILITY, 6000, true);
				item.setItemMeta(im);
				new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(blockLoc, item);
    		}
    	}
		else if(GridImage.compareTo("��")!=0)
		{
			Location blockLoc = new Location(block.getWorld(), block.getLocation().getX(), block.getLocation().getY()+1, block.getLocation().getZ());
			ItemStack item = new ItemStack(292);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(ChatColor.GREEN+""+ChatColor.BLACK+""+ChatColor.GREEN+""+ChatColor.WHITE+""+ChatColor.BOLD+"[���� �� ����]");
			im.setLore(Arrays.asList("",ChatColor.WHITE+"���� ���� �� �� �ִ�",ChatColor.WHITE+"���� �����̴�."));
			im.addEnchant(Enchantment.DURABILITY, 6000, true);
			item.setItemMeta(im);
			new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(blockLoc, item);
		}
		s.SL(block.getLocation(), org.bukkit.Sound.CHEST_OPEN, 1.0F, 0.5F);
        block.setTypeIdAndData(0, (byte)0, true);
        return true;
	}
	
	public void TrapGlassTouch(Block block, Player player)
	{
		Block SB = new Location(block.getWorld(),block.getX(),block.getY()+11,block.getZ()).getBlock();
		if(SB.getType()!=Material.WALL_SIGN)
			return;
		GoldBigDragon_RPG.Monster.MonsterSpawn MC = new GoldBigDragon_RPG.Monster.MonsterSpawn();
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		GoldBigDragon_RPG.Effect.Particle p = new GoldBigDragon_RPG.Effect.Particle();
		if(block.getData()==15||block.getData()==14||block.getData()==13||
			block.getData()==11||block.getData()==8)
		{
			switch(block.getData())
			{
    			case 15: block.setTypeIdAndData(95, (byte)7, true);break;
    			case 14: block.setTypeIdAndData(95, (byte)6, true);break;
    			case 13: block.setTypeIdAndData(95, (byte) 5, true);break;
    			case 11: block.setTypeIdAndData(95, (byte)3, true);break;
    			case 8: block.setTypeIdAndData(95, (byte)0, true);break;
			}
			for(int counter=0;counter<50;counter++)
				p.PL(block.getLocation(), org.bukkit.Effect.MAGIC_CRIT, 0);
			s.SL(block.getLocation(), org.bukkit.Sound.SUCCESSFUL_HIT, 1.0F, 0.5F);
		}
		else if(block.getData()==0||block.getData()==3||block.getData()==5||block.getData()==6||block.getData()==7)
		{
			for(int counter=0;counter<31;counter++)
				p.PL(block.getLocation(), org.bukkit.Effect.CRIT, 0);
			s.SL(block.getLocation(), org.bukkit.Sound.HURT_FLESH, 0.5F, 0.5F);
			return;
		}
		block = new Location(block.getWorld(),block.getX(),block.getY()-2,block.getZ()).getBlock();
		if(block.getType()==Material.STONE)
		{
			block = new Location(block.getWorld(),block.getX(),block.getY()+13,block.getZ()).getBlock();
	        Sign SignBlock = (Sign) block.getState();
	        String GridImage = SignBlock.getLine(1);
	        Location loc = new Location(block.getWorld(),block.getX(),block.getY(),block.getZ());
	        int Direction = Integer.parseInt(SignBlock.getLine(3));
			DungeonDoorRemover(player, GridImage.charAt(0), Direction, loc);
		}
		else
		{
			block = new Location(block.getWorld(),block.getX(),block.getY()+13,block.getZ()).getBlock();
	        Sign SignBlock = (Sign) block.getState();
	        String GridImage = SignBlock.getLine(1);
	        String DungeonName = SignBlock.getLine(2);
	        /*
	        Direction : 0 = ��
	        Direction : 1 = �ϵ�
	        Direction : 2 = ��
	        Direction : 3 = ����
	        Direction : 4 = ��
	        Direction : 5 = ����
	        Direction : 6 = ��
	        Direction : 7 = �ϼ�
	        Direction : 8 = �߾�
	         */
	        Location loc = new Location(block.getWorld(),block.getX(),block.getY(),block.getZ());
	        int Direction = Integer.parseInt(SignBlock.getLine(3));
	        int NowLevel = Integer.parseInt(SignBlock.getLine(0).split("/")[0]);
        	if(NowLevel<=0)
        		NowLevel = 1;
	        int MaxLevel = Integer.parseInt(SignBlock.getLine(0).split("/")[1]);
	        switch(Direction)
	        {
	        case 1:
	        	loc.add(-6, -12, 6);
	        	break;
	        case 3:
	        	loc.add(-6, -12, -6);
	        	break;
	        case 5:
	        	loc.add(6, -12, -6);
	        	break;
	        case 7:
	        	loc.add(6, -12, 6);
	        	break;
	        }
		  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
			YamlManager MonsterConfig = YC.getNewConfig("Dungeon/Dungeon/"+DungeonName+"/Monster.yml");
        	Object[] MobList = null;
        	String ListName = "Normal";
	        if(GridImage.compareTo("��")==0)
	        {
	        	if(MonsterConfig.getConfigurationSection("SubBoss").getKeys(false).size()==0)
	        		if(MonsterConfig.getConfigurationSection("High").getKeys(false).size()==0)
		        		if(MonsterConfig.getConfigurationSection("Middle").getKeys(false).size()==0)
			        		if(MonsterConfig.getConfigurationSection("Normal").getKeys(false).size()==0)
			        			MobList = null;
			        		else
			        			MobList = MonsterConfig.getConfigurationSection("Normal").getKeys(false).toArray();
		        		else
		        		{
		        			ListName = "Middle";
		        			MobList = MonsterConfig.getConfigurationSection("Middle").getKeys(false).toArray();
		        		}
	        		else
	        		{
	        			ListName = "High";
	        			MobList = MonsterConfig.getConfigurationSection("High").getKeys(false).toArray();
	        		}
	        	else
        		{
        			ListName = "SubBoss";
	        		MobList = MonsterConfig.getConfigurationSection("SubBoss").getKeys(false).toArray();
        		}
	        	if(MobList!=null)
	        	{
	        		for(int count = 0; count < 4; count++)
	        		{
	        			loc.add(new GoldBigDragon_RPG.Util.Number().RandomNum(-2, 2), 0.1*count, new GoldBigDragon_RPG.Util.Number().RandomNum(-2, 2));
	        			MC.SpawnMob(loc, MonsterConfig.getString(ListName+"."+MobList[new GoldBigDragon_RPG.Util.Number().RandomNum(0, MobList.length-1)].toString()), (byte)-1, null, (char) -1, false);
	        		}
	        	}
	        }
	        else
	        {
	        	if(MaxLevel < 5)
	        		MaxLevel = MaxLevel*20;
	        	else if(MaxLevel < 10)
	        		MaxLevel = MaxLevel*10;
	        	else if(MaxLevel < 20)
	        		MaxLevel = MaxLevel*5;
	        	else if(MaxLevel >= 100)
	        		MaxLevel = 100;
	        	if(MaxLevel/NowLevel>20)
	        	{
	        		if(MonsterConfig.getConfigurationSection("Normal").getKeys(false).size()==0)
		        		if(MonsterConfig.getConfigurationSection("Middle").getKeys(false).size()==0)
		        			MobList = null;
			        	else
			        	{
			        		ListName = "Middle";
			        		MobList = MonsterConfig.getConfigurationSection("Middle").getKeys(false).toArray();
			        	}
		        	else
		        	{
		        		ListName = "Normal";
		        		MobList = MonsterConfig.getConfigurationSection("Normal").getKeys(false).toArray();
		        	}
	        	}  	
	        	else if(MaxLevel/NowLevel>10)
	        	{
	        		if(MonsterConfig.getConfigurationSection("Middle").getKeys(false).size()==0)
	        			if(MonsterConfig.getConfigurationSection("High").getKeys(false).size()==0)
			        		if(MonsterConfig.getConfigurationSection("Normal").getKeys(false).size()==0)
			        			MobList = null;
				        	else
				        		MobList = MonsterConfig.getConfigurationSection("Normal").getKeys(false).toArray();
	        			else
	        			{
			        		ListName = "High";
	        				MobList = MonsterConfig.getConfigurationSection("High").getKeys(false).toArray();
	        			}
	        		else
	        		{
		        		ListName = "Middle";
	        			MobList = MonsterConfig.getConfigurationSection("Middle").getKeys(false).toArray();
	        		}
	        	}
	        	else
	        	{
	        		if(MonsterConfig.getConfigurationSection("High").getKeys(false).size()==0)
	        			if(MonsterConfig.getConfigurationSection("Middle").getKeys(false).size()==0)
			        		if(MonsterConfig.getConfigurationSection("Normal").getKeys(false).size()==0)
			        			MobList = null;
				        	else
				        		MobList = MonsterConfig.getConfigurationSection("Normal").getKeys(false).toArray();
	        			else
	        			{
			        		ListName = "Middle";
	        				MobList = MonsterConfig.getConfigurationSection("Middle").getKeys(false).toArray();
	        			}
	        		else
	        		{
		        		ListName = "High";
	        			MobList = MonsterConfig.getConfigurationSection("High").getKeys(false).toArray();
	        		}
	        	}
	        }
	        
        	if(MobList!=null)
        	{
        		for(int count = 0; count < 4; count++)
        		{
        			loc.add(new GoldBigDragon_RPG.Util.Number().RandomNum(-2, 2),0.1*count, new GoldBigDragon_RPG.Util.Number().RandomNum(-2, 2));
        			s.SL(loc, Sound.WITHER_DEATH, 1.3F, 1.8F);
        			MC.SpawnMob(loc, MonsterConfig.getString(ListName+"."+MobList[new GoldBigDragon_RPG.Util.Number().RandomNum(0, MobList.length-1)].toString()), (byte)-1, null, (char) -1, false);
        		}
        	}
		}
	}
	
	
	
	private void DungeonDoorRemover(Player player, char GridImage, int Direction, Location loc)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		Location Original = loc.add(0,-12,0);
		Original.setX(loc.getX());
		Original.setY(loc.getY());
		Original.setZ(loc.getZ());
		Block block = null;
		switch(GridImage)
        {
        case '��':
			switch(Direction)
	        {
		        case 1:
		        	loc.add(-3, 0, -4).getBlock();
		        	break;
		        case 3:
		        	loc.add(-3, 0, -16).getBlock();
		        	break;
		        case 5:
		        	loc.add(9, 0, -16).getBlock();
		        	break;
		        case 7:
		        	loc.add(9, 0, -4).getBlock();
		        	break;
	        }
			s.IronDoorOpening(loc);
        	for(int count = 0; count < 5; count++)
        	{
        		for(int count2 = 0; count2 < 5; count2++)
        		{
        			block = loc.add(-1, 0, 0).getBlock();
					block.setType(Material.AIR,true);
        		}
    			block = loc.add(5, 1, 0).getBlock();
        	}
        	break;
        case '��':
        	Location BossLoc = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
        	Location SignLoc = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
        	SignLoc.add(0, 12, 0);
	        Sign SignBlock = (Sign) SignLoc.getBlock().getState();
			switch(Direction)
	        {
		        case 1:
		        	loc = loc.add(-3, 0, -4);
		        	BossLoc = BossLoc.add(-6, 1, -37);
		        	break;
		        case 3:
		        	loc.add(-3, 0, -16).getBlock();
		        	BossLoc = BossLoc.add(-6, 1, -49);
		        	break;
		        case 5:
		        	loc.add(9, 0, -16).getBlock();
		        	BossLoc = BossLoc.add(6, 1, -49);
		        	break;
		        case 7:
		        	loc.add(9, 0, -4).getBlock();
		        	BossLoc = BossLoc.add(6, 1, -37);
		        	break;
	        }
			s.IronDoorOpening(loc);
			for(int c=0;c<2;c++)
			{
	        	for(int count = 0; count < 5; count++)
	        	{
	        		for(int count2 = 0; count2 < 5; count2++)
	        		{
	        			block = loc.add(-1, 0, 0).getBlock();
    					block.setType(Material.AIR,true);
	        		}
        			block = loc.add(5, 1, 0).getBlock();
	        	}
    			block = loc.add(0, -5, -1).getBlock();
			}
			BossRoomOpen(player, BossLoc, SignBlock.getLine(2));
        	break;
        case '��':
			switch(Direction)
	        {
	        case 1:
	        	loc.add(-3, 0, 16).getBlock();
	        	break;
	        case 3:
	        	loc.add(-3, 0, 4).getBlock();
	        	break;
	        case 5:
	        	loc.add(9, 0, 4).getBlock();
	        	break;
	        case 7:
	        	loc.add(9, 0, 16).getBlock();
	        	break;
	        }
			s.IronDoorOpening(loc);
        	for(int count = 0; count < 5; count++)
        	{
        		for(int count2 = 0; count2 < 5; count2++)
        		{
        			block = loc.add(-1, 0, 0).getBlock();
					block.setType(Material.AIR,true);
        		}
    			block = loc.add(5, 1, 0).getBlock();
        	}
        	break;
        case '��':
			switch(Direction)
	        {
		        case 1:
		        	loc.add(-16, 0, 9).getBlock();
		        	break;
		        case 3:
		        	loc.add(-16, 0, -3).getBlock();
		        	break;
		        case 5:
		        	loc.add(-4, 0, -3).getBlock();
		        	break;
		        case 7:
		        	loc.add(-4, 0, 9).getBlock();
		        	break;
	        }
			s.IronDoorOpening(loc);
        	for(int count = 0; count < 5; count++)
        	{
        		for(int count2 = 0; count2 < 5; count2++)
        		{
        			block = loc.add(0, 0, -1).getBlock();
					block.setType(Material.AIR,true);
        		}
    			block = loc.add(0, 1, 5).getBlock();
        	}
        	break;
        case '��':
			switch(Direction)
	        {
		        case 1:
		        	loc.add(4, 0, 9).getBlock();
		        	break;
		        case 3:
		        	loc.add(4, 0, -3).getBlock();
		        	break;
		        case 5:
		        	loc.add(16, 0, -3).getBlock();
		        	break;
		        case 7:
		        	loc.add(16, 0, 9).getBlock();
		        	break;
	        }
			s.IronDoorOpening(loc);
        	for(int count = 0; count < 5; count++)
        	{
        		for(int count2 = 0; count2 < 5; count2++)
        		{
        			block = loc.add(0, 0, -1).getBlock();
					block.setType(Material.AIR,true);
        		}
    			block = loc.add(0, 1, 5).getBlock();
        	}
        	break;
        }
	}
	
	public void DungeonTrapDoorWorker(Location loc, boolean isCreate)
	{
		Block block = null;
		Material M = Material.STAINED_GLASS_PANE;
		byte blockData = (byte)14;

		new GoldBigDragon_RPG.Effect.Sound().IronDoorOpening(loc);
		Location loc2;
		byte signX = 0;
		byte signZ = 0;
		byte addingX;
		byte addingZ;
		byte uppingX;
		byte uppingZ;
		for(byte directionLoop = 0; directionLoop < 4; directionLoop++)
		{
			signX = 0;
			signZ = 0;
			addingX = 0;
			addingZ = 0;
			uppingX = 0;
			uppingZ = 0;
			loc2 = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
			if(directionLoop == 0)
			{
				loc2.add(-10,0,2);
				addingZ = -1;
				uppingZ = 5;
				signX = 0;
				signZ = -2;
			}
			else if(directionLoop == 1)
			{
				loc2.add(2,0,-10);
				addingX = -1;
				uppingX = 5;
				signZ = 0;
				signX = -2;
			}
			else if(directionLoop ==2)
			{
				loc2.add(10,0,2);
				addingZ = -1;
				uppingZ = 5;
				signX = 0;
				signZ = -2;
			}
			else
			{
				loc2.add(2,0,10);
				addingX = -1;
				uppingX = 5;
				signZ = 0;
				signX = -2;
			}
			block = loc2.getBlock();
	    	for(int count = 0; count < 6; count++)
	    	{
	    		for(int count2 = 0; count2 < 5; count2++)
	    		{
	    			if(isCreate)
	    			{
		    			if(block.getType()==Material.AIR||block == null)
		    			{
		    				block.setType(M,true);
		    				block.setData(blockData);
		    			}
	    			}
	    			else if(block.getType()==M||block == null)
		    			block.setType(Material.AIR,true);
	    			block = loc2.add(addingX, 0, addingZ).getBlock();
	    		}
				block = loc2.add(uppingX, 1, uppingZ).getBlock();
	    	}
	    	block = loc2.add(signX,6,signZ).getBlock();
	    	
	    	block.setType(Material.WALL_SIGN);

	    	if(block.getType()==Material.WALL_SIGN)
	    	{
		        Sign s = (Sign) block.getState();
		        s.setLine(0, ""+(int)loc.getX());//�� �� X
		        s.setLine(1, ""+(int)loc.getY()+1);//�� �� Y
		        s.setLine(2, ""+(int)loc.getZ());//�� �� Z
		        s.setLine(3, "");//�� ����
		        s.update();
	    	}
	    	else
	    	{
		    	block.setType(Material.WALL_SIGN);
		    	if(block.getType()==Material.WALL_SIGN)
		    	{
			        Sign s = (Sign) block.getState();
			        s.setLine(0, ""+(int)loc.getX());//�� �� X
			        s.setLine(1, ""+(int)loc.getY()+1);//�� �� Y
			        s.setLine(2, ""+(int)loc.getZ());//�� �� Z
			        s.setLine(3, "");//�� ����
			        s.update();
		    	}
	    	}
		}
	}
	
}