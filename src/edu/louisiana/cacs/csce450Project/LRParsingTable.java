package edu.louisiana.cacs.csce450Project;

public class LRParsingTable {

	String[][] parseTable = new String[12][9];
	String value = "";
	public void initParsingTable(){
		parseTable[0][0] = "S5";parseTable[0][3] = "S4";
		parseTable[0][6] = "1";parseTable[0][7] = "2";
		parseTable[0][8] = "3";
		
		parseTable[1][1] = "S6";parseTable[1][5] = "accept";

		
		parseTable[2][1] = "R2";parseTable[2][2] = "S7";
		parseTable[2][4] = "R2";parseTable[2][5] = "R2";
		
		parseTable[3][1] = "R4";parseTable[3][2] = "R4";
		parseTable[3][4] = "R4";parseTable[3][5] = "R4";

		parseTable[4][0] = "S5";parseTable[4][3] = "S4";
		parseTable[4][6] = "8";parseTable[4][7] = "2";
		parseTable[4][8] = "3";
		
		parseTable[5][1] = "R6";parseTable[5][2] = "R6";
		parseTable[5][4] = "R6";parseTable[5][5] = "R6";
		
		parseTable[6][0] = "S5";parseTable[6][3] = "S4";
		parseTable[6][7] = "9";parseTable[6][8] = "3";
		
		parseTable[7][0] = "S5";parseTable[7][3] = "S4";
		parseTable[7][8] = "10";
		
		parseTable[8][1] = "S6";parseTable[8][4] = "S11";
		
		parseTable[9][1] = "R1";parseTable[9][2] = "S7";
		parseTable[9][4] = "R1";parseTable[9][5] = "R1";
		
		parseTable[11][1] = "R5";parseTable[11][2] = "R5";
		parseTable[11][4] = "R5";parseTable[11][5] = "R5";
		
		parseTable[10][1] = "R3";parseTable[10][2] = "R3";
		parseTable[10][4] = "R3";parseTable[10][5] = "R3";
		
	}
	
	private Integer getColumnIntegerValue(String columnValue){
		Integer columnIntegerValue = 0;
		if(columnValue.equalsIgnoreCase("id"))
			columnIntegerValue = 0;
		else if(columnValue.equalsIgnoreCase("+"))
			columnIntegerValue = 1;
		else if(columnValue.equalsIgnoreCase("*"))
			columnIntegerValue = 2;
		else if(columnValue.equalsIgnoreCase("("))
			columnIntegerValue = 3;
		else if(columnValue.equalsIgnoreCase(")"))
			columnIntegerValue = 4;
		else if(columnValue.equalsIgnoreCase("$"))
			columnIntegerValue = 5;
		else if(columnValue.equalsIgnoreCase("E"))
			columnIntegerValue = 6;
		else if(columnValue.equalsIgnoreCase("T"))
			columnIntegerValue = 7;
		else if(columnValue.equalsIgnoreCase("F"))
			columnIntegerValue = 8;	
		
		return columnIntegerValue;
	}
	
	public String getValue(Integer rowValue, String columnValueStr){
		value = "";
		Integer colsValue = getColumnIntegerValue(columnValueStr);
		value = parseTable[rowValue][colsValue];
		setValue(value);
		return value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
