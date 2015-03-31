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
		Stack<Integer> callStack = new Stack<Integer>();
		Frame f = new Frame();
		FileDialog fd = new FileDialog(f,"Open",FileDialog.LOAD);
		ArrayList<Instruction> mem = new ArrayList<Instruction>();
		DecodeOp dp = new DecodeOp();
		String inst;
		//***********************//
		int startLn=0;
		int currentLn=0;
		int tempLn=0;
		boolean breakPlag=false;
		Label label = new Label();
		Instruction curInstr;
		//***********************//
		
		fd.setFile(".uco");
		fd.setVisible(true);
		
		File file = new File(fd.getDirectory()+fd.getFile());
		try {
			Scanner fsc = new Scanner(file);
			while(fsc.hasNextLine()){										//��� ��ó�� �κ�
				inst = fsc.nextLine();
				String[] result =inst.split("\\s+");
				Instruction p = new Instruction();
				System.out.println(result[0]);
				System.out.println(result[1]);
				System.out.println(result[2]);
				System.out.println(result[3]);
				System.out.println(result[4]);
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
					// is opcode bgn? then set startLine;
					if(p.getOpcode()=="bgn"){ 
						startLn = currentLn;
						System.out.println(startLn);
					}
					// is there label? add to label datastructure
					if(!p.getLabel().equals("")) label.addLabel(p.getLabel(), currentLn);
				}
				// increase current line
				currentLn++;
				mem.add(p);
			}
			//read ArrayList from startLine
			// while(end==true)
			//		switch(arrayList[startLine].getOp())
			/*
			currentLn = startLn;
			while(true){
				curInstr = mem.get(currentLn);
				System.out.println(curInstr.getLabel()+"\t"+curInstr.getOpcode()+" "+curInstr.getP1()+" "+curInstr.getP2()+" "+curInstr.getP3());
				switch(curInstr.getOpcode()){
				case "call":
					tempLn = label.findLabel(curInstr.getP1());
					if(tempLn<0){
						System.out.println("no label found error");
					}
					else{
						callStack.push(currentLn+1);
						currentLn = tempLn;
					}
					break;
				case "ujp":
				case "tjp":
				case "fjp":
					tempLn = label.findLabel(curInstr.getP1());
					if(tempLn<0){
						System.out.println("no label found error");
					}
					else	currentLn = tempLn;
					break;
				case "end":
					if(callStack.isEmpty()) breakPlag = true;
					else currentLn = callStack.pop();
				}
				if(breakPlag) break;
				currentLn++;
			}
			*/ 
			fsc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}