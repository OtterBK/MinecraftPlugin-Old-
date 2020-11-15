package me.Bokum.Unmined;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
	public static String MS = "§f[§bUM§f] "; 
	public static List<Inventory> invenlist = new ArrayList<Inventory>();
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("Unmined 플러그인이 로드 되었습니다. - 제작 : Bokum");
		LoadInventory();
	}
	
	public void onDisable()
	{
		getLogger().info("Unmined 플러그인이 언로드 되었습니다. - 제작 : Bokum");
	}
	
	public boolean onCommand(CommandSender talker, Command command, String str, String[] args)
	{
		if(talker instanceof Player)
		{
			Player p = (Player) talker;
			if(str.equalsIgnoreCase("um") && p.hasPermission("unmined"))
			{
				if(args.length <= 0)
				{
					p.sendMessage(MS+"/um 숫자");
					p.sendMessage(MS+"§b제작 - Bokum");
					p.sendMessage(MS+"§b버젼 - 0.42");
					return true;
				}
				else
				{
					SaveInventory(p, args);
					return true;
				}
			}
		}
		return false;
	}
	
	public void LoadInventory()
	{
		Inventory tmpinv = null;
		invenlist.clear();
		do
		{
			tmpinv = null;
			tmpinv = getInventoryFromFile(new File(this.getDataFolder(), "UM_Inventory_"+(invenlist.size()+1)+ ".invsave"));
			if(tmpinv != null)
			{
				invenlist.add(tmpinv);
			}
			else
			{
				getLogger().info(MS+invenlist.size()+"번째까지의 인벤토리가 로드 되었습니다.");
				return;
			}
		}
		while(tmpinv != null);
	}
	
	public static boolean saveInventoryToFile(Inventory inventory, File path, String fileName) {
		if (inventory == null || path == null || fileName == null) return false;
		try {
			File invFile = new File(path, fileName + ".invsave");
			if (invFile.exists()) invFile.delete();
			FileConfiguration invConfig = YamlConfiguration.loadConfiguration(invFile);

			invConfig.set("Title", inventory.getTitle());
			invConfig.set("Size", inventory.getSize());
			invConfig.set("Max stack size", inventory.getMaxStackSize());

			ItemStack[] invContents = inventory.getContents();
			for (int i = 0; i < invContents.length; i++) {
				ItemStack itemInInv = invContents[i];
				if (itemInInv != null) if (itemInInv.getType() != Material.AIR) invConfig.set("Slot " + i, itemInInv);
			}

			invConfig.save(invFile);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	public static Inventory getInventoryFromFile(File file) {
		if (file == null) return null;
		if (!file.exists() || file.isDirectory() || !file.getAbsolutePath().endsWith(".invsave")) return null;
		try {
			FileConfiguration invConfig = YamlConfiguration.loadConfiguration(file);
			Inventory inventory = null;
			String invTitle = invConfig.getString("Title", "Inventory");
			int invSize = invConfig.getInt("Size", 27);
			int invMaxStackSize = invConfig.getInt("Max stack size", 64);
			InventoryHolder invHolder = null;
			if (invConfig.contains("Holder")) invHolder = Bukkit.getPlayer(invConfig.getString("Holder"));
			inventory = Bukkit.getServer().createInventory(invHolder, invSize, ChatColor.translateAlternateColorCodes('&', invTitle));
			inventory.setMaxStackSize(invMaxStackSize);
			try {
				ItemStack[] invContents = new ItemStack[invSize];
				for (int i = 0; i < invSize; i++) {
					if (invConfig.contains("Slot " + i)) invContents[i] = invConfig.getItemStack("Slot " + i);
					else invContents[i] = new ItemStack(Material.AIR);
				}
				inventory.setContents(invContents);
			} catch (Exception ex) {
			}
			return inventory;
		} catch (Exception ex) {
			return null;
		}
	}
	
	public void SaveInventory(Player p, String[] args)
	{
		int inven_num = 0;
		try
		{
			inven_num = Integer.valueOf(args[0]);
		}
		catch(Exception e)
		{
			p.sendMessage(MS+"/um 숫자");
			p.sendMessage(MS+"§b제작 - Bokum");
			p.sendMessage(MS+"§b버젼 - 0.35");
			return;
		}
		if(inven_num == 0)
		{
			p.sendMessage(MS+"0 이상만 입력해주세요.");
			return;
		}
		if(inven_num > (invenlist.size()+1))
		{
			p.sendMessage(MS+(invenlist.size()+1)+" 번 인벤토리를 먼저 만들어주세요.");
			return;
		}
		Inventory inv = Bukkit.getServer().createInventory(null, 36, "UM_Inventory_"+inven_num);
		if(inven_num < invenlist.size())
		{
			inv.setContents(invenlist.get(inven_num).getContents());
			p.sendMessage(MS+inven_num+"번째 인벤토리 수정");
		}
		p.openInventory(inv);
		p.sendMessage("아이템을 넣으세요.");
	}
	
	public int Getrandom(int num)
	{
		return ((int)(Math.random()*num)); //0~num-1까지중 랜덤으로 값을 반환
	}
	
	
	@EventHandler
	public void onCloseInventory(InventoryCloseEvent e)
	{
		if(e.getPlayer() instanceof Player)
		{
			Player p = (Player) e.getPlayer();
			if(e.getInventory().getTitle().contains("UM_Inventory"))
			{
				saveInventoryToFile(e.getInventory(), this.getDataFolder(), e.getInventory().getTitle());
				LoadInventory();
				p.sendMessage(MS+"인벤토리 저장완료");
				return;
			}
		}
	}	
	
	@EventHandler
	public void onOpenChestBlock(final PlayerInteractEvent e)
	{
		if(e.getPlayer() instanceof Player && e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getTypeId() == 54)
		{
			Player p = e.getPlayer();
			if(invenlist.size() <= 0)
			{
				p.sendMessage(MS+"저장된 인벤토리가 없습니다.");
				return;
			}
			e.setCancelled(true);
			Inventory getinv = Bukkit.getServer().createInventory(null, 36, "아이템");
			getinv.setContents(invenlist.get(Getrandom(invenlist.size())).getContents());
			p.openInventory(getinv);
			p.getWorld().playSound(e.getClickedBlock().getLocation(), Sound.CHEST_OPEN, 2.0f, 1.0f);
			e.getClickedBlock().setType(Material.AIR);
			Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
			{
				public void run()
				{
					e.getClickedBlock().setType(Material.CHEST);
				}
			}, 1800L);
		}
	}
}
