import java.util.Stack;


public class DecodeOp {
	public int getOrderLength(String opcode){					//명령 길이 해석
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
	
	public void setStack(Stack<String> stack,String opcode){	//명령 연산
		switch(opcode.toLowerCase()){
		case "add":
			String a = stack.pop();
			String b = stack.pop();
			int c = Integer.valueOf(a)+Integer.valueOf(b);
			stack.push(String.valueOf(c));
			break;
		}
	}
	
	public void setVar(Stack<String> vstack, String opcode){
		
	}
}
