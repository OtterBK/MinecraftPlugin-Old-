package GoldBigDragon_RPG.Config;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;
import net.md_5.bungee.api.ChatColor;

public class NewBieConfig
{
    public void CreateNewConfig()
	{
	  	YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
    	YamlManager YM = YC.getNewConfig("ETC/NewBie.yml");
	  	YM.set("TelePort.World", Bukkit.getWorlds().get(0).getSpawnLocation().getWorld().getName().toString());
	  	YM.set("TelePort.X", Bukkit.getWorlds().get(0).getSpawnLocation().getX());
	  	YM.set("TelePort.Y", Bukkit.getWorlds().get(0).getSpawnLocation().getY());
	  	YM.set("TelePort.Z", Bukkit.getWorlds().get(0).getSpawnLocation().getZ());
	  	YM.set("TelePort.Pitch", Bukkit.getWorlds().get(0).getSpawnLocation().getPitch());
	  	YM.set("TelePort.Yaw", Bukkit.getWorlds().get(0).getSpawnLocation().getYaw());

		ItemStack Icon = new MaterialData(340, (byte) 0).toItemStack(1);
		ItemMeta Icon_Meta = Icon.getItemMeta();
		Icon_Meta.setDisplayName(ChatColor.YELLOW+""+ChatColor.BOLD+"[���� ���̵�]");
		Icon_Meta.setLore(Arrays.asList(ChatColor.BLUE+""+ChatColor.BOLD+"[�Ϲ� ����]",
				ChatColor.YELLOW+""+ChatColor.BOLD+"/����",
				ChatColor.WHITE+" �ڽ��� ������ Ȯ���մϴ�.",
				ChatColor.YELLOW+""+ChatColor.BOLD+"/��ų",
				ChatColor.WHITE+" �ڽ��� ��ų�� Ȯ���մϴ�.",
				ChatColor.YELLOW+""+ChatColor.BOLD+"/�ɼ�",
				ChatColor.WHITE+" ���� �ɼ��� �����մϴ�.",
				ChatColor.YELLOW+""+ChatColor.BOLD+"/����Ʈ",
				ChatColor.WHITE+" ���� �������� ����Ʈ�� Ȯ���մϴ�.",
				ChatColor.YELLOW+""+ChatColor.BOLD+"/��",
				ChatColor.WHITE+" ���� �������� ���� Ȯ���մϴ�.",
				ChatColor.YELLOW+""+ChatColor.BOLD+"/��Ÿ",
				ChatColor.WHITE+" ��Ÿ �ý����� Ȯ���մϴ�.",
				ChatColor.YELLOW+""+ChatColor.BOLD+"/��Ƽ",
				ChatColor.WHITE+" ��Ƽ�� ���� ����� �����մϴ�.",
				"",
				ChatColor.GOLD+""+ChatColor.BOLD+"[��� ����/��ȯ/ģ�� �߰�]",
				ChatColor.GRAY+" �÷��̾� �� Ŭ��",
				""
				));
		Icon.setItemMeta(Icon_Meta);
	  	YM.set("SupportItem.0", Icon);

	  	Icon = new MaterialData(340, (byte) 0).toItemStack(1);
	  	Icon_Meta = Icon.getItemMeta();
	  	Icon_Meta.setDisplayName(ChatColor.YELLOW+""+ChatColor.BOLD+"[���� ���̵�]");
	  	Icon_Meta.setLore(Arrays.asList(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"[���۷�����]",
				ChatColor.YELLOW+""+ChatColor.BOLD+"/���ǹڽ�",
				ChatColor.WHITE+" ���� ���� ����â�� ���ϴ�.",
				ChatColor.YELLOW+""+ChatColor.BOLD+"/����",
				ChatColor.WHITE+" ������ �����մϴ�.",
				ChatColor.YELLOW+""+ChatColor.BOLD+"/������",
				ChatColor.WHITE+" ���� �տ� �� �����ۿ� ����",
				ChatColor.WHITE+" NBT ������ �մϴ�.",
				ChatColor.YELLOW+""+ChatColor.BOLD+"/����",
				ChatColor.WHITE+" ���� ���� ��ɾ ���ϴ�.",
				ChatColor.YELLOW+""+ChatColor.BOLD+"/��ƼƼ���� [1~10000]",
				ChatColor.WHITE+" �ڽ��� �������� �ش� ���� ����",
				ChatColor.WHITE+" ��� ��ƼƼ�� �����մϴ�.",
				ChatColor.YELLOW+""+ChatColor.BOLD+"/Ÿ���߰� [���ο� ������ Ÿ��]",
				ChatColor.WHITE+" Ŀ���� ������ Ÿ���� �߰��մϴ�.",
				""
				));
	  	Icon.setItemMeta(Icon_Meta);
	  	YM.set("SupportItem.1", Icon);
	  	
	  	YM.set("SupportMoney", 1000);
	  	YM.set("FirstQuest", "null");

	  	Icon = new MaterialData(340, (byte) 0).toItemStack(1);
	  	Icon_Meta = Icon.getItemMeta();
	  	Icon_Meta.setDisplayName(ChatColor.YELLOW +""+ ChatColor.BOLD + "���� �ý���");
	  	Icon_Meta.setLore(Arrays.asList(ChatColor.GRAY+ "�÷����ο��� 5���� ������ �ֽ��ϴ�.",ChatColor.RED +"["+GoldBigDragon_RPG.Main.ServerOption.STR+"]",ChatColor.GRAY+""+GoldBigDragon_RPG.Main.ServerOption.STR+"�� �÷��̾���",ChatColor.GRAY+"������ �������� �����մϴ�.",ChatColor.GREEN +  "["+GoldBigDragon_RPG.Main.ServerOption.DEX+"]",ChatColor.GRAY+""+GoldBigDragon_RPG.Main.ServerOption.DEX+"�� �÷��̾��� �뷱�� ��",ChatColor.GRAY+"���� �������� ���� ǰ��,",ChatColor.GRAY+"���Ÿ� �������� �����մϴ�.",ChatColor.BLUE+"["+GoldBigDragon_RPG.Main.ServerOption.INT+"]",ChatColor.GRAY+""+GoldBigDragon_RPG.Main.ServerOption.INT+"�� ������� �� ������ȣ,",ChatColor.GRAY+"���� ���ݷ¿� �����մϴ�.",ChatColor.WHITE+"["+GoldBigDragon_RPG.Main.ServerOption.WILL+"]",ChatColor.GRAY + ""+GoldBigDragon_RPG.Main.ServerOption.WILL+"�� �÷��̾���",ChatColor.GRAY + "ũ��Ƽ�ÿ� �����մϴ�.",ChatColor.YELLOW + "["+GoldBigDragon_RPG.Main.ServerOption.LUK+"]",ChatColor.GRAY + ""+GoldBigDragon_RPG.Main.ServerOption.LUK+"�� ũ��Ƽ�� ��",ChatColor.GRAY +"��Ű �ǴϽ�, ��Ű ���ʽ� ��",ChatColor.GRAY +"���� 'Ȯ��'�� �����մϴ�."));
	  	Icon.setItemMeta(Icon_Meta);
	  	YM.set("Guide.0", Icon);

		Icon = new MaterialData(340, (byte) 0).toItemStack(1);
		Icon_Meta = Icon.getItemMeta();
		Icon_Meta.setDisplayName(ChatColor.YELLOW +""+ ChatColor.BOLD + "����Ű �ý���");
		Icon_Meta.setLore(Arrays.asList(ChatColor.WHITE+ "/��ų"+ChatColor.GRAY+" ��ɾ ���Ͽ�",ChatColor.GRAY+"���� �ڽ��� �˰� �ִ� ��ų��",ChatColor.GRAY+"������ �� ������, Ŭ���� ����",ChatColor.GRAY+"�ش� ��ų�� �ֹٿ� ��� ��Ŵ���� ��",ChatColor.GRAY+"����Ű�� ���� ���� ��ų �����",ChatColor.GRAY+"�����մϴ�.",ChatColor.GRAY+"���� ���� ���� Ư�� �����۵���",ChatColor.GRAY+"����Ű�� ���� ������ �����",ChatColor.GRAY+"�����ϰ� �Ǿ� �ֽ��ϴ�."));
		Icon.setItemMeta(Icon_Meta);
	  	YM.set("Guide.1", Icon);
	  	YM.saveConfig();
	  	return;
	}
}
