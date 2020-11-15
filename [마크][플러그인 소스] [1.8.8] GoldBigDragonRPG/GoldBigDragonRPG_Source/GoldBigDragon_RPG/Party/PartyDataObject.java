package GoldBigDragon_RPG.Party;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import GoldBigDragon_RPG.Util.YamlController;
import GoldBigDragon_RPG.Util.YamlManager;
import net.md_5.bungee.api.ChatColor;

public class PartyDataObject
{
	private Long CreateTime = null;
	private String Title = null;
	private String Leader = null;
	private Boolean PartyLock = false;
	private String PartyPassword = null;
	private byte PartyCapacity = 2;
	private String[] PartyMember = null;
	
	public PartyDataObject(Long CreateTime, Player player, String PartyName)
	{
		this.Title = PartyName;
		this.CreateTime = CreateTime;
		this.Leader = player.getName();
		this.PartyMember = new String[this.PartyCapacity];
		this.PartyMember[0] = player.getName();
		
		GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.put(player, CreateTime);
		GoldBigDragon_RPG.Main.ServerOption.Party.put(CreateTime, this);
	}
	
	public PartyDataObject(Long CreateTime, String Leader,
			String Title, Boolean PartyLock, String PartyPassword, byte PartyCapacity,
			String[] PartyMember)
	{
		this.Title = Title;
		this.CreateTime = CreateTime;
		this.Leader = Leader;
		this.PartyLock = PartyLock;
		this.PartyPassword = PartyPassword;
		this.PartyCapacity = PartyCapacity;
		this.PartyMember = new String[this.PartyCapacity];
		
		for(byte count = 0; count < this.PartyCapacity; count++)
		{
			if(PartyMember[count]=="null")
				this.PartyMember[count] = null;
			else
				this.PartyMember[count] = PartyMember[count];
		}
	}
	
	public void ChangeMaxCpacity(Player player, byte Capacity)
	{
		if(player.getName().equals(this.Leader))
			if(Capacity >= getPartyMembers())
			{
				if(Capacity >= 2)
				{
					YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
					YamlManager Config = YC.getNewConfig("config.yml");
					if(Capacity <= Config.getInt("Party.MaxPartyUnit"))
					{
						this.PartyCapacity = Capacity;
						String[] TempMember = this.PartyMember;
						this.PartyMember = new String[Capacity];
						for(byte count = 0; count < this.PartyCapacity; count++)
							PartyMember[count] = null;
						byte a=0;
						for(byte count = 0; count < TempMember.length; count++)
						{
							if(TempMember[count]!=null&&TempMember[count]!="null")
							{
								PartyMember[a] = TempMember[count];
								a++;
							}
						}
						PartyBroadCastMessage(ChatColor.GREEN+"[��Ƽ] : �ִ� ��Ƽ�� ���� "+ChatColor.YELLOW+""+ChatColor.BOLD+Capacity+"��"+ChatColor.GREEN+"���� ����Ǿ����ϴ�!",null);
						PartyBroadCastSound(Sound.ITEM_PICKUP, 1.0F, 1.0F,null);
					}
					else
						Message(player, (byte)3);
				}
				else
					Message(player, (byte)10);
			}
			else
				Message(player, (byte)2);
		else
			Message(player, (byte)1);
	}

	public void ChangeLock(Player player)
	{
		if(player.getName().equals(this.Leader))
		{
			if(this.PartyLock == false)
			{
				this.PartyLock = true;
				PartyBroadCastMessage(ChatColor.RED+"[��Ƽ] : ���̻� ��Ƽ ������ ���� �ʽ��ϴ�!",null);
				PartyBroadCastSound(Sound.ANVIL_LAND, 1.0F, 1.0F,null);
			}
			else
			{
				this.PartyLock = false;
				PartyBroadCastMessage(ChatColor.GREEN+"[��Ƽ] : ��Ƽ ������ ���� �մϴ�!",null);
				PartyBroadCastSound(Sound.ITEM_PICKUP, 1.0F, 1.8F,null);
			}
		}
		else
			Message(player, (byte)1);
	}
	
