package com.GoldBigDragon.EmptyHand;


//���� MainŬ������ ��θ� ��Ÿ���� �ֽ��ϴ�.

//���� MainŬ������ com.GoldBigDragon.EmptyHand��� ��Ű�� �ӿ� �ֱ���!




import java.util.logging.Logger;

import org.bukkit.Bukkit;

import org.bukkit.Material;

import org.bukkit.command.Command;

import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;

import org.bukkit.event.Listener;

import org.bukkit.inventory.ItemStack;

import org.bukkit.plugin.java.JavaPlugin;


//import�� �Ǿ� �ִ� �κ��� C���� #include�� ����� ����� �մϴ�. �Լ��� ȣ���ϰ� �ִٰ� ���ø� �˴ϴ�.

//�丮�� �����ڸ�, �丮�� �ϱ� ��, �丮�� �ʿ��� ���� �������� ������ ����������!







public class Main extends JavaPlugin implements Listener

//MainŬ������ �����Դϴ�! extends JavaPlugin �� ħ���� ���ؼ�

//�� Ŭ������ JavaPlugin���� ��� ������ ��� �ް� �Ǿ����ϴ�!

{




 public void onEnable()

//onEnable()�� ���� ���� �÷������� ���� �Ǿ��� �� ������ ������ ���� ���Դϴ�.




    {

     getServer().getPluginManager().registerEvents(this, this);

     //���� �̺�Ʈ�� ������ �� ����Ǵ� �� onEnable�̶�� �̺�Ʈ�� ��� ��ŵ�ϴ�.


     getLogger().info("��������");

     getLogger().info("�����������������ᡡ��������������"); 

     getLogger().info("���������������ᡡ����������������");

     getLogger().info("�������������ᡡ������������������");

     getLogger().info("�����������ᡡ��������������������");

     getLogger().info("���������ᡡ�������������ᡡ������");

     getLogger().info("�����������ᡡ���������ᡡ�ᡡ����");

     getLogger().info("�����ᡡ�����ᡡ�����ᡡ�����ᡡ��");

     getLogger().info("���ᡡ�ᡡ�����ᡡ�ᡡ���������ᡡ");

     getLogger().info("�ᡡ�����ᡡ�����ᡡ��������������");

     getLogger().info("�������ᡡ�����ᡡ�ᡡ������������");

     getLogger().info("�����ᡡ�����ᡡ�����ᡡ����������");

     getLogger().info("�������ᡡ�ᡡ�����ᡡ�ᡡ��������");

     getLogger().info("���������ᡡ�����ᡡ�����ᡡ������");

     getLogger().info("���������������ᡡ�����ᡡ��������");

     getLogger().info("�������������ᡡ�����ᡡ����������");

     getLogger().info("�������������������ᡡ������������");

     getLogger().info("�����������������ᡡ��������������");

     getLogger().info("��������EmptyHand");

     getLogger().info("http://cafe.naver.com/goldbigdragon");

     getLogger().info("��������");

     //getLogger().info("�Ҹ�") �� ���� ��Ŷ â�� �߰� �� ���� ���� ���Դϴ�.

     //���� 'onEnalbe'��, �÷������� ���� �� �� �� �׷� �ȿ� �����Ƿ�, ���� ����� ������ ���

     //�ش� �÷������� Ȱ��ȭ �� �� ��Ŷ�� �޽����� �߰� �� ���Դϴ�.

    }

 public boolean onCommand(CommandSender talker, Command command, String string, String[] args)


 //����� �������� �������� onCommand�� �̸��� �޼ҵ带 �����ϰ�, �� �޼ҵ带 ���� ��ų �� �ʿ��� �Ķ��Ÿ��

 //CommandSender ��, ��ɾ ģ ����� talker��� ���� �ӿ� ���� ��Ű��,

 //Command ��, ģ ��ɾ�� command��� �����ӿ� ���� ��ŵ�ϴ�.

 //String, ��(label)�� ���� ��Ű�� ���� string �� ���� �� ���ҽ��ϴ�.

 //String[], �ڽ��� ģ ��ɾ��� ����(��� ǥ���� ���������)�� �����ϱ� ���� args��� ������ ���� ��ŵ�ϴ�.

 //Ʈ���ſ��� <cmdarg:>�÷��̽� Ȧ���� ��� �� ���̴ٸ� ���ذ� ����

 //�ǽǰǵ���, ��ɾ�� ���⸦ �Ͽ� ���� ��° ���� ���ڸ� üũ�մϴ�.

 //���� ��� /give goldbigdragon 156 5 ��� ��ɾ��

 //goldbigdragon�� args�� 0, 156�� args�� 1, 5�� args�� 3�� �˴ϴ�.

    {




   if(talker instanceof Player)

  //���� ��ɾ ģ ����� ��ƼƼ �÷��̾��� ���. ��, �ֿܼ��� ��ɾ� �ߵ��� ���� �ʽ��ϴ�!

   {

    Player player = (Player) talker;

                 //��ɾ ģ ����� ��ƼƼ Player ���� player��� ������ ���� ��ŵ�ϴ�.

    if(string.equalsIgnoreCase("emptyh"))

                 //���� ģ ��ɾ "�ջ�"�̾��ٸ�


    {

     ItemStack WillStack = new ItemStack(Material.AIR, 1);

                          //WillStack�̶� ������ ItemStack �Լ��� AIR(����) 1���� ���� ���� �����մϴ�.

                          //�� ������ ���� �Լ��� ���� 1���� ���� �־� ��������� ����

                          //�ƹ��͵� ���� ���¸� WillStack�̶� ������ ���� ��Ű�� �Ǵ� ��������.

     player.setItemInHand(WillStack);

                          //�׸���� ��ɾ ģ �÷��̾��� �տ� WillStack�� ���� ��ŵ�ϴ�.

    }

   }

 return true;

        //���� boolean���� ���� �߾����� ��°��� �������� ���;� �մϴ�.

  }

    

}


