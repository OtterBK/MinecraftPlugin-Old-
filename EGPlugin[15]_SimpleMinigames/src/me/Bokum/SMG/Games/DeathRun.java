package me.Bokum.SMG.Games;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.Bokum.SMG.Main;
import me.Bokum.SMG.Messenger.Messenger;
import me.Bokum.SMG.Minigame.Minigame;
import me.Bokum.SMG.Utility.Cooldown;
import me.Bokum.SMG.Utility.Utility;
import net.milkbowl.vault.economy.EconomyResponse;

public class DeathRun extends Minigame{
	public Cooldown cooldown;
	public List<Location> changingList;
	public boolean isTimer;
	public ItemStack fireBallSkill;
	public ItemStack knockBackSkill;
	public ItemStack speedSkill;
	public ItemStack skillItem;
	public Inventory skillInventory;
	public List<Location> blockloc = new ArrayList<Location>();
	
	public DeathRun(String title, int max, int min){
		super(title, max, min);
		cooldown = new Cooldown(this);
		
		List<String> loreList = new ArrayList<String>();
		loreList.add("��f- ��7��Ŭ���� ���濡 ȭ������ �߻��մϴ�.");
		loreList.add("��f- ��7ȭ������ ���� ������ 3x3�� ������ ��� ���۴ϴ�.");
		
		fireBallSkill = Utility.makeItem(280, 0, 1, "��f[ ��e���̾ ��f]", loreList);
		loreList.clear();
		
		loreList.add("��f- ��7��Ŭ���� ����4ĭ���� �ִ� �÷��̾ ƨ�ܳ��ϴ�.");
		knockBackSkill = Utility.makeItem(369, 0, 1, "��f[ ��e�˹� ��f]", loreList);
		
		loreList.add("��f- ��73�ʰ� �ż�2������ �޽��ϴ�.");
		loreList.clear();
		speedSkill = Utility.makeItem(351, 12, 1, "��f[ ��e�̼� ��� ��f]", loreList);
		
		loreList.add("��f- ��7��Ŭ���� ��ų�� �����մϴ�.");
		loreList.clear();
		skillItem = Utility.makeItem(340, 0, 1, "��f[ ��e��ų ���� ��f]", loreList);
		
		helpInventory = Bukkit.createInventory(null, 27, "��c��l�����");
		
		ItemStack item = new ItemStack(34, 1);
		ItemMeta meta2 = item.getItemMeta();
		meta2.setDisplayName("��6���");
		item.setItemMeta(meta2);
		for(int i = 0; i <= 9; i++){
			helpInventory.setItem(i, item);
		}
		for(int i = 17; i < 27; i++){
			helpInventory.setItem(i, item);
		}
		
		item = new ItemStack(Material.BOOK, 1);
		meta2 = item.getItemMeta();
		meta2.setDisplayName("��e�÷��� ���");
		item.setItemMeta(meta2);
		helpInventory.setItem(11, item);
		
		item = new ItemStack(Material.BOOK, 1);
		meta2 = item.getItemMeta();
		meta2.setDisplayName("��e���� ��Ģ");
		item.setItemMeta(meta2);
		helpInventory.setItem(13, item);
		
		skillInventory = Bukkit.createInventory(null, 9, "��c��l��ų ����");
		
		skillInventory.setItem(1, fireBallSkill);
		skillInventory.setItem(4, knockBackSkill);
		skillInventory.setItem(7, speedSkill);
		
		changingList = new ArrayList<Location>();
		
		isTimer = false;
		
		loadConfig();
		
		RestoreBlock();
	}
	
