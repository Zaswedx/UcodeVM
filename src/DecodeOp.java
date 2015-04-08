import java.util.Stack;
import java.util.EmptyStackException;

public class DecodeOp {
	public int getOrderLength(String opcode){					//�뜝�룞�삕�뜝占� �뜝�룞�삕�뜝�룞�삕 �뜝�뙏�눦�삕
		switch(opcode.toLowerCase()){
		case "proc":
		case "sym":
			return 3;
		case "lod":
		case "str":
		case "lda":
			return 2;
		case "ldc":
		case "bgn":
		case "call":
		case "tjp":
		case "fjp":
		case "ujp":
			return 1;
		}
		
		return 0;
	}
	
	public void execute(Stack<String> stack,String opcode){	
		int temp;
		String[] splitKey0,splitKey1;
		try{
			switch(opcode.toLowerCase()){
			// Unary
			case "notop":
				temp = Integer.parseInt(stack.pop());
				if(temp!=0) 	temp = 0;
				else			temp = 1;
				stack.push(String.valueOf(temp));
				break;
			case "neg":
				temp = Integer.parseInt(stack.pop());
				temp = -temp;
				stack.push(String.valueOf(temp));
				break;
			case "inc":
				temp = Integer.parseInt(stack.pop());
				temp++;
				stack.push(String.valueOf(temp));
				break;
			case "dec":
				temp = Integer.parseInt(stack.pop());
				temp--;
				stack.push(String.valueOf(temp));
				break;
			case "dup":
				temp = Integer.parseInt(stack.pop());
				stack.push(String.valueOf(temp));
				stack.push(String.valueOf(temp));
				break;
			// Binary
			case"add":		// add OP should handle sum of address and constant 
				splitKey0 = stack.pop().split("x");		
				splitKey1 = stack.pop().split("x");	
				if(splitKey0.length==1){			// if splitKey0 is constant
					if(splitKey1.length==1){			// and splitKey1 is constant
						temp = Integer.parseInt(splitKey0[0]) + Integer.parseInt(splitKey1[0]);
						stack.push(String.valueOf(temp));	// just add and push
					}
					else{							// else splitKey1 is address
						temp = Integer.parseInt(splitKey0[0])+Integer.parseInt(splitKey1[1]);	// constant key0 + offset of key1
						if(temp<0){ 					
							System.out.println("ERROR - out of hash map key range!!");
							break;
						}
						stack.push(splitKey1[0]+"x"+String.valueOf(temp));
					}
				}
				else if(splitKey1.length==1){	// if splitKey0 is address and splitKey is constant
					temp = Integer.parseInt(splitKey1[0])+Integer.parseInt(splitKey0[1]);		// constant key1 + offset of key0
					if(temp<0){
						System.out.println("ERROR - out of hash map key range!!");
						break;
					}
					stack.push(splitKey0[0]+"x"+String.valueOf(temp));
				}
				else	System.out.println("ERROR - sum of addresses is banned!!");
				break;
			case "sub":
				temp = Integer.parseInt(stack.pop());
				temp = Integer.parseInt(stack.pop())-temp;
				stack.push(String.valueOf(temp));
				break;
			case "mult":
				temp = Integer.parseInt(stack.pop())*Integer.parseInt(stack.pop());
				stack.push(String.valueOf(temp));
				break;
			case "div":
				temp = Integer.parseInt(stack.pop());
				temp = Integer.parseInt(stack.pop())/temp;
				stack.push(String.valueOf(temp));
				break;
			case "mod":
				temp = Integer.parseInt(stack.pop());
				temp = Integer.parseInt(stack.pop())%temp;
				stack.push(String.valueOf(temp));
				break;
			case "swp":	
				int temp2 = Integer.parseInt(stack.pop());	
				temp = Integer.parseInt(stack.pop());
				stack.push(String.valueOf(temp2));
				stack.push(String.valueOf(temp));
				break;
			case "and":	
				temp = Integer.parseInt(stack.pop())*Integer.parseInt(stack.pop());
				if(temp!=0)  	temp = 1;
				else			temp = 0;
				stack.push(String.valueOf(temp));
				break;
			case "or":
				temp = Integer.parseInt(stack.pop());
				int temp1 = Integer.parseInt(stack.pop());
				if(temp==0&&temp1==0)	stack.push(String.valueOf(0));
				else						stack.push(String.valueOf(1));
				break;
			// comparison operation -> if(stack[top] (compare) stack[top-1])	statement;
			case "gt":
				if(Integer.parseInt(stack.pop())<Integer.parseInt(stack.pop()))	stack.push(String.valueOf(1));
				else																		stack.push(String.valueOf(0));
				break;
			case "lt":
				if(Integer.parseInt(stack.pop())>Integer.parseInt(stack.pop()))	stack.push(String.valueOf(1));
				else																		stack.push(String.valueOf(0));
				break;
			case "ge":
				if(Integer.parseInt(stack.pop())<=Integer.parseInt(stack.pop()))	stack.push(String.valueOf(1));
				else																		stack.push(String.valueOf(0));
				break;
			case "le":
				if(Integer.parseInt(stack.pop())>=Integer.parseInt(stack.pop()))	stack.push(String.valueOf(1));
				else																		stack.push(String.valueOf(0));
				break;
			case "eq":
				if(Integer.parseInt(stack.pop())==Integer.parseInt(stack.pop()))	stack.push(String.valueOf(1));
				else																		stack.push(String.valueOf(0));
				break;
			case "ne":
				if(Integer.parseInt(stack.pop())!=Integer.parseInt(stack.pop()))	stack.push(String.valueOf(1));
				else																		stack.push(String.valueOf(0));
				break;
			}
			
		} catch(EmptyStackException e){
			e.printStackTrace();
		}
	}

}
