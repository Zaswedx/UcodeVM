import java.awt.FileDialog;
import java.awt.Frame;
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
		Frame f = new Frame();
		FileDialog fd = new FileDialog(f,"Open",FileDialog.LOAD);
		ArrayList<Instruction> mem = new ArrayList<Instruction>();
		DecodeOp dp = new DecodeOp();
		String inst;
		
		fd.setFile(".uco");
		fd.setVisible(true);
		
		File file = new File(fd.getDirectory()+fd.getFile());
		try {
			Scanner fsc = new Scanner(file);
			while(fsc.hasNextLine()){
				inst = fsc.nextLine();
				String[] result =inst.split("\\s+");
				Instruction p = new Instruction();

				if (result.length>1){
					p.setLabel(result[0]);
					p.setOpcode(result[1]);
					switch(dp.getOrderLength(result[1])){
					case 3 :
						p.setP3(result[4]);
					case 2 :
						p.setP2(result[3]);
					case 1 :
						p.setP1(result[2]);
						break;
					default :
						System.out.println("Error : 유효하지 않은 명령");	
					}
				}
				mem.add(p);
				System.out.println(p.getLabel()+" "+p.getOpcode()+" "+p.getP1()+" "+p.getP2()+" "+p.getP3());
			}
			
			fsc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}