	public void SetPassword(Player player, String Message)
	{
		if(player.getName().equals(this.Leader))
		{
			this.PartyPassword = Message;
			PartyBroadCastMessage(ChatColor.YELLOW+"[��Ƽ] : ��ȣ�� ���� �����Ǿ����ϴ�!",null);
			PartyBroadCastSound(Sound.ITEM_PICKUP, 1.0F, 1.8F,null);
		}
		else
			Message(player, (byte)1);
	}
	
	public void JoinParty(Player player)
	{
		if(GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.containsKey(player) == false)
			if(this.PartyCapacity > getPartyMembers())
				if(this.PartyLock == false)
					if(this.PartyPassword == null)
					{
						for(byte count = 0; count < this.PartyCapacity; count++)
							if(this.PartyMember[count] == null)
							{
								if(player.isOnline())
								{
									player.sendMessage(ChatColor.GREEN + "[��Ƽ] : ��Ƽ�� ���� �Ͽ����ϴ�!");
									new GoldBigDragon_RPG.Effect.Sound().SP(player, Sound.DOOR_OPEN, 1.1F, 1.0F);
								}
								this.PartyMember[count] = player.getName();
								GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.put(player, this.CreateTime);
								PartyBroadCastMessage(ChatColor.GREEN+"[��Ƽ] : "+ChatColor.YELLOW+""+ChatColor.BOLD+player.getName()+ChatColor.GREEN+"�Բ��� ��Ƽ�� �����ϼ̽��ϴ�!",player);
								PartyBroadCastSound(Sound.DOOR_OPEN, 1.1F, 1.0F,player);
								return;
							}
					}
					else
					{
						
					}
				else
					Message(player, (byte)5);
			else
				Message(player, (byte)4);
		else
			Message(player, (byte)6);
	}

	public void QuitParty(Player player)
	{
		GoldBigDragon_RPG.Main.ServerOption.PartyJoiner.remove(player);
		if(player.isOnline())
		{
			player.sendMessage(ChatColor.RED + "[��Ƽ] : ��Ƽ�� Ż���Ͽ����ϴ�!");
			new GoldBigDragon_RPG.Effect.Sound().SP(player, Sound.DOOR_CLOSE, 1.1F, 1.0F);
		}
		if(getPartyMembers() == 1)
		{
			GoldBigDragon_RPG.Main.ServerOption.Party.remove(this.CreateTime);
			return;
		}
		for(byte count = 0; count < this.PartyCapacity; count++)
			if(this.PartyMember[count] == player.getName()||this.PartyMember[count].equals(player.getName()))
			{
				PartyBroadCastMessage(ChatColor.RED+"[��Ƽ] : "+ChatColor.YELLOW+""+ChatColor.BOLD+player.getName()+ChatColor.RED+"�Բ��� ��Ƽ�� Ż���ϼ̽��ϴ�!",player);
				PartyBroadCastSound(Sound.DOOR_CLOSE, 1.1F, 1.0F,player);
				for(byte counter = count; counter < this.PartyCapacity-1; counter++)
					this.PartyMember[counter] = this.PartyMember[counter+1];
				this.PartyMember[this.PartyMember.length-1] = null;
				break;
			}
		if(player.getName().equals(this.Leader))
		{
			for(byte count = 0; count < this.PartyCapacity; count++)
				if(this.PartyMember[count] != null)
				{
					ChangeLeader(Bukkit.getServer().getPlayer(this.PartyMember[count]));
					break;
				}
		}
	}
	
