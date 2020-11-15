package GoldBigDragon_RPG.Dungeon;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SeedMaker
{
	int size;//������ ũ��
	int Maze_Level;//���� ���� ��ġ
	int TotalRoom;//�� ���踦 ���� �� �ִ� �� ����
	List<Integer> RightPass;//�ùķ��̼ǽ� ��� ������ ���踦 �����ϴ� ����.
	List<Integer> PassedRoom;//�ùķ��̼ǽ� ��� ������ ���踦 �����ϴ� ����.
	char whenIChoosed;//�ùķ��̼ǽ� ����� ���� ��ĥ �� �̵��� ���� ���� (��/��/��/�� ������ ã��)
	int whenIsaved;//�ùķ��̼ǽ� ���� 2���� �̻����� ������ �� ��ġ�� ����
	boolean isNoExit=false;//�ùķ��̼ǽ�, �̾����� ���� ���� �̾� �� ������ ����ϴ� ���� �����ϱ� ���� ����.
	int Tryed = 0;
	int StartPoint = 0;
	int BossRoom;
	int[] LoopBreaker = {0,0,0,0,0,0,0,0,0,0};
	
	boolean PerfectDungeon = false;//�ù� ���н� �ٽ� �ϵ��� �ϴ� ����.
	
	String[] Grid;//���� ��ġ �׸��带 ����.
	String[] KeyGrid;//���� �� ��ġ �׸��带 ����.
	int[] RoomCounter;//������ ���� ���� �� ���� ���� ����� ������� �ش� ��ġ�� ������.
	
	public int Randomnum(int min, int max)
    {
		return new Random().nextInt(max-min+1)+min;
    }

/*
01. �� ��ü�� ������ ä���.
02. ������ ���� ���´�.
03. ������ �Ա����� ���� ����Ѵ�.
04. � ���ο� ��Ҹ� ���� ���ΰ��� ���Ͽ� �����Ѵ�.
05. ������ ���̵�(����) �ʿ� �ش� ��Ҹ� �߰���ų �� �ִ� ������ �ִ��� Ȯ���Ѵ�.
06. ���� �߰���ų ������ �ִٸ� ����ϰ�, ���ٸ� 3�� �׸����� �ٽ� ���ư���.
07. ������ ���̵忡 �ش� ��Ҹ� �߰� ��Ų��.
08. ������ �ϼ��� �� ���� 3�� �׸����� ���ư���.
09. ���� �� ���� ��ġ�� ��� ������ �����Ѵ�.
10. ��� ������ ���� ����� ��� ���� ��Ų��.
 */	

	//���� ������//
	public String CreateSeed(int DungeonSize, String[] grid, int[] roomcounter, int Maze_Level, String DungeonType)
	{
		this.size = DungeonSize;
		this.Maze_Level = Maze_Level;
		this.Grid = grid;
		this.RoomCounter = roomcounter;
		KeyGrid = new String[size*size];
	    int spot;
	    for(;;)
	    {
	    	TotalRoom = 0;
	    	for(int count = 0; count < size*size;count++)
	    	{
	    		KeyGrid[count] = null;
	    		Grid[count] = null;
	    		RoomCounter[count] = -1;
	    	}
		    BossRoom = setBossRoom();
		    spot = BossRoom;
		    
		    //���� �� ���� ���� �ϳ��� �ֵ� ��Ʈ��
	    	setRoom(BossRoom,searchEmptyWall(spot), false);
		    for(int count = 0; count<Maze_Level*3;count ++)
		    {
		    	for(int count2 = size*size-1; count2 >= 0 ;count2--)
		    	{
		    		if(RoomCounter[count2] != -1)
		    		{
		    			spot = RoomCounter[count2];
		    			break;
		    		}
		    	}
		    	setRoom(spot,searchEmptyWall(spot), false);
		    }
	    	
	    	if(TotalRoom>=size-(size/2)-1)
	    		break;
	    }
	    
    	for(int count = (size*size)-1; count >= 0 ;count--)
    	{
    		if(RoomCounter[count] != -1)
    		{
    			Grid[RoomCounter[count]] = "��";
    			TotalRoom = TotalRoom-1;//���踦 ���� �� �ִ� �� ���� 1����. ������ ���� ������ ���� �߱� ����.
    			StartPoint = RoomCounter[count];
    			whenIChoosed = 'V';//V�� �������� ���� ���� ������ ����.
    			whenIsaved = RoomCounter[count];//���������� -1�� ���� �������� ������ �ʾ����� ���Ѵ�.
    			RightPass = new ArrayList<Integer>();
    			PassedRoom = new ArrayList<Integer>();
    			RightPass.add(0,whenIsaved);
	    		DungeonClearSimulation(whenIsaved, whenIChoosed, false);
	    		//������ ���Ƿ� ���� Ŭ���� �����ϰ� ���� �̾� ������ �˻�
	    		if(PerfectDungeon==true)
	    			if(DoorCreator()==true)//����� �� ������ ����
	    				return createMap();
	    		return "null";
    		}
    	}
    	return "null";
	}

	public int setBossRoom()
	{
		int bossEnter = 0;
		if(size%2==1)
			bossEnter=bossEnter+size+1;
		for(int count = 0; count < size/2;count++)
			bossEnter = bossEnter + size;
		bossEnter = bossEnter-(size/2)+(size*2)-1;
	    Grid[bossEnter] = "��";
	    Grid[bossEnter-size+1] = "��";
	    Grid[bossEnter-size] = "��";
	    Grid[bossEnter-size-1] = "��";
	    Grid[bossEnter-(size*2)+1] = "��";
	    Grid[bossEnter-(size*2)] = "��";
	    Grid[bossEnter-(size*2)-1] = "��";
	    Grid[bossEnter-(size*3)+1] = "��";
	    Grid[bossEnter-(size*3)] = "��";
	    Grid[bossEnter-(size*3)-1] = "��";
	    Grid[bossEnter-(size*4)] = "��";
		return bossEnter;
	}
	
	public char searchEmptyWall(int location)
	{
		boolean isEmptyN = false;
		boolean isEmptyE = false;
		boolean isEmptyW = false;
		boolean isEmptyS = false;
		if(location+size <= size*size-1)
    		if(Grid[location+size] == null)
    			isEmptyS = true;
		if(location-size >=0)
    		if(Grid[location-size] == null)
    			isEmptyN = true;
		if(location%size == 0)
	    {
    		if(location+1 <= size*size-1)
	    		if(Grid[location+1] == null)
	    			isEmptyE = true;
	    }
    	else if(location%size == size-1)
	    {
    		if(location-1 >=0)
	    		if(Grid[location-1] == null)
	    			isEmptyW = true;
	    }
    	else
    	{
    		if(location+1 <= size*size-1)
	    		if(Grid[location+1] == null)
	    			isEmptyE = true;
    		if(location-1 >= 0)
	    		if(Grid[location-1] == null)
	    			isEmptyW = true;
    	}
		
		
		if(isEmptyN==false&&isEmptyE==false&&isEmptyW==false&&isEmptyS==false)
			return 'V';
		for(int count =0;count < 100;count++)
		{
			char randomnum = (char) Randomnum(0, 3);
				switch(randomnum)
				{
				case 0:
					if(isEmptyE == true)
						return 'E';
					break;
				case 1:
					if(isEmptyW == true)
						return 'W';
					break;
				case 2:
					if(isEmptyN == true)
						return 'N';
					break;
				case 3:
					if(isEmptyS == true)
						return 'S';
					break;
				}
		}
	return 'V';
	}
	
	public boolean EnableContinuouslyRoom_SearchSouth(int location)
	{
		if(location+size <= size*size-1)
		{
			if(Grid[location+size] != "��"&&Grid[location+size] != "��"&&
			Grid[location+size] != "��"&&Grid[location+size] != "��"&&
			Grid[location+size] != "��"&&Grid[location+size] != "��"&&
					Grid[location+size] != "��"&&Grid[location+size] != "��"&&Grid[location+size] != "��"&&Grid[location+size] != "��")
    			return true;
			else
				return false;
		}
		else
			return true;
	}
	
	public boolean EnableContinuouslyRoom_SearchNorth(int location)
	{
		if(location-size >= 0)
		{
			if(Grid[location-size] != "��"&&Grid[location-size] != "��"&&Grid[location-size] != "��"&&Grid[location-size] != "��"&&
					Grid[location-size] != "��"&&Grid[location-size] != "��"&&Grid[location-size] != "��"&&Grid[location-size] != "��"&&Grid[location-size] != "��"&&Grid[location-size] != "��")
    			return true;
			else
				return false;
		}
		else
			return true;
	}
	
	public boolean EnableContinuouslyRoom_SearchEast(int location)
	{
		if(location+1 <= size*size-1)
		{
			if(Grid[location+1] != "��"&&Grid[location+1] != "��"&&Grid[location+1] != "��"&&Grid[location+1] != "��"&&
					Grid[location+1] != "��"&&Grid[location+1] != "��"&&Grid[location+1] != "��"&&Grid[location+1] != "��"&&Grid[location+1] != "��"&&Grid[location+1] != "��")
    			return true;
			else
				return false;
		}
		else
			return true;
	}
	
	public boolean EnableContinuouslyRoom_SearchWest(int location)
	{
		if(location-1 >= 0)
		{
			if(Grid[location-1] != "��"&&Grid[location-1] != "��"&&Grid[location-1] != "��"&&Grid[location-1] != "��"&&
					Grid[location-1] != "��"&&Grid[location-1] != "��"&&Grid[location-1] != "��"&&Grid[location-1] != "��"&&Grid[location-1] != "��"&&Grid[location-1] != "��")
    			return true;
			else
				return false;
		}
		else
			return true;
	}
	
	public boolean EnableContinuouslyRoom(int location, char Direction)
	{
		boolean isEmptyN = false;
		boolean isEmptyE = false;
		boolean isEmptyW = false;
		boolean isEmptyS = false;
		switch(Direction)
		{
		case 'E':isEmptyW=true;break;
		case 'W':isEmptyE=true;break;
		case 'S':isEmptyN=true;break;
		case 'N':isEmptyS=true;break;
		}
		if(location/size == 0)
		{
    		if(EnableContinuouslyRoom_SearchSouth(location))
    			isEmptyS = true;
    		if(EnableContinuouslyRoom_SearchNorth(location))
    			isEmptyN = true;
    		if(EnableContinuouslyRoom_SearchEast(location))
    			isEmptyE = true;
			isEmptyW = true;
		}
    	else if(location/size == size-1)
    	{
    		if(EnableContinuouslyRoom_SearchSouth(location))
    			isEmptyS = true;
    		if(EnableContinuouslyRoom_SearchNorth(location))
    			isEmptyN = true;
    		if(EnableContinuouslyRoom_SearchWest(location))
    			isEmptyW = true;
    		isEmptyE = true;
    	}
    	else
    	{
    		if(EnableContinuouslyRoom_SearchSouth(location))
    			isEmptyS = true;
    		if(EnableContinuouslyRoom_SearchNorth(location))
    			isEmptyN = true;
    		if(EnableContinuouslyRoom_SearchWest(location))
    			isEmptyE = true;
    		if(EnableContinuouslyRoom_SearchWest(location))
    			isEmptyW = true;
    	}
		
		
		if(isEmptyN==true&&isEmptyE==true&&isEmptyW==true&&isEmptyS==true)
			return true;
		else
			return false;
	}
	

	public String randomRoom()
	{
		byte number = (byte) Randomnum(0, 3);
		TotalRoom=TotalRoom+1;
		if(number==0)
			return "��";//�̹͹�
		return "��";
	}
	
	public int setRoom(int location, char side, boolean randomRoadConnect)
	{
		char type;
		switch(side)
		{
		case 'N' :
			if(location-size >=0&& location <= size*size-1)
			{
				type = searchEmptyWall(location-size);
				if(type == 'N')
					RoomCreate(location-size, "��",type);
				else if(type == 'E')
					RoomCreate(location-size, "��",type);
				else if(type == 'W')
					RoomCreate(location-size, "��",type);
				setRoom(location-size, type, randomRoadConnect);
				return location-size;
			}
			break;
		case 'E' :
			if(location+1 <= size*size-1&& location+1 >= 0)
			{
				type = searchEmptyWall(location+1);
				if(type == 'N')
					RoomCreate(location+1, "��",type);
				else if(type == 'S')
					RoomCreate(location+1, "��",type);
				else if(type == 'E')
					RoomCreate(location+1, "��",type);
				setRoom(location+1, type, randomRoadConnect);
				return location+1;
			}
			break;
		case 'W' :
			if(location-1 >= 0 && location <= size*size-1)
			{
				type = searchEmptyWall(location-1);
				if(type == 'N')
					RoomCreate(location-1, "��",type);
				else if(type == 'S')
					RoomCreate(location-1, "��",type);
				else if(type == 'W')
					RoomCreate(location-1, "��",type);
				setRoom(location-1, type, randomRoadConnect);
				return location-1;
			}
			break;
		case 'S' :
			if(location+size <= size*size-1&& location+size >= 0)
			{
				type = searchEmptyWall(location+size);
				if(type == 'S')
					RoomCreate(location+size, "��",type);
				else if(type == 'E')
					RoomCreate(location+size, "��",type);
				else if(type == 'W')
					RoomCreate(location+size, "��",type);
				setRoom(location+size, type, randomRoadConnect);
				return location+size;
			}
			break;
		}
		return -1;
	}
	
	public void randomRoadConnect()
	{
		int randomroad;
		char side;
		for(int count = 0; count < 100; count ++)
		{
			randomroad = Randomnum(0, (size*size)-1);
			if(Grid[randomroad] != null&&Grid[randomroad] != "��"&&Grid[randomroad] != "��"&&Grid[randomroad] != "��"&&Grid[randomroad] != "��"
					&&Grid[randomroad] != "��")
			{
				side = searchEmptyWall(randomroad);
				if(side != 'V')
				{
					switch(Grid[randomroad])
					{
					case "��" :
						switch(side)
						{
						case 'N' : Grid[randomroad] = "��"; setRoom(randomroad, 'N', true);break;
						case 'S' : Grid[randomroad] = "��"; setRoom(randomroad, 'S', true);break;
						}
						break;
					case "��" :
						switch(side)
						{
						case 'E' : Grid[randomroad] = "��"; setRoom(randomroad, 'E', true);break;
						case 'W' : Grid[randomroad] = "��"; setRoom(randomroad, 'W', true);break;
						}
						break;
					case "��" :
						switch(side)
						{
						case 'N' : Grid[randomroad] = "��"; setRoom(randomroad, 'N', true);break;
						case 'W' : Grid[randomroad] = "��"; setRoom(randomroad, 'W', true);break;
						}
						break;
					case "��" :
						switch(side)
						{
						case 'N' : Grid[randomroad] = "��"; setRoom(randomroad, 'N', true);break;
						case 'E' : Grid[randomroad] = "��"; setRoom(randomroad, 'E', true);break;
						}
						break;
					case "��" :
						switch(side)
						{
						case 'S' : Grid[randomroad] = "��"; setRoom(randomroad, 'S', true);break;
						case 'E' : Grid[randomroad] = "��"; setRoom(randomroad, 'E', true);break;
						}
						break;
					case "��" :
						switch(side)
						{
						case 'S' : Grid[randomroad] = "��"; setRoom(randomroad, 'S', true);break;
						case 'W' : Grid[randomroad] = "��"; setRoom(randomroad, 'W', true);break;
						}
						break;
					case "��" :
						switch(side)
						{
						case 'W' : Grid[randomroad] = "��"; setRoom(randomroad, 'W', true);break;
						}
						break;
					case "��" :
						switch(side)
						{
						case 'N' : Grid[randomroad] = "��"; setRoom(randomroad, 'N', true);break;
						}
						break;
					case "��" :
						switch(side)
						{
						case 'E' : Grid[randomroad] = "��"; setRoom(randomroad, 'E', true);break;
						}
						break;
					case "��" :
						switch(side)
						{
						case 'S' : Grid[randomroad] = "��"; setRoom(randomroad, 'S', true);break;
						}
						break;
					}
				}
			}
		}
	}
	
	public void RoomCreate(int location, String elseput, char Direction)
	{
		if(Randomnum(0, 4)==0)
		{
			if(EnableContinuouslyRoom(location, Direction))
			{
				RoomCounter[TotalRoom] = location;
				Grid[location] = randomRoom();
			}
			else
				Grid[location] = elseput;
		}
		else
			Grid[location] = elseput;
	}

	public boolean SearchRoad(int location, char Direction)
	{
		String Shape = null;
		String NowShape = Grid[location];
		switch(Direction)
		{
		case 'N':
			if(location-size >= 0)
			{
				Shape = Grid[location-size];
				if(isNoExit)
				{
					if(Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��"||
					Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��")
						if(NowShape == "��"||NowShape=="��"||NowShape=="��"||NowShape=="��"||NowShape=="��"||
						NowShape=="��"||NowShape=="��"||NowShape=="��"||NowShape=="��"
						||NowShape=="��"||NowShape=="��")
							return true;
				}
				else
					if(Shape == "��"||Shape == "��"||Shape == "��"||
					Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��")
						return true;
			}
			return false;
		case 'E':
			if(location%size == 0||location%size != size-1)
			{
				if(location+1 <= size*size-1)
				{
					Shape = Grid[location+1];
					if(isNoExit)
					{
						if(Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��"||
	    				Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��")
							if(NowShape == "��"||NowShape == "��"||NowShape == "��"||NowShape == "��"||NowShape == "��"||NowShape == "��"||
									NowShape == "��"||NowShape == "��"||NowShape == "��"||NowShape == "��"||NowShape == "��")
								return true;
					}
					else
						if(Shape == "��"||Shape == "��"||Shape == "��"||
	    				Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��")
							return true;
				}
			}
			return false;
		case 'S':
    		if(location+size <= size*size-1)
    		{
    			Shape = Grid[location+size];
				if(isNoExit)
				{
					if(Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��"||
	        			Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��")
						if(NowShape == "��"||NowShape == "��"||NowShape == "��"||NowShape == "��"||NowShape == "��"||NowShape == "��"||
								NowShape == "��"||NowShape == "��"||NowShape == "��"||NowShape == "��"||NowShape == "��")
							return true;
				}
				else
	        		if(Shape == "��"||Shape == "��"||Shape == "��"||
	        			Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��")
	        			return true;
    		}
			return false;
		case 'W':
			if(location%size == size-1||location%size != 0)
			{
	    		if(location-1 >=0)
	    		{
	    			Shape = Grid[location-1];
					if(isNoExit)
					{
			    		if(Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��"||
			    		Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��")
			    			if(NowShape == "��"||NowShape == "��"||NowShape == "��"||NowShape == "��"||NowShape == "��"||NowShape == "��"||
			    					NowShape == "��"||NowShape == "��"||NowShape == "��"||NowShape == "��"||NowShape == "��")
			    				return true;
					}
					else
			    		if(Shape == "��"||Shape == "��"||Shape == "��"||
			    		Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��"||Shape == "��")
			    			return true;
	    		}
			}
			return false;
		}
		return false;
	}
	
	public void DungeonClearSimulation(int loc, char WhereIcome, boolean NoClear)
	{
		//�ѹ� �� ���� PassedRoom�� �����Ͽ� �ٽô� ����ġ�� �ʴ´�.
		//���� ���� �̵��� ���� ã�´�.
		//�̵� ������ ������ ���ٸ�, ��/��/��/�� ������ ���ƺ���.
		//�̶�, ���� ���� �� ������ NowDirection ������ ������ ���´�.
		//���� ������ �켱 ���� �ʴ´�.
		//�� ���� ���� �������� �ֱ� �����̴�.
		//���� ������ �������� ���� �ִٸ� �������� ����.
		//���� ������ ��ġ�� whenIsaved�� ���� �� ����,
		//�� �濡�� ������ ���� whenIChoosed�� �����Ѵ�.
		//���� �̵� �� ���� ���ٸ�,
		//whenIsaved ��ġ�� ���ư���.
		//���� ������ �Ա����� ���� Ŭ���� ������ �����̶�� �Ǹ�.
		//����� ���࿡ ����.
		
		boolean isEmptyN = false;
		boolean isEmptyS = false;
		boolean isEmptyE = false;
		boolean isEmptyW = false;

		isEmptyN=(SearchRoad(loc, 'N'));
		isEmptyS=(SearchRoad(loc, 'S'));
		isEmptyW=(SearchRoad(loc, 'W'));
		isEmptyE=(SearchRoad(loc, 'E'));

		if(isNoExit==false)
		{
			switch(WhereIcome)
			{
				case 'N':
					isEmptyN = false;break;
				case 'E':
					isEmptyE = false;break;
				case 'S':
					isEmptyS = false;break;
				case 'W':
					isEmptyW = false;break;
			}
		}
		else
		{
			switch(WhereIcome)
			{
				case 'N':
					isEmptyN = false;break;
				case 'E':
					isEmptyE = false;break;
				case 'S':
					isEmptyS = false;break;
				case 'W':
					isEmptyW = false;break;
			}
			if(isEmptyN)
				for(int count = 0; count <PassedRoom.size();count++)
					if(PassedRoom.get(count)==loc-size)
					{
						isEmptyN = false;break;
					}
			if(isEmptyE)
				for(int count = 0; count <PassedRoom.size();count++)
					if(PassedRoom.get(count)==loc+1)
					{
						isEmptyE = false;break;
					}
			if(isEmptyS)
				for(int count = 0; count <PassedRoom.size();count++)
					if(PassedRoom.get(count)==loc+size)
					{
						isEmptyS = false;break;
					}
			if(isEmptyW)
				for(int count = 0; count <PassedRoom.size();count++)
					if(PassedRoom.get(count)==loc-1)
					{
						isEmptyW = false;break;
					}
		}
		if(Grid[loc]=="��")
		{
			PerfectDungeon = true;
			return;
		}
		boolean PassAdded = false;
		for(int count2 = 0; count2 <RightPass.size();count2++)
			if(RightPass.get(count2)==loc)
				PassAdded = true;
		if(PassAdded==false)
			RightPass.add(0,loc);
		PassedRoom.add(0, loc);
		if(Grid[loc]=="��"||Grid[loc]=="��"||Grid[loc]=="��")
		{
			whenIChoosed = (char) 'V';
			whenIsaved = loc;
			if(NoClear==false)
				isNoExit = false;
			NoClear = true;
		}
		
		if(isEmptyN==false&&isEmptyE==false&&isEmptyW==false&&isEmptyS==false)
		{
			//��� ���� ������ ��� ������ ���� ��ġ���� �ٽ� ������.
			if(isNoExit==false)
			{
				isNoExit = true;
				if(LoopBreakerUse(0))
					return;
				DungeonClearSimulation(loc, WhereIcome, false);
				return;
			}
			if(LoopBreakerUse(1))
				return;
			for(int count = 0; count < RightPass.size(); count++)
				if(RightPass.get(0)!=whenIsaved)
					RightPass.remove(0);
				else
				{
					for(int count2 = 0; count2 < RightPass.size(); count2++)
					{
						if(RightPass.get(count2)==StartPoint)
							for(int count3 = count2+1; count3 < RightPass.size(); count3++)
								RightPass.remove((count2+1));
					}
					break;
				}
			DungeonClearSimulation(whenIsaved, whenIChoosed, true);
			return;
		}
		
		char counter = 0;
		if(isEmptyN)
			counter++;
		if(isEmptyE)
			counter++;
		if(isEmptyS)
			counter++;
		if(isEmptyW)
			counter++;
		if(counter >=2)//���� 2�� �̻� �� ���� ���
		{
			if(isEmptyN)//������ �׷��� ���ʺ��� �����Ѵ�.
			{
				if(LoopBreakerUse(2))
					return;
				whenIChoosed = 'N';
				DungeonClearSimulation(loc-size, 'S', false);
			}
			else if(isEmptyE)//������ ����
			{
				if(LoopBreakerUse(3))
					return;
				whenIChoosed = 'E';
				DungeonClearSimulation(loc+1, 'W', false);
			}
			else if(isEmptyS)//������ ����
			{
				if(LoopBreakerUse(4))
					return;
				whenIChoosed = 'S';
				DungeonClearSimulation(loc+size, 'N', false);
			}
			else if(isEmptyW)//������ ����
			{
				if(LoopBreakerUse(5))
					return;
				whenIChoosed = 'W';
				DungeonClearSimulation(loc-1, 'E', false);
			}
			return;
		}
		else//���� �ϳ��� ���� ���
		{
			
			if(isEmptyN)//������ �׷��� ���ʺ��� �����Ѵ�.
			{
				if(LoopBreakerUse(6))
					return;
				DungeonClearSimulation(loc-size, 'S', NoClear);
			}
			else if(isEmptyE)//������ ����
			{
				if(LoopBreakerUse(7))
					return;
				DungeonClearSimulation(loc+1, 'W', NoClear);
			}
			else if(isEmptyS)//������ ����
			{
				if(LoopBreakerUse(8))
					return;
				DungeonClearSimulation(loc+size, 'N', NoClear);
			}
			else if(isEmptyW)//������ ����
			{
				if(LoopBreakerUse(9))
					return;
				DungeonClearSimulation(loc-1, 'E', NoClear);
			}
			return;
		}
	}
	
	public boolean DoorCreator()
	{
		for(;;)
		{
			int LastNum = RightPass.get(0);
			List<Integer> Wired = new ArrayList<Integer>();
			for(int count = 0; count < RightPass.size(); count++)
			{
				int Gap = LastNum-RightPass.get(count);
				if(Gap==0||Gap==1||Gap==-1||Gap==size||Gap==-1*size)
					LastNum = RightPass.get(count);
				else
					Wired.add(RightPass.get(count));
			}
			if(Wired.size()==0)
				break;
			else
			{
				int counter = Wired.size()-1;
				for(int count = 0; counter >= 0; count++)
				{
					if(RightPass.get(count)==Wired.get(counter))
					{
						RightPass.remove(count);
						counter--;
						count = 0;
					}
				}
			}
		}
		/*����� ����
		���踦 ���� �� �ִ� �޿� �� ���� ���� �� ����
		RightPass ������ ������ �� ��ġ���� �ҷ��´�.
		������ �°� �̵� �ù��� ���� 1Way ��Ʈ�� �����.
		��Ʈ ��θ� �����̸鼭 �߰� �߰� ������� ����� �ش�.
		����� ������ RightPass ������ ������ ���,
		�� �������ʹ� ��� ���������� ��ü�Ѵ�.
		
		Ȯ���� ���� ��(������)���� ��ü�Ѵ�.
		����� ���ڶ� ������ 1�� �̻� ������ �ȵȴ�.
		(1�� ���´ٸ� ������ ����. �ȳ����� �����浵 ������)
		
		������ - �� ������ ������ �Ʒ� �Ʒ� �κ��� ���彺�� ������� ����.
		�Ϲ� �� - ���� ������ �κп� ǥ���� ��ġ. ���� �̸� �� ���� UTC ����
		 */
		boolean NotFoundKey = true;
		char NextDirection = 'V';
		int TotalKeyRoom = 0;
		int key = 0;
		for(int count = RightPass.size()-1; count >= 0; count--)
		{
			String Shape = Grid[RightPass.get(count)];
			String KShape = KeyGrid[RightPass.get(count)];
			if(Shape.compareTo("��")==0||Shape.compareTo("��")==0)
				key=key+1;
			else if(Shape.compareTo("��")!=0&&Shape.compareTo("��")!=0
				&&Shape.compareTo("��")!=0)
			{
				if(count-1 >= 0)
				{
					if(RightPass.get(count) - RightPass.get(count-1) == -1)
						NextDirection = 'E';
					else if(RightPass.get(count) - RightPass.get(count-1) == 1)
						NextDirection = 'W';
					else if(RightPass.get(count) - RightPass.get(count-1) == size)
						NextDirection = 'N';
					else if(RightPass.get(count) - RightPass.get(count-1) == -1*size)
						NextDirection = 'S';
					else
						NextDirection = 'V';
				}
				else
					break;
				if(key>0)
				{
					NotFoundKey = false;
					//���� ���� �������� ������ �ʰ� ���� ���̾��ٸ�
					//���� ��츦 �Ǵ��� ����, ����� ����� �� ���� ���� ���� ��
					//���� �� ���� ������ ������� ����� ��.
					switch(NextDirection)
					{
					case 'N':
						KeyGrid[RightPass.get(count)] = "��";break;
					case 'E':
						KeyGrid[RightPass.get(count)] = "��";break;
					case 'S':
						KeyGrid[RightPass.get(count)] = "��";break;
					case 'W':
						KeyGrid[RightPass.get(count)] = "��";break;
					}
					TotalKeyRoom++;
					key--;
				}
				else if(NotFoundKey==false&&key==0)
				{
					if(Maze_Level >= Randomnum(0, 20))
					{
						if(count-1 >= 0)
						{
							boolean CanCreateThat = false;
							int loc = RightPass.get(count);
							if(KShape!=null)
							{
								Shape = Grid[RightPass.get(count-1)];
								if(Shape.compareTo("��")!=0&&Shape.compareTo("��")!=0
									&&Shape.compareTo("��")!=0&&KShape.compareTo("��")!=0
									&&KShape.compareTo("��")!=0&&KShape.compareTo("��")!=0
									&&KShape.compareTo("��")!=0)
									CanCreateThat=true;
							}
							else
								CanCreateThat=true;
							if(CanCreateThat)
							{
								switch(NextDirection)
								{
								case 'N':
									KeyGrid[loc] = "��";break;
								case 'E':
									KeyGrid[loc] = "��";break;
								case 'S':
									KeyGrid[loc] = "��";break;
								case 'W':
									KeyGrid[loc] = "��";break;
								}
							}
						}
					}
				}
			}
		}
		if(!(TotalKeyRoom == TotalRoom-1||TotalKeyRoom == TotalRoom))
			for(int count = RightPass.size()-1; count >= 0; count--)
			{
				String Shape = KeyGrid[RightPass.get(count)];
				if(Shape!=null)
					if(Shape.compareTo("��")==0&&Shape.compareTo("��")==0
					&&Shape.compareTo("��")==0&&Shape.compareTo("��")==0)
					{
						TotalKeyRoom--;
						KeyGrid[RightPass.get(count)]=null;
						if(TotalKeyRoom == TotalRoom-1||TotalKeyRoom == TotalRoom)
							break;
					}
			}
		if(!(TotalKeyRoom == TotalRoom-1||TotalKeyRoom==TotalRoom))
			return false;
		if(TotalKeyRoom==TotalRoom-1)//�������� ����� ����
			KeyGrid[BossRoom] = "��";
		else if(TotalKeyRoom==TotalRoom)//�������� ���� ��
			KeyGrid[BossRoom] = "��";
		else
			return false;
		return true;
	}
	
	public boolean LoopBreakerUse(int index)
	{
		for(int count = 0; count <LoopBreaker.length;count++)
			if(count!=index)
				LoopBreaker[count] = 0;
		LoopBreaker[index]++;
		if(LoopBreaker[index] >= 55)
			return true;
		else
			return false;
	}
	
	
	public String createMap()
	{
		String seed = size+"_"+BossRoom+"_"+StartPoint+"_"+TotalRoom;
		File filename = new File("plugins/GoldBigDragonRPG/Dungeon/Seed/" + seed + ".txt");
	    try
	    {
	      if (!filename.exists())
	      {
	  	    File F1 = new File("plugins/GoldBigDragonRPG");
		    File F2 = new File("plugins/GoldBigDragonRPG/Dungeon");
		    File F3 = new File("plugins/GoldBigDragonRPG/Dungeon/Seed");
		    F1.mkdir();
		    F2.mkdir();
		    F3.mkdir();
	        filename.createNewFile();
	      }
	      BufferedWriter Write = new BufferedWriter(new FileWriter(filename));
	      String s = "";
		  int counter = 0;
		  for(int count=0;count<(size*size);count++)
		  {
			  counter=counter+1;
			  if(Grid[count]==null)
			  {
				  Grid[count]= "��";
			  }
			  s = s+Grid[count];
			  if(counter==size)
			  {
				  s = s+"\r\n";
				  counter=0;
			  }
		  }		  
		  Write.append(s);
	      Write.flush();
	      Write.close();
	    }
	    catch (IOException localIOException){}

		filename = new File("plugins/GoldBigDragonRPG/Dungeon/Seed/" + seed + "_KeyGrid.txt");
	    try
	    {
	      if (!filename.exists())
	      {
	  	    File F1 = new File("plugins/GoldBigDragonRPG");
		    File F2 = new File("plugins/GoldBigDragonRPG/Dungeon");
		    File F3 = new File("plugins/GoldBigDragonRPG/Dungeon/Seed");
		    F1.mkdir();
		    F2.mkdir();
		    F3.mkdir();
	        filename.createNewFile();
	      }
	      BufferedWriter Write = new BufferedWriter(new FileWriter(filename));
	      String s = "";
		  int counter = 0;
		  for(int count=0;count<(size*size);count++)
		  {
			  counter=counter+1;
			  if(KeyGrid[count]==null)
				  KeyGrid[count]= "��";
			  s = s+KeyGrid[count];
			  if(counter==size)
			  {
				  s = s+"\r\n";
				  counter=0;
			  }
		  }		  
		  Write.append(s);
	      Write.flush();
	      Write.close();
	    }
	    catch (IOException localIOException){}
	    return seed;
	}
}
