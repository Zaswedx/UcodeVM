import java.util.Stack;
import java.util.EmptyStackException;

public class DecodeOp {
	public int getOrderLength(String opcode){					//��� ���� �ؼ�
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
	
	public void setStack(Stack<String> stack,String opcode){	//��� ����
		int temp;
		try{
			switch(opcode.toLowerCase()){
			// Unary
			case "notop":
				temp = Integer.valueOf(stack.pop());
				if(temp!=0) 	temp = 0;
				else			temp = 1;
				stack.push(String.valueOf(temp));
				break;
			case "neg":
				temp = Integer.valueOf(stack.pop());
				temp = -temp;
				stack.push(String.valueOf(temp));
				break;
			case "inc":
				temp = Integer.valueOf(stack.pop());
				temp++;
				stack.push(String.valueOf(temp));
				break;
			case "dec":
				temp = Integer.valueOf(stack.pop());
				temp--;
				stack.push(String.valueOf(temp));
				break;
			case "dup":
				temp = Integer.valueOf(stack.pop());
				stack.push(String.valueOf(temp));
				stack.push(String.valueOf(temp));
				break;
			// Binary
			case"add":
				temp = Integer.valueOf(stack.pop())+Integer.valueOf(stack.pop());
				stack.push(String.valueOf(temp));
				break;
			case "sub":
				temp = Integer.valueOf(stack.pop())-Integer.valueOf(stack.pop());
				stack.push(String.valueOf(temp));
				break;
			case "mult":
				temp = Integer.valueOf(stack.pop())*Integer.valueOf(stack.pop());
				stack.push(String.valueOf(temp));
				break;
			case "div":
				temp = Integer.valueOf(stack.pop())/Integer.valueOf(stack.pop());
				stack.push(String.valueOf(temp));
				break;
			case "mod":
				temp = Integer.valueOf(stack.pop())%Integer.valueOf(stack.pop());
				stack.push(String.valueOf(temp));
				break;
			case "swp":	
				int temp2 = Integer.valueOf(stack.pop());	
				temp = Integer.valueOf(stack.pop());
				stack.push(String.valueOf(temp2));
				stack.push(String.valueOf(temp));
				break;
			case "and":	
				temp = Integer.valueOf(stack.pop())*Integer.valueOf(stack.pop());
				if(temp!=0)  	temp = 1;
				else			temp = 0;
				stack.push(String.valueOf(temp));
				break;
			case "or":
				temp = Integer.valueOf(stack.pop());
				int temp1 = Integer.valueOf(stack.pop());
				if(temp==0&&temp1==0)	stack.push("0");
				else						stack.push("1");
				break;
			// comparison operation -> if(stack[top] (compare) stack[top-1])	statement;
			case "gt":
				if(Integer.valueOf(stack.pop())<Integer.valueOf(stack.pop()))	stack.push("1");
				else																	stack.push("0");
				break;
			case "lt":
				if(Integer.valueOf(stack.pop())>Integer.valueOf(stack.pop()))	stack.push("1");
				else																	stack.push("0");
				break;
			case "ge":
				if(Integer.valueOf(stack.pop())<=Integer.valueOf(stack.pop()))	stack.push("1");
				else																	stack.push("0");
				break;
			case "le":
				if(Integer.valueOf(stack.pop())>=Integer.valueOf(stack.pop()))	stack.push("1");
				else																	stack.push("0");
				break;
			case "eq":
				if(Integer.valueOf(stack.pop())==Integer.valueOf(stack.pop()))	stack.push("1");
				else																	stack.push("0");
				break;
			case "ne":
				if(Integer.valueOf(stack.pop())!=Integer.valueOf(stack.pop()))	stack.push("1");
				else																	stack.push("0");
				break;
			}
		} catch(EmptyStackException e){
			e.printStackTrace();
		}
	}
	
	public void setVar(Stack<String> vstack, String opcode){
		
	}
}
