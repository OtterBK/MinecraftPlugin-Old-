package hello;

public class Hello {

	public static void main(String[] args) {
		System.out.println("Hello Java!!");
		
		test:
		System.out.println("test �޼ҵ带 �׽�Ʈ �غ��ڽ��ϴ�.");
		for( int i=0; i<=15; i++)
		{
			System.out.println("���� ���� : "+i);
		}
		
		System.out.println("i ���� �ʱ�ȭ ");	


	}

static public void test () {
	System.out.println("test �޼ҵ带 �׽�Ʈ �غ��ڽ��ϴ�.");
	for( int i=0; i<15; i++)
	{
		System.out.println("���� ���� : "+i);
	}
	
	System.out.println("i ���� �ʱ�ȭ ");	
 }
}
