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
		
		//Stack<String> stack = new Stack<String>();
		Frame f = new Frame();
		FileDialog fd = new FileDialog(f,"Open",FileDialog.LOAD);
		ArrayList<Instruction> mem = new ArrayList<Instruction>();
		//ArrayList<Variable> vmem = new ArrayList<Variable>();
		DecodeOp dp = new DecodeOp();
		String inst;
		
		int currentLn=0;					// num of line executing now
		int tempLn=0;						// num of line for confirming range
		boolean breakPlag=false;			// break plag for while loop 
		Label label = new Label();		// data struct for storing label
		Instruction curInstr;			// Instruction object for
		//***********************//>		
		Stack<int[]> callStack = new Stack<int[]>();		// integer array Stack for call operation
		Stack<Integer> exStack = new Stack<Integer>();	// integer Stack for execution
		int pcNsp[] = {0,0};									// integer array for pc, sp
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
						System.out.println("Error : it is error msg ");	
					}
					// is there label? add to label(in 'labelBox' of 'Label' class)
					if(!p.getLabel().equals("")) label.addLabel(p.getLabel(), currentLn);
				}
				currentLn++;		// increase current line
				mem.add(p);
				cnt++;
			}
			currentLn = itr;		// thanks for your explanation jae-hun
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
			while(true){				// while loop for executing operations in order
				curInstr = mem.get(currentLn);		// get Instruction object of current line
				// print out current line
				System.out.println(curInstr.getLabel()+"\t"+curInstr.getOpcode()+" "+curInstr.getP1()+" "+curInstr.getP2()+" "+curInstr.getP3());
	
				switch(curInstr.getOpcode()){
				//***********************//>
				case "ldp":		// when ldp occurs,
					pcNsp[1] = exStack.size();	// check the stack point 
					currentLn++;
					break;
				//***********************//
				case "call":
					//***********************//>
					pcNsp[0] = currentLn;
					if(curInstr.getP1().equals("write")){ 		// implement write operation
						System.out.println(exStack.pop());		// by println function
						currentLn++; 
						break;
					}
					//***********************//
					// find label's line number
					tempLn = label.findLabel(curInstr.getP1());
					// if there is no label it returns -1
					if(tempLn<0){
						System.out.println("no label found error");
					}
					else{
						//***********************//>
						callStack.push(pcNsp);	// push current line number and stack point
						//***********************//
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
					//***********************//>
					else{
						pcNsp = callStack.pop();			// pop call stack and get int array
						currentLn = pcNsp[0];			// if there are some point to return, jump!! 
						while(exStack.size()!=pcNsp[1]){ exStack.pop(); }	// pop all of elements for post area
					}
					//***********************//
				default:				// if no jump required, then read next line
					//***********************//>
					// I changed your method name. I'm sorry but I think this name is more appropriate
					dp.execute(exStack, curInstr.getOpcode());			
					//***********************//
					currentLn++;										
					break;
				}
				if(breakPlag) break;
			}
			fsc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}