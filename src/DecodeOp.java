
public class DecodeOp {
	public int getOrderLength(String opcode){
		switch(opcode.toLowerCase()){
		case "proc":
		case "sym":
			return 3;
			
		}
		
		return 0;
	}
}