	public void QuitPartyOfflinePlayer(String playerName)
	{
		if(getPartyMembers() == 1)
		{
			GoldBigDragon_RPG.Main.ServerOption.Party.remove(this.CreateTime);
			return;
		}
		for(byte count = 0; count < this.PartyCapacity; count++)
			if(this.PartyMember[count]!=null)
				if(this.PartyMember[count].compareTo(playerName) == 0)
				{
					PartyBroadCastMessage(ChatColor.RED+"[��Ƽ] : "+ChatColor.YELLOW+""+ChatColor.BOLD+playerName+ChatColor.RED+"�Բ��� ��Ƽ�� Ż���ϼ̽��ϴ�!",null);
					PartyBroadCastSound(Sound.DOOR_CLOSE, 1.1F, 1.0F,null);
					for(byte counter = count; counter < this.PartyCapacity-1; counter++)
						this.PartyMember[counter] = this.PartyMember[counter+1];
					this.PartyMember[this.PartyMember.length-1] = null;
					break;
				}
		if(playerName.equals(this.Leader))
		{
			for(byte count = 0; count < this.PartyCapacity; count++)
				if(this.PartyMember[count] != null)
				{
					ChangeLeader(Bukkit.getServer().getPlayer(this.PartyMember[count]));
					break;
				}
		}
	}
	
	public void ChangeLeader(Player player)
	{
		this.Leader = player.getName();
		PartyBroadCastMessage(ChatColor.YELLOW+"[��Ƽ] : "+ChatColor.YELLOW+""+ChatColor.BOLD+player.getName()+ChatColor.YELLOW+"�Բ��� ��Ƽ ������ �Ǽ̽��ϴ�!",null);
		PartyBroadCastSound(Sound.ITEM_PICKUP, 1.0F, 1.5F,null);
	}
	
	public void ChangeLeader(Player player,String target)
	{
		if(player.getName().equals(this.Leader))
		{
			for(byte count = 0; count < getMember().length; count ++)
			{	if(getMember()[count].getName() == target ||getMember()[count].getName().equals(target))
				{
					if(Bukkit.getServer().getOfflinePlayer(target).isOnline())
						if(this.Leader != target &&!this.Leader.equals(target))
							ChangeLeader(Bukkit.getPlayer(target));
						else
							Message(player, (byte)9);
					else
						Message(player, (byte)8);
					return;
				}
			}
			Message(player, (byte)11);
		}
		else
			Message(player, (byte)1);
	}

	public void ChangeTitle(Player player, String Message)
	{
		if(player.getName().equals(this.Leader))
		{
			this.Title = Message;
			PartyBroadCastMessage(ChatColor.YELLOW+"[��Ƽ] : ��Ƽ ������ ����Ǿ����ϴ�!",null);
			PartyBroadCastSound(Sound.ITEM_PICKUP, 1.0F, 1.8F,null);
		}
		else
			Message(player, (byte)1);
	}
	
	public void KickPartyMember(Player player, String target)
	{
		if(player.getName()==this.Leader||player.getName().equals(this.Leader))
		{
			if(player.getName().equals(target)==false)
				if(Bukkit.getServer().getOfflinePlayer(target).isOnline())
					for(byte count = 0; count < this.PartyCapacity; count++)
					{
						if(this.PartyMember[count].equals(target))
						{
							QuitParty((Player) Bukkit.getServer().getPlayer(target));
							return;
						}
					}
				else
					Message(player, (byte)8);
			else
				Message(player, (byte)7);
		}
		else
			Message(player, (byte)1);
	}
	
	public String getLeader()
	{
		return this.Leader;
	}
	
	public Player[] getMember()
	{
		Player[] p = new Player[this.PartyCapacity];
		byte a = 0;
		for(byte count = 0; count < this.PartyCapacity; count++)
			if(this.PartyMember[count] != null)
				if(Bukkit.getServer().getOfflinePlayer(this.PartyMember[count]).isOnline())
				{
					p[a] = Bukkit.getServer().getPlayer(this.PartyMember[count]);
					a++;
				}
				else
					this.PartyMember[count] = null;
		Player[] pp = new Player[a];
		for(byte count = 0; count < pp.length; count++)
			pp[count] = p[count];
		
		return pp;
	}
	
	public boolean getLock()
	{
		return this.PartyLock;
	}

	public String getTitle()
	{
		return this.Title;
	}

