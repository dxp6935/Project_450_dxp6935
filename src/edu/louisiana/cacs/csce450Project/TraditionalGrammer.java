package edu.louisiana.cacs.csce450Project;

import java.util.ArrayList;
import java.util.List;

public class TraditionalGrammer {

	private List<String> LHS;
	private List<String> RHS;
	private String lhsValue,rhsValue;
	private Integer rhsLength;
	public void init(){
		LHS = new ArrayList<String>();
		RHS = new ArrayList<String>();
		LHS.add("E");LHS.add("E");
		LHS.add("T");LHS.add("T");
		LHS.add("F");LHS.add("F");
		
		RHS.add("E+T");RHS.add("T");
		RHS.add("T*F");RHS.add("F");
		RHS.add("E");RHS.add("id");
		
	}
	
	public String processGrammer(Integer index){
		String output = "";
		index--;
		setLhsValue(LHS.get(index));
		setRhsValue(RHS.get(index));
		if(index==0||index==2){
			output = lhsValue+"="+rhsValue;
		}else{
			output = lhsValue;
		}
		if(rhsValue.contains("id"))
			 setRhsLength(rhsValue.length()-1);
		else
			 setRhsLength(rhsValue.length());
		return output;
	}

	public String getLhsValue() {
		return lhsValue;
	}

	public void setLhsValue(String lhsValue) {
		this.lhsValue = lhsValue;
	}

	public String getRhsValue() {
		return rhsValue;
	}

	public void setRhsValue(String rhsValue) {
		this.rhsValue = rhsValue;
	}

	public Integer getRhsLength() {
		return rhsLength;
	}

	public void setRhsLength(Integer rhsLength) {
		this.rhsLength = rhsLength;
	}
}