	public void loadConfig(){
		try{
			ConfigurationSection sec = Main.instance.getConfig().getConfigurationSection(gameTitle).getConfigurationSection("joinPos");
			this.joinPos = new Location(Bukkit.getWorld(sec.getString("world")), sec.getInt("x"), sec.getInt("y"), sec.getInt("z"));
		}catch(Exception e){
			Main.instance.getLogger().info(gameTitle+" ������ ���� ������ �������� �ʾҽ��ϴ�.");
		}
		try{
			ConfigurationSection sec = Main.instance.getConfig().getConfigurationSection(gameTitle).getConfigurationSection("startPos");
			this.startPos = new Location(Bukkit.getWorld(sec.getString("world")), sec.getInt("x"), sec.getInt("y"), sec.getInt("z"));
		}catch(Exception e){
			Main.instance.getLogger().info(gameTitle+" ������ ���� ������ �������� �ʾҽ��ϴ�.");
		}
		try{
			ConfigurationSection sec = Main.instance.getConfig().getConfigurationSection(gameTitle).getConfigurationSection("block");
			for(int j = 1; j <= Main.instance.getConfig().getInt("blockamt"); j++){
				  blockloc.add(new Location(Main.instance.getServer().getWorld(sec.getString("world")), sec.getInt("block_loc_"+j+"_x"), sec.getInt("block_loc_"+j+"_y"), sec.getInt("block_loc_"+j+"_z")));
			}
		}
		catch (Exception e){
			Main.instance.getLogger().info("�� ������ �Ϻ��� ���� �Ǿ����� �ʽ��ϴ�. ���� �߻��� ����� �ֽ��ϴ�.");
		}
	}
	
