package GoldBigDragon_RPG.ServerTick;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;

import org.bukkit.ChatColor;

public class ServerTask_Player
{
	public void ExChangeTimer(long UTC)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		Player caller = Bukkit.getServer().getPlayer(ServerTickMain.Schedule.get(UTC).getString((byte)0));
		Player target = Bukkit.getServer().getPlayer(ServerTickMain.Schedule.get(UTC).getString((byte)1));
		if(caller == null)
		{
			Cancel(UTC,(short) 0);
			return;
		}
		else if(target == null)
		{
			Cancel(UTC,(short) 0);
			return;
		}

		if(ServerTickMain.Schedule.get(UTC).getCount() <= ServerTickMain.Schedule.get(UTC).getMaxCount())
		{
			GoldBigDragon_RPG.Effect.PacketSender PS = new GoldBigDragon_RPG.Effect.PacketSender();
			float fast;
			if(ServerTickMain.Schedule.get(UTC).getCount() <= 5)
				fast = (float) 0.5;
			else
				fast = (float) (ServerTickMain.Schedule.get(UTC).getCount()/10.0);
			s.SP(caller, Sound.NOTE_PLING, 0.8F, fast);
			s.SP(target, Sound.NOTE_PLING, 0.8F, fast);
			PS.sendTitleSubTitle(caller, "\'"+ChatColor.YELLOW+"[��ȯ ��û]"+"\'", "\'"+TimerBar(ServerTickMain.Schedule.get(UTC).getCount(), 10)+"\'", (byte)1, (byte)0, (byte)1);
			PS.sendTitleSubTitle(target, "\'"+ChatColor.YELLOW+"[��ȯ ��û]"+"\'", "\'"+TimerBar(ServerTickMain.Schedule.get(UTC).getCount(), 10)+"\'", (byte)1, (byte)0, (byte)1);
			long tick = ServerTickMain.Schedule.get(UTC).getTick()+1500;
			ServerTickMain.Schedule.get(UTC).setCount(ServerTickMain.Schedule.get(UTC).getCount()+1);
			ServerTickMain.Schedule.get(UTC).setTick(tick);
			ServerTickMain.PlayerTaskList.put(caller.getName(), ""+tick);
			ServerTickMain.PlayerTaskList.put(target.getName(), ""+tick);
			ServerTickMain.Schedule.put(tick, ServerTickMain.Schedule.get(UTC));
		}
		else
		{
			Cancel(UTC,(short) 0);
			return;
		}
	}
	
	public void Cancel (long UTC, short MessageType)
	{
		Player caller = Bukkit.getServer().getPlayer(ServerTickMain.Schedule.get(UTC).getString((byte)0));
		Player target = Bukkit.getServer().getPlayer(ServerTickMain.Schedule.get(UTC).getString((byte)1));
		
		ServerTickMain.PlayerTaskList.remove(ServerTickMain.Schedule.get(UTC).getString((byte)0));
		ServerTickMain.PlayerTaskList.remove(ServerTickMain.Schedule.get(UTC).getString((byte)1));
		ServerTickMain.Schedule.remove(UTC);
		
		if(caller != null)
			SendMessage(caller, MessageType);
		if(target != null)
			SendMessage(target, (short) (MessageType+1));
	}
	
	public void SendMessage(Player Receiver, short message)
	{
		GoldBigDragon_RPG.Effect.Sound s = new GoldBigDragon_RPG.Effect.Sound();
		switch(message)
		{
			case 0 ://��ȯ ��û�� - ��ȯ ��� �޽���
			{
				Receiver.sendMessage(ChatColor.RED+"[��ȯ] : ��밡 ��ȯ�� ����Ͽ����ϴ�.");
				s.SP(Receiver, Sound.VILLAGER_NO, 1.2F, 1.1F);
			}
			break;
			case 1 ://��ȯ ��� - ��ȯ ��� �޽���
			{
				Receiver.sendMessage(ChatColor.RED+"[��ȯ] : ��ȯ�� ��ҵǾ����ϴ�.");
				s.SP(Receiver, Sound.VILLAGER_NO, 1.2F, 1.1F);
			}
			break;
		}
	}
	
	public String TimerBar(int PassedSec, int MaxWaittingTime)
	{
		String TimerBar = ChatColor.DARK_RED+"";
		int Calculator = 0;
		if(PassedSec == MaxWaittingTime)
		{
			for(short count = 0; count <= 100; count++)
				TimerBar = TimerBar + "|";
			return TimerBar;
		}
		else if(MaxWaittingTime/100 >= 1)
		{
			Calculator = PassedSec/(MaxWaittingTime/100);
			for(short count = 0; count <= Calculator; count++)
				TimerBar = TimerBar + "|";
			TimerBar = TimerBar + ChatColor.BLUE;
			for(short count = 0; count <= 100-Calculator; count++)
				TimerBar = TimerBar + "|";
		}
		else if(MaxWaittingTime/10 >= 1)
		{
			Calculator = PassedSec/(MaxWaittingTime/10);
			for(short count = 0; count <= (Calculator*10); count++)
				TimerBar = TimerBar + "|";
			TimerBar = TimerBar + ChatColor.BLUE;
			for(short count = 0; count <= 100-(Calculator*10); count++)
				TimerBar = TimerBar + "|";
		}
		return TimerBar;
	}

	public void Gamble_SlotMachine_Rolling(long UTC)
	{
		ServerTickScheduleObject STSO = ServerTickMain.Schedule.get(UTC);
		int count = STSO.getCount()+1;
		if(count<STSO.getMaxCount())
		{
			STSO.setCount(count);
			
			short[] ItemID = new short[3];
			for(byte counter=0;counter<3;counter++)
			{
				byte randomnum = (byte) new GoldBigDragon_RPG.Util.Number().RandomNum(0, 5);
				ItemID[counter]=263;
				switch(randomnum)
				{
				case 0:
					ItemID[counter] = 263;
					break;
				case 1:
					ItemID[counter] = 265;
					break;
				case 2:
					ItemID[counter] = 266;
					break;
				case 3:
					ItemID[counter] = 264;
					break;
				case 4:
					ItemID[counter] = 388;
					break;
				case 5:
					ItemID[counter] = 399;
					break;
				}
			}
			new GoldBigDragon_RPG.GUI.GambleGUI().SlotMachine_RollingGUI(STSO.getString((byte)0), ItemID, false, STSO.getString((byte)1));

			if(count<5)
			{
				STSO.setTick(STSO.getTick()+(count*20)+count);
				ServerTickMain.Schedule.remove(UTC);
				ServerTickMain.Schedule.put(STSO.getTick()+(count*20)+count,STSO);
			}
			else if(count<10)
			{
				STSO.setTick(STSO.getTick()+(count*25)+count);
				ServerTickMain.Schedule.remove(UTC);
				ServerTickMain.Schedule.put(STSO.getTick()+(count*25)+count,STSO);
			}
			else if(count<15)
			{
				STSO.setTick(STSO.getTick()+(count*30)+count);
				ServerTickMain.Schedule.remove(UTC);
				ServerTickMain.Schedule.put(STSO.getTick()+(count*30)+count,STSO);
			}
			else
			{
				STSO.setTick(STSO.getTick()+(count*35)+count);
				ServerTickMain.Schedule.remove(UTC);
				ServerTickMain.Schedule.put(STSO.getTick()+(count*35)+count,STSO);
			}
		}
		else
		{
			short[] ID = new short[3];
			ID[0] = (short) STSO.getInt((byte)0);
			ID[1] = (short) STSO.getInt((byte)1);
			ID[2] = (short) STSO.getInt((byte)2);
			
			new GoldBigDragon_RPG.GUI.GambleGUI().SlotMachine_RollingGUI(STSO.getString((byte)0), ID , true, STSO.getString((byte)1));
			
			if(Bukkit.getServer().getPlayer(STSO.getString((byte)0)) != null)
			{
				if(Bukkit.getServer().getPlayer(STSO.getString((byte)0)).isOnline()==true)
				{
					Player player = Bukkit.getServer().getPlayer(STSO.getString((byte)0));
					String MachineNumber = STSO.getString((byte)1);
					String Present = null;
					boolean LuckyStar = false;
					YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
					YamlManager GambleConfig =YC.getNewConfig("ETC/SlotMachine.yml");
					//��� ���� ���
					if(ID[0]==ID[1]&&ID[1]==ID[2])
					{
						//Ʈ���� �ھ�
						if(ID[0]==263)
							Present = GambleConfig.getString(MachineNumber+".1");
						//Ʈ���� ���̾�
						else if(ID[0]==265)
							Present = GambleConfig.getString(MachineNumber+".2");
						//Ʈ���� ���
						else if(ID[0]==266)
							Present = GambleConfig.getString(MachineNumber+".3");
						//Ʈ���� ���̾Ƹ��
						else if(ID[0]==264)
							Present = GambleConfig.getString(MachineNumber+".4");
						//Ʈ���� ���޶���
						else if(ID[0]==388)
							Present = GambleConfig.getString(MachineNumber+".5");
						//Ʈ���� �״� ��Ÿ
						else
						{
							Present = GambleConfig.getString(MachineNumber+".6");
							LuckyStar = true;
						}
					}
					//�ΰ��� ���� ���
					else if(ID[0]==ID[1]||ID[1]==ID[2]||ID[0]==ID[2])
					{
						int Slot = 263;
						if(ID[0]==ID[1]||ID[0]==ID[2])
							Slot = ID[0];
						else if(ID[1]==ID[2])
							Slot = ID[1];

						//���� �ھ�
						if(Slot==263)
							Present = GambleConfig.getString(MachineNumber+".10");
						//���� ���̾�
						else if(Slot==265)
							Present = GambleConfig.getString(MachineNumber+".11");
						//���� ���
						else if(Slot==266)
							Present = GambleConfig.getString(MachineNumber+".12");
						//���� ���̾Ƹ��
						else if(Slot==264)
							Present = GambleConfig.getString(MachineNumber+".13");
						//���� ���޶���
						else if(Slot==388)
							Present = GambleConfig.getString(MachineNumber+".14");
						//���� �״� ��Ÿ
						else
							Present = GambleConfig.getString(MachineNumber+".15");
					}
					//��� �ٸ� ���
					else
					{
						if(ID[0]==399||ID[1]==399||ID[2]==399)
							Present = GambleConfig.getString(MachineNumber+".9");
						else
							Present = GambleConfig.getString(MachineNumber+".0");
					}
					if(Present.compareTo("null")==0)
					{
						new GoldBigDragon_RPG.Effect.Sound().SP(player, Sound.IRONGOLEM_HIT, 0.8F, 0.9F);
						player.sendMessage(ChatColor.RED+"[���� �ӽ�] : ��! ���� ��ȸ��...");
					}
					else
					{
						YamlManager PresentList  = YC.getNewConfig("Item/GamblePresent.yml");
						String Grade = PresentList.getString(Present+".Grade");
						new GoldBigDragon_RPG.Effect.Sound().SP(player, Sound.LEVEL_UP, 1.0F, 1.8F);
						if(LuckyStar)
							Bukkit.broadcastMessage(ChatColor.GREEN+"[���� �ӽ�] : "+ChatColor.YELLOW+""+ChatColor.BOLD+player.getName()+ChatColor.GREEN+"�Բ��� "+ChatColor.YELLOW+""+ChatColor.BOLD+Present+" "+Grade+ChatColor.GREEN+" ��ǰ�� ��÷�Ǽ̽��ϴ�!");
						else
							player.sendMessage(ChatColor.GREEN+"[���� �ӽ�] : "+ChatColor.YELLOW+""+ChatColor.BOLD+Present+" "+Grade+ChatColor.GREEN+" ��ǰ�� ��÷�Ǽ̽��ϴ�!");

						Object[] Presents = PresentList.getConfigurationSection(Present+".Present").getKeys(false).toArray();
						for(byte counter=0;counter<Presents.length;counter++)
						{
							ItemStack item = PresentList.getItemStack(Present+".Present."+counter);
							new GoldBigDragon_RPG.Util.PlayerUtil().giveItemForce(player, item);
						}
					}
				}
			}
			ServerTickMain.PlayerTaskList.remove(STSO.getString((byte)0));
			ServerTickMain.Schedule.remove(UTC);
		}
	}
}