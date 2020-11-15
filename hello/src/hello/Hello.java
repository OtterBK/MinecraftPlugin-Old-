package hello;

public class Hello {

	public static void main(String[] args) {
		System.out.println("Hello Java!!");
		
		test:
		System.out.println("test 메소드를 테스트 해보겠습니다.");
		for( int i=0; i<=15; i++)
		{
			System.out.println("현재 숫자 : "+i);
		}
		
		System.out.println("i 변수 초기화 ");	


	}

static public void test () {
	System.out.println("test 메소드를 테스트 해보겠습니다.");
	for( int i=0; i<15; i++)
	{
		System.out.println("현재 숫자 : "+i);
	}
	
	System.out.println("i 변수 초기화 ");	
 }
}
