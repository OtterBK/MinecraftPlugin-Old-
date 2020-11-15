package GoldBigDragon_RPG.Effect;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Sound
{
    //�ش� �÷��̾�� �Ҹ��� �����ϴ� �޼ҵ�//
	public void SP(Player player, org.bukkit.Sound sound, float volume,float pitch)
	{
    	if(player.isOnline() == true)
    		player.playSound(player.getLocation(), sound ,volume, pitch);
	}
	
    //�ش� �÷��̾�� �ش� ��ġ���� �Ҹ��� �����ϴ� �޼ҵ�//
	public void SPL(Player player, Location loc, org.bukkit.Sound sound, float volume,float pitch)
	{
    	if(player.isOnline() == true)
		player.playSound(loc, sound ,volume, pitch);
	}
	
	//�ش� ��ġ�� ���带 �����ϴ� �޼ҵ�//
	public void SL(Location loc, org.bukkit.Sound sound, float volume,float pitch)
	{
		World world = loc.getWorld();
		world.playSound(loc, sound ,volume, pitch);
	}

	public void IronDoorOpening(Location loc)
	{
		GoldBigDragon_RPG.ServerTick.ServerTickScheduleObject STSO = new GoldBigDragon_RPG.ServerTick.ServerTickScheduleObject(0, "Sound");
		STSO.setType("Sound");
		STSO.setString((byte)1, loc.getWorld().getName());
		STSO.setInt((byte)0, (int)loc.getX());
		STSO.setInt((byte)1, (int)loc.getY());
		STSO.setInt((byte)2, (int)loc.getZ());
		STSO.setString((byte)0, "0000001");//�Ҹ� ����
		STSO.setInt((byte)3, 20);//�Ҹ� ũ��
		STSO.setInt((byte)4, 5);//�Ҹ� �ӵ�
		
		STSO.setInt((byte)5, 1);//ƽ ����
		STSO.setMaxCount(STSO.getString((byte)0).length());
		STSO.setTick(GoldBigDragon_RPG.ServerTick.ServerTickMain.nowUTC);
		GoldBigDragon_RPG.ServerTick.ServerTickMain.Schedule.put(GoldBigDragon_RPG.ServerTick.ServerTickMain.nowUTC, STSO);
	}
}
