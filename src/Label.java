import java.util.ArrayList;

public class Label {
	public class LabelElem{
		private String labelName;
		private int lineNum;
		public LabelElem(){
			labelName = "";
			lineNum=0;
		}
		public void setLabel(String lname, int lnum){
			labelName = lname;
			lineNum = lnum;
		}
		public String getLabelName(){ return labelName; }
		public int getLineNum() { return lineNum; }
	}	
	private ArrayList<LabelElem> labelBox = new ArrayList<LabelElem>();
	private int retNum;
	
	public Label(){ retNum = -1; }
	public void addLabel(String lname, int lnum){
		LabelElem element = new LabelElem();
		element.setLabel(lname, lnum);
		labelBox.add(element);
	}
	public int findLabel(String lname){
		for(int i=0;i<labelBox.size();i++){
			if(labelBox.get(i).getLabelName().equals(lname)){
				retNum = labelBox.get(i).getLineNum();
			}
		}
		return retNum;
	}
}
