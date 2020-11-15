package GoldBigDragon_RPG.Util;

import java.util.Calendar;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

//�ڹ� ���� ���̺귯�� ��, ��¥ �Լ��� ȣ����.

public class ETC
{
	public Date date = new Date();
	//��¥ �Լ��� ȣ���Ͽ� date�� �� �̸����� �����ϴ� �ܶ�

	public String getFrom(long nowUTC, long PrevUTC)
	{
		if(nowUTC-PrevUTC<0)
			return "�� �� ����";
		else
		{
			long WaitingTime = (nowUTC-PrevUTC)/1000;
			short year = 0;
			byte month = 0;
			byte day = 0;
			byte hour = 0;
			byte min = 0;
			String pastedTime="";
			if(WaitingTime >= 31536000)
			{
				year = (short) (WaitingTime/31536000);
				WaitingTime = WaitingTime-(31536000*year);
			}
			if(WaitingTime >= 2678400)
			{
				month = (byte) (WaitingTime/2678400);
				WaitingTime = WaitingTime-(2678400*month);
			}
			if(WaitingTime >= 86400)
			{
				day = (byte) (WaitingTime/86400);
				WaitingTime = WaitingTime-(86400*day);
			}
			if(WaitingTime >= 3600)
			{
				hour = (byte) (WaitingTime/3600);
				WaitingTime = WaitingTime-(3600*hour);
			}
			if(WaitingTime >= 60)
			{
				min = (byte) (WaitingTime/60);
				WaitingTime = WaitingTime-(60*min);
			}
			if(year!=0)
				pastedTime = year+"��";
			if(month!=0)
				pastedTime = pastedTime+month+"����";
			if(day!=0)
				pastedTime = pastedTime+day+"��";
			if(hour!=0)
				pastedTime = pastedTime+hour+"�ð�";
			if(min!=0)
				pastedTime = pastedTime+min+"��";
			pastedTime = pastedTime+WaitingTime+"��";
			return pastedTime;
		}
	}
	
	public String getSchedule(long UTC)
	{
		Date a = new Date(UTC);
		date.after(a);
		return date.getYear()+"�� "+date.getMonth()+"�� "+date.getDay()+"�� "+
		date.getHours()+"�� "+date.getMinutes()+"��";
	}
	
	public long getNowUTC()
	{
		return date.UTC(date.getYear(),date.getMonth(),date.getDate(),date.getHours(),date.getMinutes(),date.getSeconds());
	}
	
    public long getSec()
    {
    	Calendar Calender = Calendar.getInstance();
    	return Calender.getTimeInMillis();
    }
    
    //���� �ʸ� ��Ÿ���� �޼ҵ�//
    public int getSecond()
    {
    	return date.getSeconds();
    }
    
    //���� ���� ��Ÿ���� �޼ҵ�//
    public int getMin()
    {
    	return date.getMinutes();
    }
    
    //���� �ð��� ��Ÿ���� �޼ҵ�//
    public int getHour()
    {
    	return date.getHours();
    }
    
    //���� ������ ��Ÿ���� �޼ҵ�//
    public int getDay()
    {
    	return date.getDate();
    }
    
    //���� ���� ��Ÿ���� �޼ҵ�//
    public int getMonth()
    {
    	return date.getMonth()+1;
    }

    //���� �⵵�� ��Ÿ���� �޼ҵ�//
    public int getYear()
    {
    	return date.getYear()+1900;
    }
    
    //��/��/�� ���� �����ִ� �޼ҵ�//
    public String getToday()
    {
    	return getYear() + "." + getMonth() + "." + getDay();
    }

    //������ ������ ����� ���� ���ϴ� �޼ҵ�//
    public String Today()
	{
		Calendar Calender = Calendar.getInstance();
		//���� �⵵, ��, ��
		short year = (short)Calender.get ( Calendar.YEAR );
		byte month = (byte)(Calender.get ( Calendar.MONTH ) + 1);
		byte date = (byte)Calender.get ( Calendar.DATE ) ;
		//���� �ð�(��,��,��)
		byte hour = (byte)Calender.get ( Calendar.HOUR_OF_DAY ) ;
		int zellerMonth;
		int zellerYear;
		String Today = null;
		if(month < 3) { // ������ 3���� ������
		    
		    zellerMonth = month + 12; // �� + 12
		    zellerYear = year - 1; // �� - 1
		}

		else {
			zellerMonth = month;
			zellerYear = year;
		}
		   
		int computation = date + (26 * (zellerMonth + 1)) / 10 + zellerYear + 
		                  zellerYear / 4 + 6 * (zellerYear / 100) +
		                  zellerYear / 400;
		int dayOfWeek = computation % 7;
		
		
		 switch(dayOfWeek) // 0~6���� ��~�ݿ��Ϸ� ǥ��
		    {
		     
		      case 0:
		    	  Today = "�����";
		          break;
		      case 1:
		    	  Today = "�Ͽ���";
		          break;
		      case 2:
		    	  Today = "������";
		          break;
		      case 3:
		    	  Today = "ȭ����";
		          break;
		      case 4:
		    	  Today = "������";
		          break;
		      case 5:
		    	  Today = "����� ";
		          break;
		      case 6:
		    	  Today = "�ݿ���";
		          break;
		    }   
		 return Today;
		    
	}
	
    public void UpdatePlayerHPMP(Player player)
    {
		if(Bukkit.getPluginManager().isPluginEnabled("MagicSpells") == true
		&&GoldBigDragon_RPG.Main.ServerOption.MagicSpellsCatched==true)
		{
			OtherPlugins.SpellMain MS = new OtherPlugins.SpellMain();
			MS.setPlayerMaxAndNowMana(player);
		}
		GoldBigDragon_RPG.Attack.Damage d = new GoldBigDragon_RPG.Attack.Damage();
		Damageable p = player;
		int BonusHealth = d.getPlayerEquipmentStat(player, "�����", null, false)[0];
		int MaxHealth = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_MaxHP()+BonusHealth;
		if(MaxHealth > 0)
			p.setMaxHealth(MaxHealth);
		return;
    }
    
    public void SlotChangedUpdatePlayerHPMP(Player player, ItemStack newSlot, boolean isHotbarChange)
    {
		if(GoldBigDragon_RPG.Main.ServerOption.MagicSpellsCatched == true)
		{
			OtherPlugins.SpellMain MS = new OtherPlugins.SpellMain();
			MS.setSlotChangePlayerMaxAndNowMana(player, newSlot, isHotbarChange);
		}
		GoldBigDragon_RPG.Attack.Damage d = new GoldBigDragon_RPG.Attack.Damage();
		Damageable p = player;

		int BonusHealth = d.getPlayerEquipmentStat(player, "�����", newSlot, isHotbarChange)[0];
		int MaxHealth = GoldBigDragon_RPG.Main.ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_MaxHP()+BonusHealth;

		if(MaxHealth > 0)
			p.setMaxHealth(MaxHealth);
		return;
    }
}
