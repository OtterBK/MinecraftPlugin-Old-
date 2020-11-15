package GoldBigDragon_RPG.ETC;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

public class Teleport
{
	public void CreateNewTeleportSpot(Player player, String TeleportName)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();

		if(player.isOp() == false)
		{
			s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage(ChatColor.RED + "[SYSTEM] : �ش� ��ɾ �����ϱ� ���ؼ��� ������ ������ �ʿ��մϴ�!");
			return;
		}
		else
		{
		  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
			YamlManager TeleportList = YC.getNewConfig("Teleport/TeleportList.yml");

			if(TeleportList.contains(TeleportName))
			{
				s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
				player.sendMessage(ChatColor.RED + "[SYSTEM] : �ش� ���� ������ �̹� ��ϵǾ� �ֽ��ϴ�!");
				return;
			}
			
			TeleportList.set(TeleportName+".World", player.getLocation().getWorld().getName());
			TeleportList.set(TeleportName+".X", player.getLocation().getX());
			TeleportList.set(TeleportName+".Y", player.getLocation().getY());
			TeleportList.set(TeleportName+".Z", player.getLocation().getZ());
			TeleportList.set(TeleportName+".Pitch", player.getLocation().getPitch());
			TeleportList.set(TeleportName+".Yaw", player.getLocation().getYaw());
			TeleportList.set(TeleportName+".OnlyOpUse", true);
			TeleportList.saveConfig();
			
			s.SP(player, org.bukkit.Sound.CHICKEN_EGG_POP, 2.0F, 1.7F);
			player.sendMessage(ChatColor.GREEN + "[SYSTEM] : ���� ��ġ�� ���� ������ ��ϵǾ����ϴ�!");
			
			return;
	      }
	}
	
	public void setTeleportPermission(Player player, String TeleportSpotName)
	{
		if(player.isOp() == true)
		{
		  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
			YamlManager TeleportList = YC.getNewConfig("Teleport/TeleportList.yml");
			if(TeleportList.contains(TeleportSpotName))
			{
				if(TeleportList.getBoolean(TeleportSpotName+".OnlyOpUse") == true)
				{
					TeleportList.set(TeleportSpotName+".OnlyOpUse", false);
					player.sendMessage(ChatColor.GREEN + "[SYSTEM] : �ش� ������ �Ϲ� ������ ������ �� �ְ� �Ǿ����ϴ�!");
				}
				else
				{
					TeleportList.set(TeleportSpotName+".OnlyOpUse", true);
					player.sendMessage(ChatColor.RED + "[SYSTEM] : �ش� ������ �Ϲ� ������ ������ �� ���� �Ǿ����ϴ�!");
					
				}
				TeleportList.saveConfig();
				new GoldBigDragon_RPG.Effect.Sound().SP(player, org.bukkit.Sound.CHICKEN_EGG_POP, 2.0F, 1.7F);
			}
			else
			{
				new GoldBigDragon_RPG.Effect.Sound().SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
		  		player.sendMessage(ChatColor.RED+"[SYSTEM] : �ش� �̸����� ��ϵ� ���� ������ �����ϴ�!");
			}
	  		return;
		}
		else
		{
			new GoldBigDragon_RPG.Effect.Sound().SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage(ChatColor.RED + "[SYSTEM] : �ش� ��ɾ �����ϱ� ���ؼ��� ������ ������ �ʿ��մϴ�!");
			return;	
		}
	}
	
	
	public void RemoveTeleportList(Player player, String TeleportName)
	{
		if(player.isOp() == false)
		{
			new GoldBigDragon_RPG.Effect.Sound().SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
			player.sendMessage(ChatColor.RED + "[SYSTEM] : �ش� ��ɾ �����ϱ� ���ؼ��� ������ ������ �ʿ��մϴ�!");
			return;
		}
		else
		{
		  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
			YamlManager TeleportList = YC.getNewConfig("Teleport/TeleportList.yml");

			if(TeleportList.contains(TeleportName))
			{
				TeleportList.removeKey(TeleportName+".World");
				TeleportList.removeKey(TeleportName+".X");
				TeleportList.removeKey(TeleportName+".Y");
				TeleportList.removeKey(TeleportName+".Z");
				TeleportList.removeKey(TeleportName+".Pitch");
				TeleportList.removeKey(TeleportName+".Yaw");
				TeleportList.removeKey(TeleportName+".OnlyOpUse");
				TeleportList.removeKey(TeleportName+"");
				TeleportList.saveConfig();

				new GoldBigDragon_RPG.Effect.Sound().SP(player,org.bukkit.Sound.ITEM_BREAK,0.7F,1.0F);
	    		player.sendMessage(ChatColor.GREEN+"[SYSTEM] : "+ChatColor.YELLOW+TeleportName+ChatColor.GREEN+" ���� ������ ���������� �����Ͽ����ϴ�!");
			}
			else
			{
				new GoldBigDragon_RPG.Effect.Sound().SP(player,org.bukkit.Sound.ITEM_BREAK,0.7F,1.0F);
		  		player.sendMessage(ChatColor.RED+"[SYSTEM] : �ش� �̸����� ��ϵ� ���� ������ �����ϴ�!");
			}
		}
		return;
	}
	
	public void TeleportUser(Player player, String TeleportSpotName)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		GoldBigDragon_RPG.Effect.Potion p = new GoldBigDragon_RPG.Effect.Potion();

	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
		YamlManager TeleportList = YC.getNewConfig("Teleport/TeleportList.yml");

		if(TeleportList.contains(TeleportSpotName))
		{
			if(TeleportList.getBoolean(TeleportSpotName+".OnlyOpUse")== true  && player.isOp() == false)
			{
				s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
				player.sendMessage(ChatColor.RED+"[SYSTEM] : �ش� ������ ���ѵ� �����Դϴ�!");
				return;
			}
			else
			{
				s.SP(player, org.bukkit.Sound.ENDERMAN_TELEPORT, 0.8F, 1.0F);
				Location loc = new Location(Bukkit.getWorld(TeleportList.getString(TeleportSpotName+".World")), TeleportList.getInt(TeleportSpotName+".X")+0.5,  TeleportList.getInt(TeleportSpotName+".Y")+0.5,  TeleportList.getInt(TeleportSpotName+".Z")+0.5,  TeleportList.getInt(TeleportSpotName+".Yaw"),  TeleportList.getInt(TeleportSpotName+".Pitch"));
				player.teleport(loc);
				s.SP(player, org.bukkit.Sound.ENDERMAN_TELEPORT, 0.8F, 1.0F);
				p.givePotionEffect(player, PotionEffectType.BLINDNESS, 1, 15);
				return;
			}
		}
		for(short counter=0;counter<Bukkit.getServer().getWorlds().size();counter++)
		{
			if(Bukkit.getServer().getWorlds().get(counter).getName().equalsIgnoreCase(TeleportSpotName))
			{
				if(player.isOp()==true)
				{
					s.SP(player, org.bukkit.Sound.ENDERMAN_TELEPORT, 0.8F, 1.0F);
					player.teleport(Bukkit.getServer().getWorld(TeleportSpotName).getSpawnLocation());
					p.givePotionEffect(player, PotionEffectType.BLINDNESS, 1, 15);
					s.SP(player, org.bukkit.Sound.ENDERMAN_TELEPORT, 0.8F, 1.0F);
				}
				else
				{
					s.SP(player, org.bukkit.Sound.ORB_PICKUP, 2.0F, 1.7F);
					player.sendMessage(ChatColor.RED+"[SYSTEM] : ���尣 �̵��� �����ڸ� ���˴ϴ�!");
				}
				return;
			}
		}
		s.SP(player,org.bukkit.Sound.ITEM_BREAK,0.7F,1.0F);
  		player.sendMessage(ChatColor.RED+"[SYSTEM] : �ش� �̸����� ��ϵ� ���� ������ �����ϴ�!");
		return;
	}
}
