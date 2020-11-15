package GoldBigDragon_RPG.Util;

import java.util.Random;
//�ڹ� ���� ���̺귯�� ��, ���� �Լ��� ȣ����.

public class Number
{
    public boolean isNumeric(String str)
    //�����ΰ��� �˾Ƴ� �ִ� �޼ҵ�
    {  
      try  
      {  
        double d = Double.parseDouble(str);  
      }  
      catch(NumberFormatException nfe)  
      {  
        return false;  
      }  
      return true;  
    }
	
	//�ּ� ~ �ִ� �� ��, ������ ���� �����ϴ� �޼ҵ�//
	public int RandomNum(int min, int max)
    {
		if(min<=max)
			return new Random().nextInt((int) (max-min+1))+min;
		else
			return new Random().nextInt((int) (min-max+1))+max;
    }

    public boolean RandomPercent(double percent)
	{
		if (Math.random() <= percent)
		return true;
		return false;
	}
}
