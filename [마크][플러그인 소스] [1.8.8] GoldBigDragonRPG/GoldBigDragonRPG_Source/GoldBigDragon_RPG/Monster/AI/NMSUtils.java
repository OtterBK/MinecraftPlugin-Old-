package GoldBigDragon_RPG.Monster.AI;
//https://github.com/sgtcaze/Tutorial/tree/master/Season%203/EP27
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityTypes;

public class NMSUtils
{
	public void registerEntity(String name, int id, Class<? extends EntityInsentient> nmsClass, Class<? extends EntityInsentient> customClass)
	{
        try
        {
            /*
            * ����, ��ƼƼ Ÿ�� Ŭ������ �ִ� ��� �ʵ��(fields)��
            * HashMap����Ʈ�� �����Ѵ�.
            * ���ڴ� reflection�� ����߱� ������, ���߿� ����ũ����Ʈ�� �ʵ� �̸��� �ٲ㵵 ������ ����.
            * �ʵ鿡 ����Ʈ���� ��������� ��, �츮���� ���߿� �ſ� ���� ������ �� �ְ� �ȴ�.
            */
            List<Map<?, ?>> dataMaps = new ArrayList<Map<?, ?>>();
            for(Field f : EntityTypes.class.getDeclaredFields())
            {
                if(f.getType().getSimpleName().equals(Map.class.getSimpleName()))
                {
                    f.setAccessible(true);
                    dataMaps.add((Map<?, ?>) f.get(null));
                }
            }
 
            /*
            * ����ũ����Ʈ�� �� �ʵ��� üũ�� ��, �̹� ��ϵ� id�� �ִٸ�,
            * ������ �����͸� ���� ������, �츮�� ����� ���� ¤�� �ְ� �ȴ�.
            */
            
            dataMaps.get(0).remove(name);
            dataMaps.get(2).remove(id);
            
            /*
            * ���� �츮���� �츮�� ���� Ŀ���� ��ƼƼ�� ��� ��ų ���̴�.
            */
            Method method = EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, int.class);
            method.setAccessible(true);
            method.invoke(null, customClass, name, id);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
    public static Object getPrivateField(String fieldName, Class clazz, Object object) {
        Field field;
        Object o = null;

        try
        {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            o = field.get(object);
        }
        catch(NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        catch(IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return o;
    }
}