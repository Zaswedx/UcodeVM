import java.util.ArrayList;
import java.util.Stack;
import java.util.EmptyStackException;

public class DecodeOp {
	public int getOrderLength(String opcode){					//占쏙옙占� 占쏙옙占쏙옙 占쌔쇽옙
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
	
	public void execute(Stack<Integer> stack,String opcode){	//占쏙옙占� 占쏙옙占쏙옙
		int temp;
		try{
			switch(opcode.toLowerCase()){
			// Unary
			case "notop":
				temp = stack.pop();
				if(temp!=0) 	temp = 0;
				else			temp = 1;
				stack.push(temp);
				break;
			case "neg":
				temp = stack.pop();
				temp = -temp;
				stack.push(temp);
				break;
			case "inc":
				temp = stack.pop();
				temp++;
				stack.push(temp);
				break;
			case "dec":
				temp = stack.pop();
				temp--;
				stack.push(temp);
				break;
			case "dup":
				temp = stack.pop();
				stack.push(temp);
				stack.push(temp);
				break;
			// Binary
			case"add":
				temp = stack.pop()+stack.pop();
				stack.push(temp);
				break;
			case "sub":
				temp = stack.pop()-stack.pop();
				stack.push(temp);
				break;
			case "mult":
				temp = stack.pop()*stack.pop();
				stack.push(temp);
				break;
			case "div":
				temp = stack.pop()/stack.pop();
				stack.push(temp);
				break;
			case "mod":
				temp = stack.pop()%stack.pop();
				stack.push(temp);
				break;
			case "swp":	
				int temp2 = stack.pop();	
				temp = stack.pop();
				stack.push(temp2);
				stack.push(temp);
				break;
			case "and":	
				temp = stack.pop()*stack.pop();
				if(temp!=0)  	temp = 1;
				else			temp = 0;
				stack.push(temp);
				break;
			case "or":
				temp = stack.pop();
				int temp1 = stack.pop();
				if(temp==0&&temp1==0)	stack.push(0);
				else						stack.push(1);
				break;
			// comparison operation -> if(stack[top] (compare) stack[top-1])	statement;
			case "gt":
				if(stack.pop()<stack.pop())	stack.push(1);
				else							stack.push(0);
				break;
			case "lt":
				if(stack.pop()>stack.pop())	stack.push(1);
				else							stack.push(0);
				break;
			case "ge":
				if(stack.pop()<=stack.pop())	stack.push(1);
				else								stack.push(0);
				break;
			case "le":
				if(stack.pop()>=stack.pop())	stack.push(1);
				else								stack.push(0);
				break;
			case "eq":
				if(stack.pop()==stack.pop())	stack.push(1);
				else								stack.push(0);
				break;
			case "ne":
				if(stack.pop()!=stack.pop())	stack.push(1);
				else								stack.push(0);
				break;
			}
		} catch(EmptyStackException e){
			e.printStackTrace();
		}
	}
	
	public void setVar(ArrayList<Variable> vmem, Instruction p){
		Variable v = new Variable();
		switch(p.getOpcode()){
		case "sym" :
			v.setBlock(Integer.valueOf(p.getP1()));
			v.setS_num(Integer.valueOf(p.getP2()));
			v.setSize(Integer.valueOf(p.getP3()));
			vmem.add(v);
			break;
		}
	}
}
