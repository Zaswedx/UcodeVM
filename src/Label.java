import java.util.ArrayList;
public class Label {			
	/************inner class**********/
	public class LabelElem{				// class for two tuple (label name, line number) data
		private String labelName;			// label name
		private int lineNum;					// line number
		public LabelElem(){					// constructor
			labelName = "";
			lineNum=0;
		}
		public void setLabel(String lname, int lnum){		// set data
			labelName = lname;
			lineNum = lnum;
		}
		public String getLabelName(){ return labelName; }	// get label name
		public int getLineNum() { return lineNum; }			// get line number
	}
	/************inner class**********/
	
	private ArrayList<LabelElem> labelBox = new ArrayList<LabelElem>(); 	// ArratList for storing Label data
	private int retNum;															 
	public Label(){ retNum = -1; }												// constructor
	public void addLabel(String lname, int lnum){								// add new label to arrayList
		LabelElem element = new LabelElem();
		element.setLabel(lname, lnum);
		labelBox.add(element);
	}
	public int findLabel(String lname){										// find label and return line number
		retNum = -1;																// inital value -1, if there is no label detected then return this
		for(int i=0;i<labelBox.size();i++){
			if(labelBox.get(i).getLabelName().equals(lname)){				// if there is label
				retNum = labelBox.get(i).getLineNum();							// get line number
			}
		}
		return retNum;															// return line number or -1
	}
}