	public int getCapacity()
	{
		return this.PartyCapacity;
	}

	public String getPassword()
	{
		return this.PartyPassword;
	}
	
	public void getPartyInformation()
	{
		
	}
		
	public int getPartyMembers()
	{
		int Members = 0;
		for(byte count = 0; count < this.PartyCapacity; count++)
			if(this.PartyMember[count] != null)
				if(Bukkit.getServer().getOfflinePlayer(this.PartyMember[count]).isOnline())
					Members = Members+1;
		return Members;
	}
	
	public void PartyBroadCastMessage(String Message, Player noAlertMember)
	{
		Player[] p = getMember();
		
		for(byte count = 0; count < p.length; count++)
		{
			if(p[count] != null && p[count] != noAlertMember)
				if(Bukkit.getServer().getOfflinePlayer(p[count].getName()).isOnline())
					p[count].sendMessage(Message);
		}
	}

	public void PartyBroadCastSound(Sound s, float volume, float pitch, Player noAlertMember)
	{
		GoldBigDragon_RPG.Effect.Sound sound = new GoldBigDragon_RPG.Effect.Sound();
		Player[] p = getMember();
		for(byte count = 0; count < p.length; count++)
			if(p[count] != null && p[count] != noAlertMember)
				if(Bukkit.getServer().getOfflinePlayer(p[count].getName()).isOnline())
					sound.SP(p[count], s, volume, pitch);
	}
	
	public void Message(Player player, byte num)
	{
		GoldBigDragon_RPG.Effect.Sound sound = new GoldBigDragon_RPG.Effect.Sound();
		sound.SP(player, Sound.ORB_PICKUP, 1.0F, 1.8F);
		switch(num)
		{
		case 1:
			player.sendMessage(ChatColor.RED + "[��Ƽ] : ����� ��Ƽ ������ �ƴմϴ�!");
			return;
		case 2:
			player.sendMessage(ChatColor.RED + "[��Ƽ] : �ִ� �ο����� ���� ��Ƽ �ο� �� ���� ������ �� �����ϴ�!");
			return;
		case 3:
			{
				YamlController YC = new YamlController(GoldBigDragon_RPG.Main.Main.plugin);
				YamlManager Config = YC.getNewConfig("config.yml");
				player.sendMessage(ChatColor.RED + "[��Ƽ] : �ִ� ��Ƽ �ο� �� : " +ChatColor.YELLOW+""+ChatColor.BOLD+""+Config.getInt("Party.MaxPartyUnit")+"��");
			}
			return;
		case 4:
			player.sendMessage(ChatColor.RED + "[��Ƽ] : ��Ƽ �ο��� �� á���ϴ�!");
			return;
		case 5:
			player.sendMessage(ChatColor.RED + "[��Ƽ] : �ش� ��Ƽ�� ���̻� ��Ƽ���� �������� �ʽ��ϴ�!");
			return;
		case 6:
			player.sendMessage(ChatColor.RED + "[��Ƽ] : ����� �̹� �ٸ� ��Ƽ�� ���� ���Դϴ�!");
			return;
		case 7:
			player.sendMessage(ChatColor.RED + "[��Ƽ] : �ڱ� �ڽ��� ���� ��ų �� �����ϴ�!");
			return;
		case 8:
			player.sendMessage(ChatColor.RED + "[��Ƽ] : �ش� �÷��̾�� ��Ƽ���� �ƴմϴ�!");
			return;
		case 9:
			player.sendMessage(ChatColor.RED + "[��Ƽ] : ����� �̹� ��Ƽ �����Դϴ�!");
			return;
		case 10:
			player.sendMessage(ChatColor.RED + "[��Ƽ] : ��Ƽ �ο��� �ּ� 2�� �̻��̾�� �մϴ�!");
			return;
		case 11:
			player.sendMessage(ChatColor.RED + "[��Ƽ] : �ش� �÷��̾�� ��Ƽ���� �ƴմϴ�!");
			return;
		}
	}
}
