import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class UCODEI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// File I/O
		Frame f = new Frame();
		FileDialog fd = new FileDialog(f,"Open",FileDialog.LOAD);
		
		DecodeOp dp = new DecodeOp();	// object, executing stack operation
		Label label = new Label();		// object, data structure for storing label
		
		int currentLn=0,tempLn=0,ldpLine = 0;			// 'currentLn' is num of line executing now
															// 'tempLn' is num of line for confirming range
															// 'ldpLine' is num of parameters
		
		boolean breakPlag=false,			// break plag for while loop
				 ldpFlag = false;			// is it pushing parameter into stack now?
		
		Instruction curInstr;			// Instruction object for
		
		ArrayList<Instruction> IstrArr = new ArrayList<Instruction>();		// get lines from .uco file and save here !!
		HashMap<String, String> memory = new HashMap<String, String>();	// Memory data structure, we can get or set data by 'key'
		
		Stack<int[]> callStack = new Stack<int[]>();		// integer array Stack for call operation
		Stack<String> exStack = new Stack<String>();		// String Stack for execution
		Stack<String> paramStack = new Stack<String>();	// String Stack for 
		
		int pcNsp[]= new int[2];								// integer array for pc, sp
		pcNsp[0]= 0;
		pcNsp[1]= 1;
		
		fd.setFile(".uco");
		fd.setVisible(true);
		
		File file = new File(fd.getDirectory()+fd.getFile());
		try {
			Scanner fsc = new Scanner(file);
			int itr = -1;			// 'itr' represents where the begin point is  
			int floor = 0;		// 'floor' represents current lexical level
			while(fsc.hasNextLine()){
				String[] result =fsc.nextLine().split("\\s+"); 		// split command elements
				Instruction p = new Instruction();						

				if (result.length>1){									// if this line isn't blank line
					if (result[1].equals("proc")) floor++;				// if opcode is proc, it's lexical level is increase 
					else if (result[1].equals("end")) floor--;		// if opcode is end, it's lexical level is decrease
					else if (floor==0 && itr == -1) itr = currentLn;	// if opcode isn't proc or end and lexical level is 0 then here is bgn point
					p.setLabel(result[0]);								
					p.setOpcode(result[1]);
					switch(dp.getOrderLength(result[1])){				// in DecodeOp returns num operand for each opcode by method
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
					// is there label? add to label object (in 'labelBox' of 'Label' class)
					if(!p.getLabel().equals("")) label.addLabel(p.getLabel(), currentLn);
				}
				currentLn++;		// increase current line
				IstrArr.add(p);	// when ldp occurs,
			}
			
			currentLn = itr;			// get bgn line number 
			floor = 0;					// start from lexical level 0

			while(true){				// while loop for executing operations in order
				curInstr = IstrArr.get(currentLn);		// get Instruction object of current line
				
				switch(curInstr.getOpcode()){
				//********MEM*********//>
				case "ldp":		
					pcNsp[1] = exStack.size();	// check the stack point
					ldpFlag = true;				
					ldpLine = 0;					 
					currentLn++;
					break;
				case "ldc":
					exStack.push(curInstr.getP1());
					if (ldpFlag) ldpLine++;		
					currentLn++;
					break;
				case "lod":
					if (curInstr.getP1().equals("1")){							// if this block is local
						exStack.push(memory.get("0x"+curInstr.getP2()));		// address is "0x" + offset number			
					}else{															// if this block is global
						exStack.push(memory.get(String.valueOf(floor)+"x"+curInstr.getP2()));		// address is lexical level + "x" + offset number							
					}
					if (ldpFlag) ldpLine++;
					currentLn++;
					break;
				case "lda":
					if (curInstr.getP1().equals("1")){
						exStack.push("0x"+curInstr.getP2());
					}else{
						exStack.push(String.valueOf(floor)+"x"+curInstr.getP2());
					}
					if (ldpFlag) ldpLine++;
					currentLn++;
					break;
				case "str":
					String assemKey; 
					if(curInstr.getP1().equals("1")){
						assemKey = String.valueOf("0x"+curInstr.getP2());
						memory.put(assemKey, exStack.pop());
					} else{
						assemKey = String.valueOf(floor)+"x"+curInstr.getP2();
						memory.put(assemKey, exStack.pop());
					}
					currentLn++;
					break;
				case "sym":
					if(ldpLine>0){
						memory.put(String.valueOf(floor)+"x"+curInstr.getP2(), paramStack.pop());
						ldpLine--;
					}
					else{
						for (int i=Integer.parseInt(curInstr.getP2());i<Integer.parseInt(curInstr.getP2())+Integer.parseInt(curInstr.getP3());i++){
							String tempString = String.valueOf(floor) + "x" + String.valueOf(i);
							memory.put(tempString, "");
						}
					}
					currentLn++;
					break;
				case "ldi":
					exStack.push(memory.get(exStack.pop()));
					currentLn++;
					break;
				case "sti":
					String val = exStack.pop();
					String key = exStack.pop();
					memory.put(key, val);
					currentLn++;
					break;
				//*********************//
				
				//********JUMP*********//>
				case "fjp":
					if(!exStack.pop().equals("0")){
						currentLn++;
						break;
					}
					tempLn = label.findLabel(curInstr.getP1());	// find label's line number
					if(tempLn<0){	// if there is no label it returns -1
						System.out.println("no label found error");
					}
					else currentLn = tempLn;		// jump!!!
					break;
				case "tjp":
					if(exStack.pop().equals("0")){
						currentLn++;
						break;
					}
					tempLn = label.findLabel(curInstr.getP1());	// find label's line number
					if(tempLn<0){	// if there is no label it returns -1
						System.out.println("no label found error");
					}
					else currentLn = tempLn;		// jump!!!
					break;
				case "ujp":
					tempLn = label.findLabel(curInstr.getP1());	// find label's line number
					if(tempLn<0){	// if there is no label it returns -1
						System.out.println("no label found error");
					}
					else currentLn = tempLn;		// jump!!!
					break;
				//************************//
				
				//********FUNC********//>	
				case "call":
					pcNsp[0] = currentLn;
					ldpFlag = false;
					
					if(curInstr.getP1().equals("write")){ 		// implement write operation
						System.out.println(exStack.pop());		// by println function
						currentLn++; 
						break;
					} else if(curInstr.getP1().equals("read")){// implement read operation
						Scanner scan = new Scanner(System.in);
						System.out.print("INPUT : ");
						String[] splitKey = exStack.pop().split("x");
						if(splitKey.length!=2){
							System.out.println("ERROR!! Stack[top] should be address!!");
							break;
						}
						else{
							if(Integer.parseInt(splitKey[1])<1){
								System.out.println("ERROR!! address is out of range");
								break;
							}
							memory.put(splitKey[0]+"x"+splitKey[1],scan.nextLine());
						}
						currentLn++; 
						break;
					} else if(curInstr.getP1().equals("lf")){	// implement line feed operation
						System.out.println("");
						currentLn++; 
						break;
					}
					
					tempLn = label.findLabel(curInstr.getP1());	// find label's line number
					floor++;
					
					if(tempLn<0){					// if there is no label it returns -1
						System.out.println("no label found error");
					}
					else{
						
						callStack.push(pcNsp);	// push current line number and stack point
						currentLn = tempLn;		// and jump!!
						pcNsp = new int[2];
						pcNsp[0]=pcNsp[1]=0;
					}
					for(int i=0;i<ldpLine;i++)	paramStack.push(exStack.pop());
					break;
				case "end":
					if(callStack.isEmpty()) breakPlag = true;		// if there is no point to return, change flag to true for breaking loop
					else{												// clear the useless local variable 
						pcNsp = callStack.pop();						// pop call stack and get integer array
						currentLn = pcNsp[0]+1;						// if there are some points to return, jump!! 
						while(exStack.size()!=pcNsp[1]){ exStack.pop(); }	// pop all of elements for post area
						
					}
					floor--;
					break;
				
				case "retv":
					int temp[] = 	callStack.pop();	// stack point +1, so 'end' operation can't clear out return value
					temp[1]++;
					callStack.push(temp);
					currentLn++;
					break;
				//************************//
			
				//********RANGE*******//>
				case "chkl":
					if(Integer.parseInt(exStack.pop())>Integer.parseInt(curInstr.getP1())) System.out.println("Error!!");
					currentLn++;
					break;
				case "chkh":
					if(Integer.parseInt(exStack.pop())<Integer.parseInt(curInstr.getP1())) System.out.println("Error!!");
					currentLn++;
					break;
				//************************//
				default:				// when it just need to exStack operation
					dp.execute(exStack, curInstr.getOpcode());
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