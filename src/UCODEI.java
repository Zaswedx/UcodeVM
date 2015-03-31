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
		
		//***********************//>
		int currentLn=0;					// num of line executing now
		int tempLn=0;						// num of line for confirming range
		boolean breakPlag=false;			// break plag for while loop 
		Label label = new Label();		// data struct for storing label
		Instruction curInstr;			// Instruction object for 
		Stack<Integer> callStack = new Stack<Integer>();		// when call operation occur, push num of next line(where it should be when call is over)  
		//***********************//
		
		fd.setFile(".uco");
		fd.setVisible(true);
		
		File file = new File(fd.getDirectory()+fd.getFile());
		try {
			Scanner fsc = new Scanner(file);
			int itr = -1,cnt = 0,floor = 0;
			while(fsc.hasNextLine()){										//��� ��ó��
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
						System.out.println("Error : ��ȿ���� ���� ���");	
					}
					//***********************//>
					// is there label? add to label(in 'labelBox' of 'Label' class)
					if(!p.getLabel().equals("")) label.addLabel(p.getLabel(), currentLn);
				}
				currentLn++;		// increase current line
				//***********************//
				mem.add(p);
				cnt++;
			}
			//***********************//>
			currentLn = itr;		// I can't get 'bgn' point so I use yours 
									// answer : all 'proc' have 'end', also end's nextline is 'proc' or 'bgn'.
									// so we find 'bgn' point through use 3 varible(cnt, floor, itr). itr = bgn's line number.
			//***********************// it's jaehun's idea~! 
			/*
			floor = 0;
			while(true){									//��� ó��
//				System.out.println(mem.get(i).getLabel()+" "+mem.get(i).getOpcode()+" "+mem.get(i).getP1()+" "+mem.get(i).getP2()+" "+mem.get(i).getP3());
//				dp.setStack(stack, mem.get(i).getOpcode());
				System.out.println(mem.get(itr).getOpcode());
				if (mem.get(itr).getOpcode().equals("end")&&floor == 0) break;
				itr++;
				if (itr>=cnt-1) itr = 0;
			}
			*/
			//***********************//>
			while(true){				// while loop for executing operations in order
				curInstr = mem.get(currentLn);		// get Instruction object of current line
				// print out current line
				System.out.println(curInstr.getLabel()+"\t"+curInstr.getOpcode()+" "+curInstr.getP1()+" "+curInstr.getP2()+" "+curInstr.getP3());
	
				switch(curInstr.getOpcode()){
				case "call":
					// we didn't implement call operation. it's just exception for later implementation
					if(curInstr.getP1().equals("write")){ 
						currentLn++; 
						break;
					}
					// find label's line number
					tempLn = label.findLabel(curInstr.getP1());
					// if there is no label it returns -1
					if(tempLn<0){
						System.out.println("no label found error");
					}
					else{
						callStack.push(currentLn);	// push current line number 
						currentLn = tempLn;			// and jump!!
					}
					break;
				case "ujp":
				case "tjp":
				case "fjp":
					// find label's line number
					tempLn = label.findLabel(curInstr.getP1());
					// if there is no label it returns -1
					if(tempLn<0){
						System.out.println("no label found error");
					}
					else currentLn = tempLn;		// jump!!!
					break;
				case "end":
					if(callStack.isEmpty()) breakPlag = true;		// if there is no point to return, change plag to true for breaking loop
					else currentLn = callStack.pop();				// if there are some point to return, jump!! 
				default:				// if no jump required, then read next line
					currentLn++;										
					break;
				}
				if(breakPlag) break;
			}
			//***********************//
			fsc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}