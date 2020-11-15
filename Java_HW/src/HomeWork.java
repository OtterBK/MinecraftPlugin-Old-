public class HomeWork
{
	public static void HomeWork_1_1()
	{
		System.out.print("수를 선택하세요. : ");
		int scannum = (new java.util.Scanner(System.in)).nextInt();
		int cnt = 1, num = 1, i;
		while(cnt<=5)
		{
			i = 0;
			while(i < cnt)
			{
				System.out.format("%2d ", num);
				num++;
				i++;
			}
			System.out.println("");
			cnt++;
		}
	}
	
	public static void HomeWork_1_2()
	{
		System.out.print("수를 선택하세요. : ");
		int scannum = (new java.util.Scanner(System.in)).nextInt();
		int cnt =1, num = 1, i = 0;
		while(cnt <= 5)
		{
			if(i < cnt)
			{
				System.out.format("%2d ", num);
				num++;
				i++;
				continue;
			}
			cnt++;
			i = 0;
			System.out.println("");
		}
	}
	
	public static void HomeWork_1_3()
	{
		int egg_weight = 0, egg_cnt = 0;
		while(true)
		{
			System.out.print("계란의 무게를 입력하세요(단위 : g) : ");
			egg_weight = (new java.util.Scanner(System.in)).nextInt();
			if(egg_weight < 300)
			{
				continue;
			}
			egg_cnt++;
			if(egg_cnt >= 5)
			{
				System.out.println("계란 한 판을 모두 담았습니다!");
				break;
			}
			System.out.println("현재 왕계란의 수 : "+egg_cnt);
		}
	}
	
	public static void HomeWork_2_1()
	{
		System.out.print("수를 선택하세요. : ");
		int scannum = (new java.util.Scanner(System.in)).nextInt();
		for(int cnt = 0; cnt < scannum; cnt++)
		{
			for(int i = 0, num = 1; i < scannum ;i++ )
			{	
				if((cnt-i) > 0)
				{
					System.out.print("   ");
					num += (i+1);
				}
				else
				{
					num += i;
					System.out.format("%3d", num);
				}
			}
			System.out.println();
		}
	}
	
	public static void HomeWork_2_2()
	{
		System.out.print("크기를 선택하세요. : ");
		int sn = (new java.util.Scanner(System.in)).nextInt();
		for(int i = 1, num = 1; i <= (sn*sn); i++)
		{
			System.out.format("%2d", num);
			num += 2;
			if(num > 9)
			{
				num = 1;
			}
			if(i % sn == 0)
			{
				System.out.println();
			}
		}
	}
	
	public static void HomeWork_2_3()
	{
		System.out.print("int 범위 내에 있는 자연수 내에서 입력 : ");
		int sn = (new java.util.Scanner(System.in)).nextInt();
		for(int i = 2; i < sn; i++)
		{
			if(sn % i == 0)
				System.out.format("%d ", i);
		}
	}
	
	public static void HomeWork_3_1()
	{
		for(int i = 1; i <= 9; i++)
		{
			int num = i;
			for(int j = 0; j < 8; j++)
			{
				System.out.format("%2d  ", (num+i));
				num += i;
			}
			System.out.println();
		}
	}
	
	/*public static void HomeWork_3_2()
	{
		System.out.print("수를 입력하세요. : ");
		String scanstr = (new java.util.Scanner(System.in)).nextLine();
		int snum = Integer.valueOf(scanstr);
		if(snum < 1 || snum > 1000000)
		{
			System.out.println("숫자의 범위는 1~1000000 입니다.");
			return;
		}
		String outstr = "";
		int outint = 0;
		for(int i = scanstr.length()-1; i >= 0; i--)
		{
			Integer.
			String strtmp = (String) scanstr.charAt(i);
			if(Integer.valueOf(scanstr.charAt(i)) != 0)
			{
				outstr += scanstr.charAt(i);
				outint += Integer.valueOf(scanstr.charAt(i));
			}
		}
		System.out.println("역순: "+outstr+", 합계: "+outint);
	}*/
	
	public static void HomeWork_3_3()
	{
		int num[] = new int[6];
		for(int i = 0; i <= 6;)
		{
			System.out.print("수를 입력하세요. : ");
			if()
			checktmp = (new java.util.Scanner(System.in)).nextInt();
		}
		int top_geti = 0;
		for(int i = 1; i < num.length; i++)
		{
			if(num[top_geti] < num[i])
			{
				top_geti = i;
			}
		}
		System.out.format("%d번쨰 요소의 값이 최대값 : %d입니다.", top_geti+1, num[top_geti]);
	}
}
