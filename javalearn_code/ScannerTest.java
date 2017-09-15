import java.util.*;
class ScannerTest {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		sc.useDelimiter("\n");
		while(sc.hasNextLong()){
			System.out.println("键盘输出是："+sc.nextLong());
		}
	}
}


