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
		
		Stack<String> stack = new Stack<String>();
		Frame f = new Frame();
		FileDialog fd = new FileDialog(f,"Open",FileDialog.LOAD);
		ArrayList<Instruction> mem = new ArrayList<Instruction>();
		ArrayList<Variable> vmem = new ArrayList<Variable>();
		DecodeOp dp = new DecodeOp();
		String inst;
		
		fd.setFile(".uco");
		fd.setVisible(true);
		
		File file = new File(fd.getDirectory()+fd.getFile());
		try {
			Scanner fsc = new Scanner(file);
			int itr = -1,cnt = 0,floor = 0;
			while(fsc.hasNextLine()){										//명령 전처리
				inst = fsc.nextLine();
				String[] result =inst.split("\\s+");
				Instruction p = new Instruction();

				if (result.length>1){
					if (result[1].equals("proc")) floor++;
					else if (result[1].equals("end")) floor--;
					else if (floor==0 && itr == -1) itr = cnt;
					p.setLabel(result[0]);
					p.setOpcode(result[1]);
					switch(dp.getOrderLength(result[1])){
					case 3 :
						p.setP3(result[4]);
					case 2 :
						p.setP2(result[3]);
					case 1 :
						p.setP1(result[2]);
					case 0 :
						break;
					default :
						System.out.println("Error : 유효하지 않은 명령");	
					}
				}
				mem.add(p);
				cnt++;
			}
			floor = 0;
			while(true){									//명령 처리
//				System.out.println(mem.get(i).getLabel()+" "+mem.get(i).getOpcode()+" "+mem.get(i).getP1()+" "+mem.get(i).getP2()+" "+mem.get(i).getP3());
//				dp.setStack(stack, mem.get(i).getOpcode());
				System.out.println(mem.get(itr).getOpcode());
				if (mem.get(itr).getOpcode().equals("end")&&floor == 0) break;
				itr++;
				if (itr>=cnt-1) itr = 0;
			}
			fsc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}