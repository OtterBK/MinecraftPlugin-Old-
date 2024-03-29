package septagram.Theomachy.Ability.HUMAN;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import septagram.Theomachy.Theomachy;
import septagram.Theomachy.Ability.Ability;
import septagram.Theomachy.DB.GameData;
import septagram.Theomachy.Utility.CoolTimeChecker;
import septagram.Theomachy.Utility.EventFilter;
import septagram.Theomachy.Utility.PlayerInventory;
import septagram.Theomachy.Utility.Skill;

public class Bomber extends Ability
{
	private final int coolTime0=27;
	private final int material=4;
	private final int stack0=2;
	private Location tntLocation;
	
	public Bomber(String playerName)
	{
		super(playerName,"Bomber", 105, true, false, false);
		Theomachy.log.info(playerName+abilityName);
	}
	
	public void description()
	{
		Player player = GameData.OnlinePlayer.get(playerName);
		player.sendMessage(ChatColor.DARK_GREEN+"=================== "+ChatColor.YELLOW+"능력 정보"+ChatColor.DARK_GREEN+" ===================");
		player.sendMessage(ChatColor.YELLOW+"[ 봄버 ]  "+ChatColor.RED+"[ 인간 ]  "+ChatColor.BLUE+"Active  "+ChatColor.GREEN+"Rank[ A ]");
		player.sendMessage("폭발을 다루는 능력입니다.\n"+
						   "지정된 위치에 2.0의 폭발을 일으킵니다.\n" +
						   "우클릭으로 해당 위치에 보이지 않는 tnt를 설치하며\n" +
						   "좌클릭으로 어디서든 폭발시킬 수 있습니다.\n" +
						   ChatColor.GREEN+"(좌클릭) "+ChatColor.WHITE+" 코블스톤 "+stack0+"개 소모, 쿨타임 "+coolTime0+"초"); 
	}
	
	public void T_Active(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if (PlayerInventory.InHandItemCheck(player, 369))
		{
			switch(EventFilter.PlayerInteract(event))
			{
			case 1:
				leftAction(player);
				break;
			case 2:case 3:
				rightAction(player);
				break;
			}
		}
	}

	private void leftAction(Player player)
	{
		Block block = player.getTargetBlock(null, 5);
		if (block.getTypeId() != 0)
		{
			Location location = block.getLocation();
			location.setY(location.getY()+1);
			this.tntLocation = location;
			player.sendMessage("해당 블럭에 폭탄이 설치되었습니다.");			
		}
	}
	
	private void rightAction(Player player)
	{
		if (CoolTimeChecker.Check(player, 0)&&PlayerInventory.ItemCheck(player, material, stack0))
		{
			if (tntLocation != null)
			{
				Skill.Use(player, material, stack0, 0, coolTime0);
				World world = player.getWorld();
				world.createExplosion(tntLocation, 2.0f, true);
				tntLocation = null;
				player.sendMessage("TNT가 폭발했습니다!");
				
			}
			else
				player.sendMessage("TNT가 설치되지 않았습니다.");
		}
	}
}
