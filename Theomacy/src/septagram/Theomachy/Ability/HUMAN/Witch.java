package septagram.Theomachy.Ability.HUMAN;

import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import septagram.Theomachy.Theomachy;
import septagram.Theomachy.Ability.Ability;
import septagram.Theomachy.DB.GameData;
import septagram.Theomachy.Utility.CoolTimeChecker;
import septagram.Theomachy.Utility.EventFilter;
import septagram.Theomachy.Utility.GetPlayerList;
import septagram.Theomachy.Utility.PlayerInventory;
import septagram.Theomachy.Utility.Skill;

public class Witch extends Ability
{
	private final int coolTime0=60;
	private final int material=4;
	private final int stack0=1;
	
	public Witch(String playerName)
	{
		super(playerName,"Witch", 116, true, false, false);
		Theomachy.log.info(playerName+abilityName);
	}
	
	public void description()
	{
		Player player = GameData.OnlinePlayer.get(playerName);
		player.sendMessage(ChatColor.DARK_GREEN+"=================== "+ChatColor.YELLOW+"능력 정보"+ChatColor.DARK_GREEN+" ===================");
		player.sendMessage(ChatColor.YELLOW+"[ 마녀 ]  "+ChatColor.RED+"[ 인간 ]  "+ChatColor.BLUE+"Active,Passive  "+ChatColor.GREEN+"Rank[ B ]");
		player.sendMessage("디버프를 사용하는 능력입니다.\n"+
						   "블레이즈 로드를 이용한 능력 사용시 주변 10칸 안에 있는 자신의 팀원을 제외한 모두에게 각종 10초간 디버프를 적용합니다.\n" +
						   "자신을 공격한 상대는 7% 확률로 5초간 디버프에 걸리게 됩니다. \n" +
						   ChatColor.GREEN+"(좌클릭) "+ChatColor.WHITE+" 코블스톤 "+stack0+"개 소모, 쿨타임 "+coolTime0+"초\n");
	}
	
	public void T_Active(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if (PlayerInventory.InHandItemCheck(player, 369))
		{
			switch(EventFilter.PlayerInteract(event))
			{
			case 0:case 1:
				leftAction(player);
				break;
			}
		}
	}

	private void leftAction(Player player)
	{
		if (CoolTimeChecker.Check(player, 0)&&PlayerInventory.ItemCheck(player, material, stack0))
		{
			List<Player> targetList = GetPlayerList.getNearByNotTeamMembers(player, 10, 10, 10);
			if (targetList != null)
			{
				Skill.Use(player, material, stack0, 0, coolTime0);
				for (Player e : targetList)
				{
					
					e.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 200,0),true);
					e.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200,0),true);
					e.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200,0),true);
					e.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 200,0),true);
					e.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200,0),true);
					e.sendMessage("마녀에 의해 저주에 걸렸습니다.");
				}
			}
			else
				player.sendMessage("능력을 사용 할 대상이 없습니다.");
		}
	}
	
	public void T_Passive(EntityDamageByEntityEvent event)
	{
		Player player = (Player)event.getEntity();
		if (player.getName().equals(playerName))
		{
			Random random = new Random();
			int rn = random.nextInt(14);
			if (rn == 0)
			{
				Player target = (Player) event.getDamager();
				target.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 100,0),true);//*20	
				target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100,0),true);
				target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100,0),true);
				target.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 100,0),true);
				target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 100,0),true);
				target.sendMessage("마녀에 의해 저주가 걸렸습니다.");
			}
		}
	}
}
