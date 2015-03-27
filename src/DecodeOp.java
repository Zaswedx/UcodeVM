import java.util.Stack;


public class DecodeOp {
	public int getOrderLength(String opcode){
		switch(opcode.toLowerCase()){
		case "proc":
		case "sym":
			return 3;
		case "¿¬½À":
			return 10;
		}
		
		return 0;
	}
	
	public void setStack(Stack<String> stack,String opcode){
		switch(opcode.toLowerCase()){
		case "add":
			String a = stack.pop();
			String b = stack.pop();
			int c = Integer.valueOf(a)+Integer.valueOf(b);
			stack.push(String.valueOf(c));
			break;
		}
	}
}
