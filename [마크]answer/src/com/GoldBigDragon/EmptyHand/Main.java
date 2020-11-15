package com.GoldBigDragon.EmptyHand;


//현재 Main클래스의 경로를 나타내고 있습니다.

//현재 Main클래스는 com.GoldBigDragon.EmptyHand라는 패키지 속에 있군요!




import java.util.logging.Logger;

import org.bukkit.Bukkit;

import org.bukkit.Material;

import org.bukkit.command.Command;

import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;

import org.bukkit.event.Listener;

import org.bukkit.inventory.ItemStack;

import org.bukkit.plugin.java.JavaPlugin;


//import로 되어 있는 부분은 C언어에서 #include와 비슷한 기능을 합니다. 함수를 호출하고 있다고 보시면 됩니다.

//요리로 따지자면, 요리를 하기 전, 요리에 필요한 재료와 도구들을 꺼내는 동작이지요!







public class Main extends JavaPlugin implements Listener

//Main클래스의 시작입니다! extends JavaPlugin 을 침으로 인해서

//이 클래스는 JavaPlugin속의 모든 내용을 상속 받게 되었습니다!

{




 public void onEnable()

//onEnable()은 지금 만든 플러그인이 실행 되었을 때 실행할 내용을 적는 곳입니다.




    {

     getServer().getPluginManager().registerEvents(this, this);

     //서버 이벤트에 실행할 때 실행되는 이 onEnable이라는 이벤트를 등록 시킵니다.


     getLogger().info("　　　　");

     getLogger().info("　　　　　　　　■　　　　　　　　"); 

     getLogger().info("　　　　　　　■　　　　　　　　　");

     getLogger().info("　　　　　　■　　　　　　　　　　");

     getLogger().info("　　　　　■　　　　　　　　　　　");

     getLogger().info("　　　　■　　　　　　　■　　　　");

     getLogger().info("　　　　　■　　　　　■　■　　　");

     getLogger().info("　　■　　　■　　　■　　　■　　");

     getLogger().info("　■　■　　　■　■　　　　　■　");

     getLogger().info("■　　　■　　　■　　　　　　　■");

     getLogger().info("　　　■　　　■　■　　　　　　　");

     getLogger().info("　　■　　　■　　　■　　　　　　");

     getLogger().info("　　　■　■　　　■　■　　　　　");

     getLogger().info("　　　　■　　　■　　　■　　　　");

     getLogger().info("　　　　　　　■　　　■　　　　　");

     getLogger().info("　　　　　　■　　　■　　　　　　");

     getLogger().info("　　　　　　　　　■　　　　　　　");

     getLogger().info("　　　　　　　　■　　　　　　　　");

     getLogger().info("　　　　EmptyHand");

     getLogger().info("http://cafe.naver.com/goldbigdragon");

     getLogger().info("　　　　");

     //getLogger().info("할말") 은 서버 버킷 창에 뜨게 할 말을 적는 것입니다.

     //현재 'onEnalbe'즉, 플러그인이 실행 될 때 의 그룹 안에 썼으므로, 여기 적어둔 내용은 모두

     //해당 플러그인이 활성화 될 때 버킷에 메시지가 뜨게 될 것입니다.

    }

 public boolean onCommand(CommandSender talker, Command command, String string, String[] args)


 //결과로 이진수가 나오도록 onCommand란 이름의 메소드를 생성하고, 이 메소드를 실행 시킬 때 필요한 파라미타는

 //CommandSender 즉, 명령어를 친 사람을 talker라는 변수 속에 저장 시키고,

 //Command 즉, 친 명령어는 command라는 변수속에 저장 시킵니다.

 //String, 라벨(label)을 저장 시키는 변수 string 을 생성 해 보았습니다.

 //String[], 자신이 친 명령어의 갯수(라는 표현이 어색하지만)를 저장하기 위해 args라는 변수를 생성 시킵니다.

 //트리거에서 <cmdarg:>플레이스 홀더를 사용 해 보셨다면 이해가 빨리

 //되실건데요, 명령어에서 띄어쓰기를 하여 다음 번째 오는 글자를 체크합니다.

 //예를 들면 /give goldbigdragon 156 5 라는 명령어에서

 //goldbigdragon은 args가 0, 156은 args가 1, 5는 args가 3이 됩니다.

    {




   if(talker instanceof Player)

  //만약 명령어를 친 사람이 엔티티 플레이어일 경우. 즉, 콘솔에서 명령어 발동은 되지 않습니다!

   {

    Player player = (Player) talker;

                 //명령어를 친 사람을 엔티티 Player 형의 player라는 변수에 저장 시킵니다.

    if(string.equalsIgnoreCase("emptyh"))

                 //만약 친 명령어가 "손삭"이었다면


    {

     ItemStack WillStack = new ItemStack(Material.AIR, 1);

                          //WillStack이란 변수에 ItemStack 함수에 AIR(공기) 1개를 넣은 값을 저장합니다.

                          //즉 아이템 스택 함수에 공기 1개를 집어 넣어 결과값으로 나온

                          //아무것도 없는 상태를 WillStack이란 변수에 저장 시키게 되는 것이지요.

     player.setItemInHand(WillStack);

                          //그리고는 명령어를 친 플레이어의 손에 WillStack을 설정 시킵니다.

    }

   }

 return true;

        //위에 boolean으로 설정 했었으니 출력값은 이진수로 나와야 합니다.

  }

    

}


