import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class UCODEI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("rawtypes")
		Stack stack = new Stack();
		Scanner sc = new Scanner(System.in);
		ArrayList<Instruction> mem = new ArrayList<Instruction>();
		String inst;
		
		File file = new File(sc.nextLine());
		try {
			Scanner fsc = new Scanner(file);
			while(fsc.hasNextLine()){
				inst = fsc.nextLine();
				System.out.println(inst);
			}
			fsc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sc.close();
	}
}