	public void setLoc(Player p, String args[]){
		if(args.length == 2){
			p.sendMessage("/smg set dr join");
			p.sendMessage("/smg set dr start");
			p.sendMessage("/smg set dr block");
		}else if(args.length >= 3){
			if(args[2].equalsIgnoreCase("test")){
				final int cur = Utility.Getcursch(this);
				tasktime[cur] = 5;
				tasknum[cur] = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable(){
					public void run(){
						Bukkit.broadcastMessage("������");
					}
				}, 1l, 20l);
			}
			if(args[2].equalsIgnoreCase("join")){
				if(!Main.instance.getConfig().isConfigurationSection(gameTitle)){
					Main.instance.getConfig().createSection(gameTitle);
					Main.instance.saveConfig();
				}
				if(!Main.instance.getConfig().getConfigurationSection(gameTitle).isConfigurationSection("joinPos")){
					Main.instance.getConfig().getConfigurationSection(gameTitle).createSection("joinPos");
					Main.instance.saveConfig();
				}
				ConfigurationSection sec = Main.instance.getConfig().getConfigurationSection(gameTitle).getConfigurationSection("joinPos");
				sec.set("world", p.getWorld().getName());
				sec.set("x", p.getLocation().getBlockX());
				sec.set("y", p.getLocation().getBlockY());
				sec.set("z", p.getLocation().getBlockZ());
				Main.instance.saveConfig();
				loadConfig();
			}else if(args[2].equalsIgnoreCase("start")){
				if(!Main.instance.getConfig().isConfigurationSection(gameTitle)){
					Main.instance.getConfig().createSection(gameTitle);
					Main.instance.saveConfig();
				}
				if(!Main.instance.getConfig().getConfigurationSection(gameTitle).isConfigurationSection("startPos")){
					Main.instance.getConfig().getConfigurationSection(gameTitle).createSection("startPos");
					Main.instance.saveConfig();
				}
				ConfigurationSection sec = Main.instance.getConfig().getConfigurationSection(gameTitle).getConfigurationSection("startPos");
				sec.set("world", p.getWorld().getName());
				sec.set("x", p.getLocation().getBlockX());
				sec.set("y", p.getLocation().getBlockY());
				sec.set("z", p.getLocation().getBlockZ());
				Main.instance.saveConfig();
				loadConfig();
			}else if(args[2].equalsIgnoreCase("block")){
				if(!Main.instance.getConfig().isConfigurationSection(gameTitle)){
					Main.instance.getConfig().createSection(gameTitle);
					Main.instance.saveConfig();
				}
				if(!Main.instance.getConfig().getConfigurationSection(gameTitle).isConfigurationSection("block")){
					Main.instance.getConfig().getConfigurationSection(gameTitle).createSection("block");
					Main.instance.saveConfig();
				}
					int x1 = Integer.valueOf(args[3]);
					int y1 = Integer.valueOf(args[4]);
					int z1 = Integer.valueOf(args[5]);
					int x2 = Integer.valueOf(args[6]);
					int y2 = Integer.valueOf(args[7]);
					int z2 = Integer.valueOf(args[8]);
					SaveBlock(p, x1, y1, z1, x2, y2, z2);
				Main.instance.saveConfig();
				loadConfig();
			}
		}
	}
	
	public void clearData(){
		for(int i = 0; i < tasknum.length; i++){
			if(tasknum[i] != -5){
				 Utility.Canceltask(minigame, i);
			}
		}
		for(Player p : playerList){
			Main.playingList.remove(p.getName());
		}
		RestoreBlock();
		playerList.clear();
		isStart = false;
		isLobbyStart = false;
		isTimer = false;
		isGameEnd = false;
		Forcestoptimer = 0;
		for(int i = 0; i < tasknum.length; i++){
			tasknum[i] = -5;
			tasktime[i] = -5;
		}
	}
	
	public void forceStop(){
		Messenger.sendMessage(playerList, "������ ���� ���� �Ǿ����ϴ�.", true);
		Bukkit.broadcastMessage(Messenger.MS+gameTitle+" ������ ���� ���� �Ǿ����ϴ�.");
		for(Player p : playerList){
			try{
				me.Bokum.EGM.Main.Spawn(p);
			}catch(Exception e){
				p.teleport(Main.lobby);
			}
		}
		clearData();
	}
	
	public void gameJoin(Player p){
		if(playerList.contains(p)){
			p.sendMessage(Messenger.MS+"�̹� ���ӿ� �������̽ʴϴ�.");
			return;
		}
		if(playerList.size() >= maxPlayer){
			p.sendMessage(Messenger.MS+"�̹� �ִ��ο�(+"+maxPlayer+")�Դϴ�.");
			return;
		}
		if(isStart){
			p.sendMessage(Messenger.MS+"�̹� ������ �������Դϴ�.");
			return;
		}
		else{
			playerList.add(p);
			p.teleport(joinPos);
			p.getInventory().clear();
			p.getInventory().setHelmet(null);
			p.getInventory().setChestplate(null);
			p.getInventory().setLeggings(null);
			p.getInventory().setBoots(null);
			p.getInventory().setItem(8, helpItem);
			me.Bokum.EGM.Main.gamelist.put(p.getName(), gameTitle);
			Main.playingList.put(p.getName(), Main.deathrun);
			Messenger.sendMessage(playerList, 
					p.getName()+" ���� "+gameTitle+"�� �����ϼ̽��ϴ�. �ο� (��e "+playerList.size()+"��7 / ��c"+minPlayer+" ��f)"
					,true);
			if(!isLobbyStart && playerList.size() >= minPlayer){
				startGame();
			}
			Messenger.sendSound(playerList, Sound.NOTE_PLING, 2.0f, 0.5f);
		}
	}
	
	public void startGame(){
		isLobbyStart = true;
		final int cur = Utility.Getcursch(this);
		tasktime[cur] = 5;
		Bukkit.broadcastMessage(Messenger.MS+"��c��l��������f�� �� ���۵˴ϴ�.");
		tasknum[cur] = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable(){
			public void run(){
				if(tasktime[cur] > 0){
					Messenger.sendMessage(playerList, "������ "+tasktime[cur]*10+" �� �� ���۵˴ϴ�.", true);
					tasktime[cur]--;
					if(tasktime[cur] == 3){
						Bukkit.broadcastMessage(Messenger.MS+"��a10�� �� ��c��������f�� ���� �ʱ�ȭ�մϴ�. ��c���� �������ּ���!");
					}
					if(tasktime[cur] == 2){
						Bukkit.broadcastMessage(Messenger.MS+"��c��������f�� ���� �ʱ�ȭ�մϴ�. ��c������!!!");
						RestoreBlock();
					}
				}
				else{
					Utility.Canceltask(minigame, cur);
					isStart = true;
					for(int i = 0; i < playerList.size(); i++){
						Player p = playerList.get(i);
						p.getInventory().clear();
						p.playSound(p.getLocation(), Sound.ANVIL_LAND, 2.0f, 1.0f);
						p.sendMessage(Messenger.MS+"������ ���� �ƽ��ϴ�.");
						p.getInventory().setItem(0, skillItem);
						p.teleport(startPos);
					}
					startWait();
				}
			}
		}, 200L, 200L);
	}
	
	public void startWait(){
		final int cur = Utility.Getcursch(this);
		tasktime[cur] = 25;
		tasknum[cur] = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable(){
			public void run(){
				if(tasktime[cur] > 0){
					Messenger.sendMessage(playerList,tasktime[cur]+" �� �� �����մϴ�. ��ų�� ����ּ���.", true);
					tasktime[cur]--;
				}
				else{
					Utility.Canceltask(minigame, cur);
					timer();
				}
			}
		}, 60L, 20L);
	}
	
	public void RestoreBlock(){
		for(Location loc : blockloc){
			loc.getBlock().setTypeIdAndData(35, (byte) 0, true);
		}
	}
	
	public void SaveBlock(Player p, int x1, int y1, int z1, int x2, int y2, int z2){
		blockloc.clear();
		int amt = 0;
		if(x1 > x2){
			int tmpint = x1;
			x1 = x2;
			x2 = tmpint;
		}
		if(y1 > y2){
			int tmpint = y1;
			y1 = y2;
			y2 = tmpint;
		}		
		if(z1 > z2){
			int tmpint = z1;
			z1 = z2;
			z2 = tmpint;
		}
		Location pos1 = new Location(Main.instance.getServer().getWorld("world"), x1, y1, z1);
		Location pos2 = new Location(Main.instance.getServer().getWorld("world"), x2, y2, z2);
		ConfigurationSection sec = Main.instance.getConfig().getConfigurationSection(gameTitle).getConfigurationSection("block");
		for(int x = pos1.getBlockX(); x <= pos2.getBlockX(); x++){
			for(int y = pos1.getBlockY(); y <= pos2.getBlockY(); y++){
				for(int z = pos1.getBlockZ(); z <= pos2.getBlockZ(); z++){
					Location block_loc = new Location(Main.instance.getServer().getWorld("world"), Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z));
					if(block_loc.getBlock().getType() == Material.WOOL){
						amt++;
						sec.set("world", block_loc.getWorld().getName());
						sec.set("block_loc_"+amt+"_x", block_loc.getBlockX());
						sec.set("block_loc_"+amt+"_y", block_loc.getBlockY());
						sec.set("block_loc_"+amt+"_z", block_loc.getBlockZ());			
						blockloc.add(block_loc);
					}
				}
			}
		}
		Main.instance.getConfig().set("blockamt", amt);
		Main.instance.saveConfig();
		loadConfig();
		p.sendMessage(Messenger.MS+"�����Ϸ�");
	}
	
	public void timer(){
		final int cur = Utility.Getcursch(this);
		isTimer = true;
		Messenger.sendMessage(playerList, "������ ���۵ƽ��ϴ�!", true);
		tasknum[cur] = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable(){
			public void run(){
				if(!isGameEnd){
					List<Location> locList = new ArrayList<Location>();
					for(int i = 0; i < changingList.size(); i++){
						locList.add(changingList.get(i));
					}
					for(Location l : locList){
						if(l.getBlock().getData() == 0) l.getBlock().setTypeIdAndData(35, (byte) 4, true);
						else if(l.getBlock().getData() == 4) l.getBlock().setTypeIdAndData(35, (byte) 1, true);
						else if(l.getBlock().getData() == 1) l.getBlock().setTypeIdAndData(35, (byte) 14, true);
						else if(l.getBlock().getData() == 14){
							l.getBlock().setType(Material.AIR);
							changingList.remove(l);
						} else if(l.getBlock().getType() == Material.AIR) changingList.remove(l);
					}
				}
				else{
					Utility.Canceltask(minigame, cur);
				}
			}
		}, 0L, 15L);
	}
	
	public void gameQuit(Player p){
		if(!playerList.contains(p)) return;
		Main.playingList.remove(p.getName());
		playerList.remove(p);
		if(!isStart) return;
		Messenger.sendMessage(playerList, p.getName()+" ��f���� Ż���Ǽ̽��ϴ�.", true);
		if(playerList.size() <= 1){
			try{
				winPlayer(playerList.get(0));
			}catch(Exception e){
				
			}
		}
	}
	
	public void gameHelper(Player p, int slot){
		switch(slot){
			case 11:
				p.sendMessage("��7���� �̸� ��f: ��c������");
				p.sendMessage("��f������ ���۵� �� 25�ʰ� �ɷ��� �����Ͻ� �� �ֽ��ϴ�.");
				p.sendMessage("��f25�ʰ� �����Ŀ��� �ڽ��� �� �ؿ��ִ� ����");
			 	p.sendMessage("��f������� �ǰ� �ٸ� �÷��̾ ����߷�");
			 	p.sendMessage("��f���������� ��Ƴ����� �¸��մϴ�.");
				p.closeInventory();
				return;
				  
			case 13:
				p.sendMessage("��f�������� �������� �ʽ��ϴ�.");
				p.sendMessage("��fPVP�� �Ұ����մϴ�.");
				p.closeInventory();
				return;
				  
			default: return;
			}  
		}
	
	public void winPlayer(final Player p){
		isGameEnd = true;
		p.playSound(p.getLocation(), Sound.ANVIL_LAND, 2.5f, 1.5f);
		Messenger.sendMessage(playerList, "����� ���ķ� ���ҽ��ϴ�!", true);
		try{
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable()
		{
			public void run()
			{
				EconomyResponse r = Main.econ.depositPlayer(p.getName(), 500);
				if (r.transactionSuccess()) {
					p.sendMessage(ChatColor.GOLD + "�¸� �������� 500���� �����̽��ϴ�.");
				}
				try{
					me.Bokum.EGM.Main.Spawn(p);
					} catch(Exception e){
					p.teleport(Main.lobby);
					}
				clearData();
				Bukkit.broadcastMessage(Messenger.MS+"��c��l��������f�� ��9"+p.getName()+"��f���� �¸��� ���� �ƽ��ϴ�.");
			}
				}, 140L);
		}catch(Exception e){
	    	forceStop();
	    }
	}
	
	public void selectSkill(Player p, int slot){
		p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1.5f, 0.5f);
		switch(slot){
		case 1: p.getInventory().clear(); p.closeInventory(); p.getInventory().addItem(fireBallSkill); return;
		case 4: p.getInventory().clear(); p.closeInventory(); p.getInventory().addItem(knockBackSkill); return;
		case 7: p.getInventory().clear(); p.closeInventory(); p.getInventory().addItem(speedSkill); return;
		
		default: return;
		}
	}
	
	//�̺�Ʈ
	
	public void onBlockPlace(BlockPlaceEvent e){
		if(!(e.getPlayer() instanceof Player)) return;
		Player p = e.getPlayer();
		if(!playerList.contains(p)) return;
		e.setCancelled(true);
	}
	
	public void onBlockBreak(BlockBreakEvent e){
		if(!(e.getPlayer() instanceof Player)) return;
		Player p = e.getPlayer();
		if(!playerList.contains(p)) return;
		e.setCancelled(true);
	}
	
	public void onPlayerMove(PlayerMoveEvent e){
		if(!playerList.contains(e.getPlayer())) return;
		if(!isTimer) return;
		Location l = e.getPlayer().getLocation().getBlock().getRelative(0, -1, 0).getLocation();
		if(l.getBlock().getType() == Material.AIR || changingList.contains(l)) return;
		changingList.add(new Location(l.getWorld(), l.getBlockX(), l.getBlockY(), l.getBlockZ()));
	}
	
	public void onQuit(PlayerQuitEvent e){
		if(!playerList.contains(e.getPlayer())) return;
		gameQuit(e.getPlayer());
	}

	public void onDropItem(PlayerDropItemEvent e){
		if(!playerList.contains(e.getPlayer())) return;
		e.setCancelled(true);
	}
	
	public void onPlayerDeath(PlayerDeathEvent e){
		if(!playerList.contains(e.getEntity())) return;
		gameQuit(e.getEntity());
	}
	
	public void onInteract(PlayerInteractEvent e){
		Player p = (Player) e.getPlayer();
		if(!playerList.contains(p)) return;
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(e.getItem() != null){
				if(e.getItem().getType() == Material.STICK && cooldown.checkCooldown(p, "��ų") && isTimer){
					Utility.spawnFireball(p, 2);
					cooldown.setCooldown(p, "��ų", 10, false, 0);
				}else if(e.getItem().getType() == Material.COMPASS){
					p.openInventory(helpInventory);
				}else if(e.getItem().getType() == Material.BOOK){
					p.openInventory(skillInventory);
				}else if(e.getItem().getType() == Material.BLAZE_ROD && cooldown.checkCooldown(p, "��ų")&& isTimer){
					for(Player t : playerList){
						if(t.getLocation().distance(p.getLocation()) <= 4){
							if(t.getName() == p.getName()) continue;
								t.setVelocity(t.getLocation().getDirection().multiply(-1.3));
								t.getWorld().playSound(t.getLocation(), Sound.BAT_TAKEOFF, 1.5f, 0.2f);	
						}
					}
					cooldown.setCooldown(p, "��ų", 10, false, 0);
				}else if(e.getItem().getType() == Material.INK_SACK && cooldown.checkCooldown(p, "��ų")&& isTimer){
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 1));
					p.sendMessage(Messenger.MS+"3�ʰ� �ӵ��� ���˴ϴ�.");
					cooldown.setCooldown(p, "��ų", 13, false, 0);
				}
			}
		}
	}
	
	public void onClickInventory(InventoryClickEvent e){
		if(!(e.getWhoClicked() instanceof Player)) return;
		Player p = (Player) e.getWhoClicked();
		if(!playerList.contains(p)) return;
		if(e.getInventory().getTitle().equalsIgnoreCase("��c��l�����")){
			gameHelper(p, e.getSlot());
			e.setCancelled(true);
			p.updateInventory();
		}else if(e.getInventory().getTitle().equalsIgnoreCase("��c��l��ų ����")){
			selectSkill(p, e.getSlot());
			e.setCancelled(true);
			p.updateInventory();
		}
	}
	
	public void onEntityDamagedByEntity(EntityDamageByEntityEvent e){
		if(!(e.getEntity() instanceof Player)) return;
		Player p = (Player) e.getEntity();
		if(!playerList.contains(p)) return;
		e.setCancelled(true);
	}
	
	public void onEntityExplode(EntityExplodeEvent event) {
		Entity ent = event.getEntity();
		if (ent instanceof Fireball) {
			event.setCancelled(true);
			Location l = new Location(event.getLocation().getWorld(), event.getLocation().getBlockX(), 
					0, event.getLocation().getBlockZ());
			for(int x = 0; x < 3; x++){
				for(int z = 0; z < 3; z++){
					l.getBlock().getRelative(x, 0, z).setType(Material.AIR);
				}
			}
		}
	}
	public void onExplosionPrime(ExplosionPrimeEvent event) {
		event.setFire(false);
		
		Entity ent = event.getEntity();
		if (ent instanceof Fireball)
			event.setRadius(2);
	}
}
