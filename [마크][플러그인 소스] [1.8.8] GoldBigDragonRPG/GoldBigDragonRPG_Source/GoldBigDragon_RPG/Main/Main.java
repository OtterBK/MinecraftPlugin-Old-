package GoldBigDragon_RPG.Main;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

import GoldBigDragon_RPG.Party.PartyDataManager;
import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;

public class Main extends JavaPlugin implements Listener
{
	public static JavaPlugin plugin = null;
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		plugin = this;
		new ServerOption().Initialize();
	  	return;
	}
	
	public void onDisable()
	{
		new GoldBigDragon_RPG.Effect.Corpse().RemoveAllCorpse();
		if(Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI"))
		{
	    	Collection<? extends Player> playerlist = Bukkit.getServer().getOnlinePlayers();
	    	Player[] a = new Player[playerlist.size()];
	    	playerlist.toArray(a);
	    	for(short count = 0; count <a.length;count++)
	    		new OtherPlugins.NoteBlockAPIMain().Stop(a[count]);
		}
	  	new PartyDataManager().saveParty();

		new GoldBigDragon_RPG.ServerTick.ServerTickScheduleManager().saveCategoriFile();
		Object[] players = Bukkit.getOnlinePlayers().toArray();
		for(short count = 0; count < players.length; count++)
			ServerOption.PlayerList.get(((Player)players[count]).getUniqueId().toString()).saveAll();
	  	Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[Clossing GoldBigDragon Advanced...]");
	  	return;
	}
	
	/*�� ���� ����̹Ƿ� ����.
	@EventHandler
	public void onInventoryOpenEvent(PlayerAchievementAwardedEvent event)
	{
	    if(event.getAchievement().equals(Achievement.OPEN_INVENTORY))
	    {
	        event.setCancelled(true);
	        new GoldBigDragon_RPG.GUI.StatsGUI().StatusGUI(event.getPlayer());
	    }
	}
	*/
	@EventHandler
	private void PlayerJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
	  	YamlController YC = new YamlController(this);
	  	if(YC.isExit("Skill/PlayerData/" + player.getUniqueId()+".yml") == false)
	  		new GoldBigDragon_RPG.Skill.SkillConfig().CreateNewPlayerSkill(player);
	  	else
	  		new GoldBigDragon_RPG.ETC.Job().PlayerFixAllSkillAndJobYML(player);
		Object_Player PO = new Object_Player(player);
		ServerOption.PlayerList.put(player.getUniqueId().toString(), PO);
		if(player.getLocation().getWorld().getName().compareTo("Dungeon")==0)
		{
			new GoldBigDragon_RPG.Util.PlayerUtil().teleportToCurrentArea(player, true);
			new GoldBigDragon_RPG.Dungeon.DungeonWork().EraseAllDungeonKey(player, false);
			GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setDungeon_Enter(null);
			GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(event.getPlayer().getUniqueId().toString()).setDungeon_UTC(-1);
		}
		if(new GoldBigDragon_RPG.Effect.Corpse().DeathCapture(player,true))
			new GoldBigDragon_RPG.Effect.Corpse().CreateCorpse(player);
		
    	new GoldBigDragon_RPG.Main.ServerOption().MagicSpellCatch();
    	new GoldBigDragon_RPG.Main.ServerOption().CitizensCatch();

		new Object_UserData().UserDataInit(player);
		
		if(player.isOp() == true)
			new GoldBigDragon_RPG.Effect.PacketSender().sendTitleSubTitle(player,"\'��e/���ǹڽ�\'", "\'��eGoldBigDragonAdvanced ���̵� �� ���� ������ �����մϴ�.\'", (byte)1,(byte)10, (byte)1);
		else
		{
			YamlManager Config = YC.getNewConfig("config.yml");
			if(Config.getInt("Event.DropChance")>=2||Config.getInt("Event.Multiple_EXP_Get")>=2||Config.getInt("Event.Multiple_Level_Up_StatPoint")>=2||Config.getInt("Event.Multiple_Level_Up_SkillPoint")>=2)
			{
				String alert = "[";
				if(Config.getInt("Event.DropChance")>=2)
					alert =alert+ "��ӷ� ���� "+Config.getInt("Event.DropChance")+"��";
				if(Config.getInt("Event.DropChance")>=2)
					alert = alert+", ";
				if(Config.getInt("Event.Multiple_EXP_Get")>=2)
					alert = alert + "����ġ " + Config.getInt("Event.Multiple_EXP_Get")+"�� ȹ��";
				if(Config.getInt("Event.Multiple_EXP_Get")>=2)
					alert = alert+", ";
				if(Config.getInt("Event.Multiple_Level_Up_StatPoint")>=2)
					alert = alert +"���� ����Ʈ "+Config.getInt("Event.Multiple_Level_Up_StatPoint")+"�� ȹ��";
				if(Config.getInt("Event.Multiple_Level_Up_StatPoint")>=2)
					alert = alert+", ";
				if(Config.getInt("Event.Multiple_Level_Up_SkillPoint")>=2)
					alert = alert +"��ų ����Ʈ " +Config.getInt("Event.Multiple_Level_Up_SkillPoint")+"�� ȹ��";
				alert = alert+"]";
				new GoldBigDragon_RPG.Effect.PacketSender().sendTitleSubTitle(player, "\'���� �̺�Ʈ�� �������Դϴ�.\'", "\'"+alert+"\'", (byte)1, (byte)10, (byte)1);
			}
		}
	  	if(YC.isExit("Quest/PlayerData/" + player.getUniqueId()+".yml") == false)
	  	{
	  	    new GoldBigDragon_RPG.Quest.QuestConfig().CreateNewPlayerConfig(player);

			YamlManager NewBieYM = YC.getNewConfig("ETC/NewBie.yml");
			for(byte count = 0; count < YC.getNewConfig("ETC/NewBie.yml").getConfigurationSection("SupportItem").getKeys(false).toArray().length;count++)
				if(NewBieYM.getItemStack("SupportItem."+count) != null)
					player.getInventory().addItem(NewBieYM.getItemStack("SupportItem."+count));
			player.teleport(new Location(Bukkit.getWorld(NewBieYM.getString("TelePort.World")), NewBieYM.getInt("TelePort.X"), NewBieYM.getInt("TelePort.Y"), NewBieYM.getInt("TelePort.Z"), NewBieYM.getInt("TelePort.Yaw"), NewBieYM.getInt("TelePort.Pitch")));
	  	}
		new GoldBigDragon_RPG.Util.ETC().UpdatePlayerHPMP(event.getPlayer());
    	new GoldBigDragon_RPG.GUI.EquipGUI().FriendJoinQuitMessage(player, true);

		if(YC.getNewConfig("config.yml").getString("Server.JoinMessage") != null)
			event.setJoinMessage(YC.getNewConfig("config.yml").getString("Server.JoinMessage").replace("%player%",event.getPlayer().getName()));
		else
			event.setJoinMessage(null);
	}
	
	@EventHandler
	private void PlayerQuit(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		
	  	YamlController YC = new YamlController(this);
		if(player.getLocation().getWorld().getName().compareTo("Dungeon")==0)
			new GoldBigDragon_RPG.Dungeon.DungeonWork().EraseAllDungeonKey(player, true);
		
		if(new GoldBigDragon_RPG.Effect.Corpse().DeathCapture(player,false))
			new GoldBigDragon_RPG.Effect.Corpse().RemoveCorpse(player.getName());
		
		if(ServerOption.PartyJoiner.containsKey(player))
			ServerOption.Party.get(ServerOption.PartyJoiner.get(player)).QuitParty(player);
		
		if(ServerOption.NoteBlockAPIAble == true)
			new OtherPlugins.NoteBlockAPIMain().Stop(event.getPlayer());

		YamlManager UserData = YC.getNewConfig("UserData/"+ player.getUniqueId()+".yml");
		UserData.removeKey("Data");
		UserData.saveConfig();
		ServerOption.PlayerCurrentArea.remove(player);
    	new GoldBigDragon_RPG.GUI.EquipGUI().FriendJoinQuitMessage(player, false);

		YamlManager Config = YC.getNewConfig("config.yml");
		if(Config.getString("Server.QuitMessage") != null)
		{
			String message = Config.getString("Server.QuitMessage").replace("%player%",event.getPlayer().getName());
			event.setQuitMessage(message);
		}
		else
			event.setQuitMessage(null);
	  GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).saveAll();
	  GoldBigDragon_RPG.Main.ServerOption.PlayerList.remove(player.getUniqueId().toString());
	}
	
	@EventHandler
	private void PlayerRespawn(PlayerRespawnEvent event)
	{
	  	YamlController YC = new YamlController(this);
	  	YamlManager Config = YC.getNewConfig("config.yml");
		if(Config.getBoolean("Death.SystemOn"))
		{
			Player player = event.getPlayer();
	    	event.setRespawnLocation(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getLastDeathPoint());
	    	player.setGameMode(GameMode.SPECTATOR);
			if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).isBgmOn())
			{
		    	if(Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI") == true)
		    	{
		    		new OtherPlugins.NoteBlockAPIMain().Stop(player);
					if(Config.contains("Death.Track"))
						if(Config.getInt("Death.Track")!=-1)
							new OtherPlugins.NoteBlockAPIMain().Play(player, Config.getInt("Death.Track"));
		    	}
			}
	    	new GoldBigDragon_RPG.GUI.DeathGUI().OpenReviveSelectGUI(player);
		}
		return;
	}
	
	@EventHandler
	private void Move(PlayerMoveEvent event){GoldBigDragon_RPG.Event.PlayerAction PA = new GoldBigDragon_RPG.Event.PlayerAction();PA.PlayerMove(event);return;}

	@EventHandler
	private void HotBarMove(PlayerItemHeldEvent event)
	{
		//if(MagicSpellsCatched == false)
		//	MagicSpellCatch();
		new GoldBigDragon_RPG.Event.ChangeHotBar().HotBarMove(event);
		return;
	}
	
	@EventHandler
	private void PlayerItemDrop(PlayerDropItemEvent event)
	{
		ItemStack IT = event.getItemDrop().getItemStack();
		if(IT.hasItemMeta() == true)
			if(IT.getItemMeta().hasLore() == true)
				if(IT.getItemMeta().getLore().size() == 4)
					if(IT.getItemMeta().getLore().get(3).equals(ChatColor.YELLOW+"[Ŭ���� �����Կ��� ����]")==true)
						event.setCancelled(true);
		return;
	}
	
	@EventHandler
	private void PlayerChatting(PlayerChatEvent event)
	{
		event.setMessage(ChatColor.translateAlternateColorCodes('&', event.getMessage()));
		new GoldBigDragon_RPG.Event.PlayerAction().PlayerChatting(event);
		return;
	}

	@EventHandler
	private void PlayerFishing(PlayerFishEvent event)
	{
		GoldBigDragon_RPG.Event.Fishing F = new GoldBigDragon_RPG.Event.Fishing();
		F.PlayerFishing(event);
		return;
	}
	
	@EventHandler
	private void Map(MapInitializeEvent event)
	{
		new GoldBigDragon_RPG.ETC.Map().onMap(event);
		return;
	}
	
	@EventHandler
	private void AmorStand(PlayerArmorStandManipulateEvent event)
	{
		Player player = event.getPlayer();
		ArmorStand AS = event.getRightClicked();
		String name = AS.getCustomName();
		if(name!=null)
		{
			if(name.charAt(0)=='��'&&name.charAt(1)=='0'&&name.charAt(2)=='��'&&name.charAt(3)=='l')
			{
				event.setCancelled(true);
				new GoldBigDragon_RPG.Structure.StructureMain().StructureUse(player,AS.getCustomName());
				return;
			}
			else if(name.charAt(0)=='��'&&name.charAt(1)=='c'&&name.charAt(2)=='��'&&name.charAt(3)=='0')
			{
				event.setCancelled(true);
				if(player.getItemInHand() != null)
				{
					if(ServerOption.DeathRescue != null)
					{
						if(ServerOption.DeathRescue.getTypeId()==player.getItemInHand().getTypeId())
						{
							ItemStack Pitem = player.getItemInHand();
							if(ServerOption.DeathRescue.getAmount()<=Pitem.getAmount())
							{
								GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
								String Name = null;
								if(AS.getItemInHand().getType() != Material.AIR)
									Name = AS.getItemInHand().getItemMeta().getDisplayName();
								else if(AS.getHelmet().getType() != Material.AIR)
									Name = AS.getHelmet().getItemMeta().getDisplayName();
								if(Name != null)
								{
									Player target = Bukkit.getPlayer(Name);
									if(target != null)
									{
									  	if(GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(target.getUniqueId().toString()).isDeath())
									  	{
											if(new GoldBigDragon_RPG.Util.PlayerUtil().deleteItem(player, ServerOption.DeathRescue, ServerOption.DeathRescue.getAmount()))
											{
												new GoldBigDragon_RPG.Effect.Corpse().RemoveCorpse(Name);
												player.updateInventory();
												player.sendMessage(ChatColor.LIGHT_PURPLE+"[����] : "+ChatColor.YELLOW+target.getName()+ChatColor.LIGHT_PURPLE+"���� ��Ȱ���׽��ϴ�!");
												target.sendMessage(ChatColor.LIGHT_PURPLE+"[��Ȱ] : "+ChatColor.YELLOW+player.getName()+ChatColor.LIGHT_PURPLE+"�Կ� ���� ��Ȱ�Ͽ����ϴ�!");
												target.setGameMode(GameMode.SURVIVAL);
												target.closeInventory();
												Location l = target.getLocation();
												l.add(0, 1, 0);
												target.teleport(l);
												for(short count2=0;count2<210;count2++)
													new GoldBigDragon_RPG.Effect.Particle().PL(target.getLocation(), org.bukkit.Effect.SMOKE, new GoldBigDragon_RPG.Util.Number().RandomNum(0, 14));
												s.SL(target.getLocation(), Sound.BLAZE_BREATH, 0.5F, 1.8F);
										    	if(Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI") == true)
										    		new OtherPlugins.NoteBlockAPIMain().Stop(target);
											  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
										    	YamlManager Config = YC.getNewConfig("config.yml");
										    	new GoldBigDragon_RPG.GUI.DeathGUI().Penalty(target, Config.getString("Death.Spawn_Help.SetHealth"), Config.getString("Death.Spawn_Help.PenaltyEXP"), Config.getString("Death.Spawn_Help.PenaltyMoney"));
												return;
											}
											else
											{
												s.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
												player.sendMessage(ChatColor.RED+"[SYSTEM] : ��Ȱ �������� �����Ͽ� ��Ȱ��ų �� �����ϴ�!");
												return;
											}
									  	}
									}
									else
									{
										Collection<Entity> aa = player.getLocation().getWorld().getNearbyEntities(player.getLocation(), 3, 3, 3);
										for(int count = 0; count < aa.size(); count++)
										{
											Entity now = ((Entity)aa.toArray()[count]);
											if(now.getType()==EntityType.ARMOR_STAND)
											{
												String CustomName = now.getCustomName();
												if(CustomName != null)
												{
													if(CustomName.charAt(0)=='��'&&CustomName.charAt(1)=='c'&&CustomName.charAt(2)=='��'&&CustomName.charAt(3)=='0')
													{
														String Name2 = null;
														if(AS.getItemInHand().getType() != Material.AIR)
															Name2 = AS.getItemInHand().getItemMeta().getDisplayName();
														else if(AS.getHelmet().getType() != Material.AIR)
															Name2 = AS.getHelmet().getItemMeta().getDisplayName();
														if(Name.compareTo(Name2)==0)
															now.remove();
													}
												}
											}
										}
										return;
									}
								}
								else
								{
									AS.remove();
									return;
								}
							}
						}
					}
				}
			}
			else
			{
				if(event.getPlayer().isOp()==false)
				{
					String TargetArea = null;
					GoldBigDragon_RPG.ETC.Area A = new GoldBigDragon_RPG.ETC.Area();
					if(A.getAreaName((Entity)AS) != null)
						TargetArea = A.getAreaName((Entity)AS)[0];
					if(TargetArea != null && A.getAreaOption(TargetArea, (char) 7) == false)
					{
						event.setCancelled(true);
						return;
					}
				}
			}
		}
	}

	@EventHandler
	private void BlockBurnEvent(BlockBurnEvent event)
	{
		if(ServerOption.AntiExplode || event.getBlock().getLocation().getWorld().getName().compareTo("Dungeon")==0)
			event.setCancelled(true);
	}
	@EventHandler
	private void BlockIgniteEvent(BlockIgniteEvent event)
	{
		if((ServerOption.AntiExplode || event.getBlock().getLocation().getWorld().getName().compareTo("Dungeon")==0)&&event.getIgnitingEntity()==null)
			event.setCancelled(true);
	}
	
	
	@EventHandler
	private void PlayerDeath(PlayerDeathEvent event)
	{
		final Player player = event.getEntity();
	  	YamlController YC = new YamlController(this);
	  	YamlManager Config = YC.getNewConfig("config.yml");
		if(Config.getBoolean("Death.SystemOn"))
		{
			GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setDeath(true);
		    Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
		    {
		      public void run()
		      {
		        PacketPlayInClientCommand packet = new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN);
		        ((CraftPlayer)player).getHandle().playerConnection.a(packet);
		      }
		    }, 1L);
		}
		GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).setLastDeathPoint(new Location(event.getEntity().getLocation().getWorld(), event.getEntity().getLocation().getX(), event.getEntity().getLocation().getY(), event.getEntity().getLocation().getZ(), event.getEntity().getLocation().getYaw(), event.getEntity().getLocation().getPitch()));
		if(event.getKeepInventory()==false)
		{
			List<ItemStack> Ilist = event.getDrops();
			event.setKeepInventory(true);
			event.getEntity().getInventory().clear();
			for(byte count = 0; count < Ilist.size(); count++)
			{
				ItemStack IT = Ilist.get(count);
				if(IT.isSimilar(ServerOption.DeathRevive))
				{
					Ilist.remove(count);
					event.getEntity().getInventory().addItem(IT);
				}
				
				/*
				else if(IT.hasItemMeta() == true)
					if(IT.getItemMeta().hasLore() == true)
						if(IT.getItemMeta().getLore().size() >= 4)
							if(IT.getItemMeta().getLore().get(3).equals(ChatColor.YELLOW+"[Ŭ���� �����Կ��� ����]")==true)
								Ilist.remove(count);
				*/
			}
			for(int count = 0; count < Ilist.size(); count++)
				new GoldBigDragon_RPG.Event.ItemDrop().CustomItemDrop(event.getEntity().getLocation(), Ilist.get(count));
		}
		return;
	}
	
	@EventHandler
	private void KeepItemDurability(PlayerItemDamageEvent event)
	{
		ItemStack item = event.getItem();
		if(item.hasItemMeta())
			if(item.getItemMeta().hasLore() == true)
				if(item.getItemMeta().getLore().toString().contains("������"))
					event.setCancelled(true);
		return;
	}
	
	@EventHandler
	private void RA(EntityShootBowEvent event) {new GoldBigDragon_RPG.Attack.Attack().RangeAttack(event);return;}
    @EventHandler
    private void Attack(EntityDamageByEntityEvent event) {new GoldBigDragon_RPG.Attack.Attack().AttackRouter(event);return;}
    @EventHandler
	private void EntitySpawn(CreatureSpawnEvent event) {new GoldBigDragon_RPG.Monster.MonsterSpawnEvent().EntitySpawn(event);return;}
    @EventHandler
    private void ITBlock(PlayerInteractEvent event)
    {
    	new GoldBigDragon_RPG.Util.ETC().UpdatePlayerHPMP(event.getPlayer());
    	new GoldBigDragon_RPG.Event.Interact().PlayerInteract(event);
    	return;
    }
    @EventHandler
    private void ITEnity(PlayerInteractEntityEvent event)
    {
    	new GoldBigDragon_RPG.Util.ETC().UpdatePlayerHPMP(event.getPlayer());
    	new GoldBigDragon_RPG.Main.ServerOption().CitizensCatch();
    	new GoldBigDragon_RPG.Event.Interact().PlayerInteractEntity(event);
    	return;
    }
    @EventHandler
    private void ItemGetMessage(PlayerPickupItemEvent event) {new GoldBigDragon_RPG.Event.Interact().PlayerGetItem(event);}
	@EventHandler
	private void MonsterKill(EntityDeathEvent event)	{new GoldBigDragon_RPG.Monster.MonsterKill().MonsterKilling(event);return;}
	@EventHandler
	private void BBreak(BlockBreakEvent event)
	{new GoldBigDragon_RPG.Event.BlockBreak().BlockBreaking(event); return;}
	@EventHandler
	private void BlockPlace(BlockPlaceEvent event){new GoldBigDragon_RPG.Event.BlockPlace().BlockPlaceE(event); return;}

	@EventHandler
	private void EntityExplode(EntityExplodeEvent event)
	{
		if(ServerOption.AntiExplode || event.getEntity().getLocation().getWorld().getName().compareTo("Dungeon")==0)
			event.blockList().clear();
	}
	
	@EventHandler
	private void ExplosionPrime(ExplosionPrimeEvent event)
	{
		if(event.getEntity().getLocation().getWorld().getName().compareTo("Dungeon")==0)
		{
			if(event.getEntityType()==EntityType.ENDER_CRYSTAL)
				return;
			else
			{
				event.setCancelled(true);
				new GoldBigDragon_RPG.Monster.MonsterKill().Boomb(event.getEntity());
				new GoldBigDragon_RPG.Monster.MonsterKill().DungeonKilled((LivingEntity)event.getEntity(), true);
			}
			event.getEntity().remove();
		}
	}
	
	@EventHandler
	private void onArrowHitBlock(ProjectileHitEvent event)
	{
		if(event.getEntity().getLocation().getWorld().getName().compareTo("Dungeon")==0)
		{
			if(event.getEntity().getType()==EntityType.ARROW)
			{
				Arrow a = (Arrow)event.getEntity();
				if(a.getShooter() instanceof Player)
				{
					Player player = (Player)a.getShooter();
					if(player.isOnline())
					{
						Location down = new Location(event.getEntity().getWorld(), event.getEntity().getLocation().getX(), event.getEntity().getLocation().getY(), event.getEntity().getLocation().getZ());
						Block b = null;
						int yaw = (int) event.getEntity().getLocation().getYaw();
						for(byte count = 0; count < 2; count++)
						{
							if(yaw >= -46 && yaw <=45)
								down.add(0, 0, 1);
							else if(yaw >= 46 && yaw <= 135)
								down.add(1, 0, 0);
							else if(yaw >= -136 && yaw <= -45)
								down.add(-1, 0, 0);
							else
								down.add(0, 0, -1);
							b = down.getBlock();
							if(b.getTypeId()!=0)
								break;
						}
						if(b.getTypeId() == 146)
							new GoldBigDragon_RPG.Dungeon.DungeonWork().TrapChestOpen(b);
						else if(b.getTypeId() == 95)
							new GoldBigDragon_RPG.Dungeon.DungeonWork().TrapGlassTouch(b, player);
					}
				}
			}
		}
	}
	
	
	@EventHandler
	private void applyHealthRegen(EntityRegainHealthEvent event)
	{
		if (event.isCancelled())
			return;
		if (((event.getEntity() instanceof Player)) &&(event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED))
	    {
	    	GoldBigDragon_RPG.Util.ETC ETC = new GoldBigDragon_RPG.Util.ETC();
	    	ETC.UpdatePlayerHPMP((Player)event.getEntity());
	    }
		return;
	}
	
	@EventHandler
	private void InventoryClick(InventoryClickEvent event)
	{
		if(Bukkit.getPluginManager().isPluginEnabled("MagicSpells") == true
		&&ServerOption.MagicSpellsCatched==true)
		{
			GoldBigDragon_RPG.Util.ETC ETC = new GoldBigDragon_RPG.Util.ETC();
			ETC.UpdatePlayerHPMP((Player)event.getWhoClicked());
		}
		if(event.getClickedInventory() == null)
			return;
		if(event.getCurrentItem().hasItemMeta() == true)
		{
			if(event.getCurrentItem().getItemMeta().hasLore() == true)
			{
				if(event.getCurrentItem().getItemMeta().getLore().size() == 4)
				{
					if(event.getCurrentItem().getItemMeta().getLore().get(3).equals((ChatColor.YELLOW+"[Ŭ���� �����Կ��� ����]")))
					{
						event.setCancelled(true);
						event.getWhoClicked().getInventory().setItem(event.getSlot(), new ItemStack(0));
						GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
						s.SP((Player)event.getWhoClicked(), Sound.ANVIL_LAND, 1.0F, 1.9F);
					}
				}
			}
		}
		if(ChatColor.stripColor(event.getWhoClicked().getOpenInventory().getTitle()).compareTo("��ȯ")==0)
			new GoldBigDragon_RPG.GUI.EquipGUI().ExchangeInventoryclick(event);
		
		if(event.getInventory().getName().length() >= 3)
		{
			if(event.getInventory().getName().charAt(0)=='��')
			{
				if(event.getInventory().getName().charAt(1)=='c')//��üGUI
					new GoldBigDragon_RPG.Structure.StructureMain().InventoryClickRouter(event, ChatColor.stripColor(event.getInventory().getName().toString()));
				else if(event.getInventory().getName().charAt(1)=='2')//����GUI
					new GoldBigDragon_RPG.Dungeon.DungeonGUI().InventoryClickRouter(event, ChatColor.stripColor(event.getInventory().getName().toString()));
				else if(event.getInventory().getName().charAt(1)=='0')//�Ϲ�GUI
				{
					String InventoryName = ChatColor.stripColor(event.getInventory().getName().toString());
					if(event.getInventory().getType()==InventoryType.CHEST)
					{
						if(!(InventoryName.equals("���� ��� ����")||InventoryName.equals("��ƾ� �� ������ ���")
						||InventoryName.equals("���� ������ ���")||InventoryName.equals("�ʽ��� ����")
						||InventoryName.equals("�ش� ����� ĳ�� ���� ������")||InventoryName.equals("���� �߰� ���")
						||InventoryName.equals("NPC �� ����")||InventoryName.equals("container.chest")
						||InventoryName.equals("container.chestDouble")||InventoryName.equals("�ʽ��� ���̵�")
						||InventoryName.equals("container.minecart")||InventoryName.equals("�̺�Ʈ ��ü ����")
						||InventoryName.equals("�̺�Ʈ ���� ����")||InventoryName.equals("��ȯ")||InventoryName.equals("��Ȱ ������")
						||InventoryName.equals("���� ������")||InventoryName.equals("���� ��ǰ ����")||InventoryName.equals("���� ��� ����")
						||InventoryName.compareTo("[NPC] ���� �������� �÷� �ּ���")==0
						
						))
						{
							event.setCancelled(true);
						}
					}
					
					if(InventoryName.contains("����")||InventoryName.contains("�ɼ�")||InventoryName.contains("������")||
					   InventoryName.equals("��Ÿ")||InventoryName.contains("���̵�")||
					   InventoryName.contains("��Ƽ")||InventoryName.contains("NPC")
					   ||InventoryName.contains("����")||InventoryName.equals("�̺�Ʈ ����")
					   ||InventoryName.contains("���")||InventoryName.contains("����Ʈ")||
					   InventoryName.contains("������")||InventoryName.contains("���")||InventoryName.equals("������Ʈ �߰�")
					   ||InventoryName.contains("���")||InventoryName.contains("����")||InventoryName.contains("������")
					    ||InventoryName.contains("[MapleStory]")||InventoryName.contains("[Mabinogi]")
					    ||InventoryName.contains("��ų")||InventoryName.contains("��ũ")||InventoryName.contains("����")
					    ||InventoryName.contains("��ϵ�")||InventoryName.contains("������")||InventoryName.contains("�ʽ���")
					    ||InventoryName.contains("ī�װ�")||InventoryName.equals("�ش� ����� ĳ�� ���� ������")||InventoryName.contains("����")
					    ||InventoryName.contains("����")||InventoryName.contains("����")||InventoryName.contains("��������")
					    ||InventoryName.contains("�̺�Ʈ")||InventoryName.contains("ģ��")||InventoryName.contains("�׺�")
					    ||InventoryName.equals("��ȯ")||InventoryName.contains("��Ȱ")||InventoryName.contains("����")
					    ||InventoryName.compareTo("���� �ӽ�")==0||InventoryName.contains("��ü")
					    )
					{
						new GoldBigDragon_RPG.Event.InventoryClick().InventoryClickRouter(event, InventoryName);
					}
				}
			}
		}
		return;
	}
	
	@EventHandler
	private void InventoryClose(InventoryCloseEvent event)
	{
		if(Bukkit.getPluginManager().isPluginEnabled("MagicSpells") == true
		&&ServerOption.MagicSpellsCatched==true)
		{
			GoldBigDragon_RPG.Util.ETC ETC = new GoldBigDragon_RPG.Util.ETC();
			ETC.UpdatePlayerHPMP((Player)event.getPlayer());
		}

		if(event.getInventory().getName().charAt(0)=='��')
		{
			if(event.getInventory().getName().charAt(1)=='c')
				new GoldBigDragon_RPG.Structure.StructureMain().InventoryCloseRouter(event, ChatColor.stripColor(event.getInventory().getName().toString()));
			else if(event.getInventory().getName().charAt(1)=='2')
				new GoldBigDragon_RPG.Dungeon.DungeonGUI().InventoryCloseRouter(event, ChatColor.stripColor(event.getInventory().getName().toString()));
			else
				new GoldBigDragon_RPG.Event.InventoryClose().InventoryCloseRouter(event);
		}
		return;
	}

	public boolean onCommand(CommandSender talker, org.bukkit.command.Command command, String string, String[] args)
    {
		new GoldBigDragon_RPG.Main.ServerOption().MagicSpellCatch();
		new GoldBigDragon_RPG.Main.ServerOption().CitizensCatch();
		for(byte count = 0; count <args.length; count++)
			args[count] = ChatColor.translateAlternateColorCodes('&', args[count]);
		
		if(talker instanceof Player)
		{
			Player player = (Player) talker;
			GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
			
			switch(string)
			{
		  		case "�����ʱ�ȭ��":
		  			if(player.isOp() == true)
		  			{
						ItemStack Icon = new MaterialData(340, (byte) 0).toItemStack(1);
						ItemMeta Icon_Meta = Icon.getItemMeta();
						Icon_Meta.setDisplayName("��2��3��4��3��3��l[���� �ʱ�ȭ �ֹ���]");
						Icon_Meta.setLore(Arrays.asList("��a[�ֹ���]",""));
						Icon.setItemMeta(Icon_Meta);
						if(args.length==1)
						{
			  				if(Bukkit.getServer().getPlayer(args[0]) != null)
			  				{
			  					Player target = Bukkit.getServer().getPlayer(args[0]);
				  				if(target.isOnline())
				  					new GoldBigDragon_RPG.Util.PlayerUtil().giveItemForce(target, Icon);
				  				else
				  				{
				  					player.sendMessage(ChatColor.RED + "[SYSTEM] : �ش� �÷��̾�� �������� �ƴմϴ�!");
				  					s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
				  				}
			  				}
						}
						else
		  					new GoldBigDragon_RPG.Util.PlayerUtil().giveItemForce(player, Icon);
		  			}
					else
					{
						talker.sendMessage(ChatColor.RED + "[SYSTEM] : �ش� ��ɾ �����ϱ� ���ؼ��� ������ ������ �ʿ��մϴ�!");
						s.SP((Player)talker, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
					}
		  			return true;
				case"gui���":
					if(player.isOp() == true)
					{
					 	s.SP((Player)talker, org.bukkit.Sound.VILLAGER_HAGGLE, 1.0F, 1.8F);
					    player.sendMessage(ChatColor.GREEN+"[NPC] : GUI�� Ȱ��ȭ ��ų NPC�� ��Ŭ�� �ϼ���!");
					    new Object_UserData().setInt(player, (byte) 4, 114);
					}
					else
					{
						talker.sendMessage(ChatColor.RED + "[SYSTEM] : �ش� ��ɾ �����ϱ� ���ؼ��� ������ ������ �ʿ��մϴ�!");
						s.SP((Player)talker, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
					}
					return true;
				case"ģ��":
				 	s.SP((Player)talker, org.bukkit.Sound.HORSE_SADDLE, 1.0F, 1.8F);
				 	new GoldBigDragon_RPG.GUI.ETCGUI().FriendsGUI(player, (short) 0);
					return true;
				case"��ų":
				 	s.SP((Player)talker, org.bukkit.Sound.HORSE_SADDLE, 1.0F, 1.8F);
				    GoldBigDragon_RPG.Skill.PlayerSkillGUI PSKGUI = new GoldBigDragon_RPG.Skill.PlayerSkillGUI();
					PSKGUI.MainSkillsListGUI(player, (short) 0);
					return true;
		  		case "������" :
		  			GoldBigDragon_RPG.CustomItem.ItemsCommand ItemC = new GoldBigDragon_RPG.CustomItem.ItemsCommand();
		  			if(args.length <= 0)
		  			{
		  				if(args.length <=0)
		  				{
		  					GoldBigDragon_RPG.Command.HelpMessage HM = new GoldBigDragon_RPG.Command.HelpMessage();
			  				HM.HelpMessager(player, (byte) 1);
			  				return true;	
		  				}
		  				if(ChatColor.stripColor(args[0]).equalsIgnoreCase("��������") ==true)
		  				 ItemC.onCommand2(talker, command, string, args);
		  				else
		  				{
		  					GoldBigDragon_RPG.Command.HelpMessage HM = new GoldBigDragon_RPG.Command.HelpMessage();
			  				HM.HelpMessager(player, (byte) 1);
			  				return true;	
		  				}
		  			}
		  			 if(ChatColor.stripColor(args[0]).equalsIgnoreCase("���") == false&& ChatColor.stripColor(args[0]).equalsIgnoreCase("���") == false&& ChatColor.stripColor(args[0]).equalsIgnoreCase("����") == false&& ChatColor.stripColor(args[0]).equalsIgnoreCase("�ޱ�") == false&&ChatColor.stripColor(args[0]).equalsIgnoreCase("�ֱ�") == false)
		  				 ItemC.onCommand2(talker, command, string, args);
		  			 else
				  		ItemC.onCommand1(talker, command, string, args);
		  			break;
		  		case "��Ƽ":
		  			GoldBigDragon_RPG.Party.PartyCommand PartyC = new GoldBigDragon_RPG.Party.PartyCommand();
		  			PartyC.onCommand(talker, command, string, args);
		  			return true;
				case "�׽�Ʈ":
				case "�׽�Ʈ2":
				case "��ƼƼ����":
				case "����������":
				case "����":
				case "����":
		  		case "��":
		  		case "Ÿ���߰�":
		  			new GoldBigDragon_RPG.Command.SystemCommand().onCommand(player, args, string);
		  			return true;
		  		case "����":
				 	s.SP((Player)talker, org.bukkit.Sound.HORSE_ARMOR, 0.8F, 1.8F);
				 	new GoldBigDragon_RPG.GUI.StatsGUI().StatusGUI((Player)talker);
					return true;
		  		case "�ɼ�":
				 	s.SP((Player)talker, org.bukkit.Sound.HORSE_ARMOR, 0.8F, 1.8F);
				 	new GoldBigDragon_RPG.GUI.OptionGUI().optionGUI((Player)talker);
					return true;
		  		case "��Ÿ":
				 	s.SP((Player)talker, org.bukkit.Sound.HORSE_ARMOR, 0.8F, 1.8F);
				 	new GoldBigDragon_RPG.GUI.ETCGUI().ETCGUI_Main((Player) talker);
					return true;
		  		case "���ǹڽ�":
					  if(talker.isOp() == true)
					  {
						  new Object_UserData().clearAll(player);
				  		  GoldBigDragon_RPG.GUI.OPBoxGUI opgui = new GoldBigDragon_RPG.GUI.OPBoxGUI();
						  s.SP((Player)talker, org.bukkit.Sound.HORSE_ARMOR, 0.8F, 1.8F);
						  opgui.OPBoxGUI_Main((Player)talker,(byte) 1);
						  return true;
					  }
					  else
					  {
						  talker.sendMessage(ChatColor.RED + "[SYSTEM] : �ش� ��ɾ �����ϱ� ���ؼ��� ������ ������ �ʿ��մϴ�!");
						  s.SP((Player)talker, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
						  return true;
					  }
		  		case "����" :
				  if(talker.isOp() == true)
				  {
					  s.SP((Player)talker, org.bukkit.Sound.HORSE_ARMOR, 0.8F, 1.8F);
		  			new GoldBigDragon_RPG.Monster.MonsterGUI().MonsterListGUI(player, 0);
				  }
				  else
				  {
					  talker.sendMessage(ChatColor.RED + "[SYSTEM] : �ش� ��ɾ �����ϱ� ���ؼ��� ������ ������ �ʿ��մϴ�!");
					  s.SP((Player)talker, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
				  }
		  			return true;
		  		case "����":
		  			GoldBigDragon_RPG.Command.WarpCommand WarpC = new GoldBigDragon_RPG.Command.WarpCommand();
		  			WarpC.onCommand(talker, command, string, args);
		  			return true;
		  		case "����":
		  			GoldBigDragon_RPG.Command.AreaCommand AreaC = new GoldBigDragon_RPG.Command.AreaCommand();
		  			AreaC.onCommand(talker, command, string, args);
		  			return true;
		  		case "����":
		  			GoldBigDragon_RPG.Command.NPCcommand NPCC = new GoldBigDragon_RPG.Command.NPCcommand();
		  			NPCC.onCommand(talker, command, string, args);
		  			return true;
		  		case "����Ʈ":
		  			GoldBigDragon_RPG.Quest.QuestCommand QC = new GoldBigDragon_RPG.Quest.QuestCommand();
		  			QC.onCommand(talker, command, string, args);
		  			return true;
		  		case "Ŀ�ǵ�":
		  			if(player.isOp() == true)
		  			{
		  				Object_UserData u = new Object_UserData();
						if(u.getType(player).equals("Skill"))
						{
							if(u.getString(player, (byte)1).equalsIgnoreCase("SKC"))
							{
								String CommandString = "";
								for(byte count = 0; count <args.length-1; count ++)
									CommandString = CommandString+args[count]+" ";
								CommandString = CommandString+args[args.length-1];
							  	YamlController YC = new YamlController(this);
								YamlManager SkillList  = YC.getNewConfig("Skill/SkillList.yml");
								if(CommandString.contains("/")==false)
									CommandString = "/"+CommandString;
								if(CommandString.equalsIgnoreCase("/����"))
									SkillList.set(u.getString(player, (byte)2)+".SkillRank."+u.getInt(player, (byte)4)+".Command","null");
								else
									SkillList.set(u.getString(player, (byte)2)+".SkillRank."+u.getInt(player, (byte)4)+".Command",CommandString);
								SkillList.saveConfig();
								GoldBigDragon_RPG.Skill.OPBoxSkillGUI SKGUI = new GoldBigDragon_RPG.Skill.OPBoxSkillGUI();
								SKGUI.SkillRankOptionGUI(player, u.getString(player, (byte)2), (short) u.getInt(player, (byte)4));
								u.clearAll(player);
							}
						}
						else
						{
		  					player.sendMessage(ChatColor.RED+"[��ų ����] : ��ų ������ ���� Ŀ�ǵ� �Դϴ�!");
							s.SP((Player)talker, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
		  					return true;
						}
		  			}
					else
					{
						talker.sendMessage(ChatColor.RED + "[SYSTEM] : �ش� ��ɾ �����ϱ� ���ؼ��� ������ ������ �ʿ��մϴ�!");
						s.SP((Player)talker, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
					}
					return true;
			}
			return false;
		}
		else
		{
			
		}
		return false;
    }

	@EventHandler
	public void Sign(SignChangeEvent event)
	{
		for (int i = 0; i <= 3; i++)
	    {
			String line = event.getLine(i);
	        line = ChatColor.translateAlternateColorCodes('&', line);
	        event.setLine(i, line);
	    }
	}

}