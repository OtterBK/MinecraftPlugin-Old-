package me.Bokum.testpackage;

import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Pluginonoffmessage extends JavaPlugin {

	public final Logger logger = Logger.getLogger("Minecraft");
	@Override
	public void onEnable() {
			PluginDescriptionFile pdfFile = this.getDescription();
			this.logger.info(pdfFile.getName() + pdfFile.getVersion() + "가 활성화 되었습니다.");
	}
	
	@Override
	public void onDisable() {
			PluginDescriptionFile pdfFile = this.getDescription();
			this.logger.info(pdfFile.getName() + pdfFile.getVersion() + "가 비활성화 되었습니다.");
	}


@EventHandler

public void StoneGet (PlayerLevelChangeEvent event) {

Player player = (Player)event.getPlayer();

if (event.getNewLevel() > event.getOldLevel())
{
	player.getInventory().addItem(new ItemStack(Material.STONE, 1));
}
}
}