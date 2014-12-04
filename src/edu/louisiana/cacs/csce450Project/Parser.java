package edu.louisiana.cacs.csce450Project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Collections;
import java.util.List;

public class Parser{
	/*
	* YOUR CODE GOES HERE
	* 
	* You must implement two methods
	* 1. parse
	* 2. printParseTree
     
    * Print the intermediate states of the parsing process,
    * including the intermediate states of the parse tree,make
    * as specified in the class handout.
    * If the input is legal according to the grammar,
    * print ACCEPT, else UNGRAMMATICAL.
    * If the parse is successful, print the final parse tree.

	* You can modify the input and output of these function but not the name
	*/
	
	private boolean result = true;
	private List<String> inputList;
	private List<String> stackList;
	private LRParsingTable lrParsingTable;
	private TraditionalGrammer traditionalGrammer;
	private List<List<String>> outputPrintingList;
	List<String> parseTreeStackList;
	public Parser(String fileName){
		System.out.println("File to parse : "+fileName);
		inputList = new ArrayList<String>();
		stackList = new ArrayList<String>();
		outputPrintingList = new ArrayList<List<String>>();
		parseTreeStackList  = new ArrayList<String>();
		lrParsingTable = new LRParsingTable();
		traditionalGrammer = new TraditionalGrammer();
		intiOutputPrintList();
		readFileContent(fileName);
		lrParsingTable.initParsingTable();
		traditionalGrammer.init();
		doParse();
		
	}
	
	private void intiOutputPrintList(){
		List<String> tempOutputPrintList = new ArrayList<String>();
		tempOutputPrintList.add("");
		tempOutputPrintList.add("input");
		tempOutputPrintList.add("action");
		tempOutputPrintList.add("action");
		tempOutputPrintList.add("value");
		tempOutputPrintList.add("length");
		tempOutputPrintList.add("temp");
		tempOutputPrintList.add("goto");
		tempOutputPrintList.add("goto");
		tempOutputPrintList.add("stack");
		tempOutputPrintList.add("");
		List<String> tempOutputPrintList1 = new ArrayList<String>();
		tempOutputPrintList1.add("stack");
		tempOutputPrintList1.add("tokens");
		tempOutputPrintList1.add("loopup");
		tempOutputPrintList1.add("value");
		tempOutputPrintList1.add("of LHS");
		tempOutputPrintList1.add("of RHS");
		tempOutputPrintList1.add("stack");
		tempOutputPrintList1.add("lookup");
		tempOutputPrintList1.add("value");
		tempOutputPrintList1.add("action");
		tempOutputPrintList1.add("parse tree stack");
		List<String> tempOutputPrintList2 = new ArrayList<String>();
		tempOutputPrintList2.add("_________________________________________________________________________________________"
								+ "________________________________________________________________________________________");
		List<String> tempOutputPrintList3 = new ArrayList<String>();
		outputPrintingList.add(tempOutputPrintList);
		outputPrintingList.add(tempOutputPrintList1);
		outputPrintingList.add(tempOutputPrintList2);
		outputPrintingList.add(tempOutputPrintList3);
	}
	
	private void readFileContent(String fileName){
		try 
		{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				String[] strTempArr = sCurrentLine.split(" ");
				inputList = Arrays.asList(strTempArr);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	private void updateParseTreeStack(String push){
		String currentToken = "";
		if(push.contains("id"))
			currentToken = "id";
		else if(push.contains("E"))
			currentToken = "E";
		else if(push.contains("T"))
			currentToken = "T";
		else if(push.contains("F"))
			currentToken = "F";
		else 
			return;
		if(parseTreeStackList.size()==0)
			parseTreeStackList.add(currentToken);
		else{
			if(parseTreeStackList.size()>10){
				if(push.equals("T9")){
					boolean firsBlock = false,secondBlock =false,mainFirstBlock = true,mainSecondBlock =true;
					List<String> firstBlockList = new ArrayList<String>();
					List<String> secondBlockList = new ArrayList<String>();
					List<String> thirdBlockList = new ArrayList<String>();
					for(String temp : parseTreeStackList){
						if(temp.contains("[F")){
							if(mainFirstBlock){
								firsBlock = true;
							}
						}
						if(firsBlock)
							firstBlockList.add(temp);
						if(temp.contains("]")){
							if(mainFirstBlock){
								mainFirstBlock = false;
								firsBlock = false;
								continue;
							}
							if(temp.contains("]")){
								if(mainSecondBlock){
									mainSecondBlock = false;
								}else if(secondBlock){
									secondBlock = false;
								}
							}
						}
						if(temp.contains("[T")){
							if(mainSecondBlock){
								secondBlock = true;
							}
						}
						if(secondBlock){
							secondBlockList.add(temp);
						}
						
						
						if(!(firsBlock)){//
							if(!(secondBlock)){
								thirdBlockList.add(temp);
							}
						}
					}
					parseTreeStackList.clear();
					parseTreeStackList.add(0,"["+currentToken+" ");
					for(String temp : secondBlockList){
						parseTreeStackList.add(temp);
					}
					parseTreeStackList.add(" ] ");
					parseTreeStackList.add(" * ");
					for(String temp : firstBlockList){
						parseTreeStackList.add(temp);
					}
					for(String temp : thirdBlockList){
						parseTreeStackList.add(temp);
					}
					return;
				}else if(push.equals("E1")){
					boolean firsBlock = true,mainFirstBlock = true;//,mainSecondBlock =true;
					List<String> firstBlockList = new ArrayList<String>();
					List<String> secondBlockList = new ArrayList<String>();
					Integer counter = 0;
					for(String temp : parseTreeStackList){
						if(firsBlock)
							firstBlockList.add(temp);
						if(temp.contains("]")){
							if(mainFirstBlock){
								mainFirstBlock = false;
								continue;
							}else if(firsBlock){
								firsBlock = false;
								if(counter==0){
									mainFirstBlock = true;
									firsBlock = true;
									counter++;
								}
								continue;
							}
						}
						if(!firsBlock){
							secondBlockList.add(temp);
						}
					}
					parseTreeStackList.clear();
					parseTreeStackList.add(0,"["+currentToken+" ");
					for(String temp : secondBlockList){
						parseTreeStackList.add(temp);
					}
					parseTreeStackList.add(" + ");
					for(String temp : firstBlockList){
						parseTreeStackList.add(temp);
					}
					parseTreeStackList.add(" ]");
					return;
				}
			}
			
			if(currentToken.equalsIgnoreCase("id")){
				parseTreeStackList.add(0,""+currentToken+"");
			}else{
				parseTreeStackList.add(0,"["+currentToken+" ");
				Integer indexId = 0;
				for(String temp : parseTreeStackList){
					temp = temp.trim();
					if(temp.equalsIgnoreCase("id")){
						indexId++;
						break;
					}
					indexId++;
				}
				parseTreeStackList.add(indexId," ]");
			}
		}
	}
	
	private void doParse(){
		stackList.add(0+"");
		List<String> inputTokensList  = new ArrayList<String>();
		for (String string : inputList) {
			inputTokensList.add(string);
		}
		List<String> tempOutputPrintList1 = new ArrayList<String>();
		StringBuilder sb2 = new StringBuilder();
		StringBuilder sb3 = new StringBuilder();
		for(String temp : stackList){
			sb2.append(temp);
		}
		for(String temp : inputTokensList){
			sb3.append(temp);
		}
		Integer stackCursor=0;
		Integer counter = 0;
		boolean accept = false;
		while(true){
			List<String> tempOutputPrintList = new ArrayList<String>();
			List<String> tempStackList = new ArrayList<String>();
			if(inputTokensList.size()==0){
				break;
			}
			String TOS = stackList.get(stackCursor);
			String tokenOfInputList = inputTokensList.get(0);
			String parseTableValue = lrParsingTable.getValue(Integer.parseInt(TOS), (tokenOfInputList.trim()));
			StringBuilder sbStackList = new StringBuilder();
			StringBuilder sbInputTokens = new StringBuilder();
			for(String temp : stackList){
				sbStackList.append(temp);
			}
			for(String temp : inputTokensList){
				sbInputTokens.append(temp);
			}
			if(counter!=0){
				tempOutputPrintList.add(sbStackList.toString());//Stack
				tempOutputPrintList.add(sbInputTokens.toString());//input tokens
				tempOutputPrintList.add("["+TOS+","+tokenOfInputList+"]");//action lookup
				tempOutputPrintList.add(parseTableValue);//action value

			}else{
				tempOutputPrintList1.add(sb2.toString());//Stack
				tempOutputPrintList1.add(sb3.toString());//input tokens
				tempOutputPrintList1.add("["+TOS+","+tokenOfInputList+"]");//action lookup
				tempOutputPrintList1.add(parseTableValue);//action value
				
			}
			if(parseTableValue.startsWith("S")){
				stackList.add(tokenOfInputList);
				stackList.add(parseTableValue.charAt(1)+"");
				inputTokensList.remove(0);
				stackCursor+=2;
				for(String temp : stackList){
					tempStackList.add(temp);
				}
				tempStackList.remove((tempStackList.size()-1));
				tempStackList.remove((tempStackList.size()-1));
			}else if(parseTableValue.startsWith("R")){
				String traditionalGrammerValue = traditionalGrammer.processGrammer(Integer.parseInt(parseTableValue.charAt(1)+""));
				if(traditionalGrammerValue.contains("=")){// || traditionalGrammerValue.contains("*")){
					stackList.remove(stackList.get(stackCursor));
					stackCursor--;
					stackList.remove(stackList.get(stackCursor));
					stackCursor--;
					stackList.remove(stackList.get(stackCursor));
					stackCursor--;
					stackList.remove(stackList.get(stackCursor));
					stackCursor--;
				}else{
					String gotoParseTableValue = lrParsingTable.getValue(Integer.parseInt(stackList.get(stackCursor-2)), traditionalGrammerValue);
					stackList.set(stackCursor, gotoParseTableValue);
					stackList.set((stackCursor-1), traditionalGrammerValue);
				}
				for(String temp : stackList){
					tempStackList.add(temp);
				}
				tempStackList.remove((tempStackList.size()-1));
				tempStackList.remove((tempStackList.size()-1));
			}else if(parseTableValue.equalsIgnoreCase("accept")){
				//break;
				accept = true;
			}else{
				System.out.println("Check the parse table values,\nRow : "+TOS+"\nColumn:"+tokenOfInputList);
			}

			if(counter!=0){
				if(parseTableValue.startsWith("R")){
					tempOutputPrintList.add(traditionalGrammer.getLhsValue());//value of LHS
					tempOutputPrintList.add(traditionalGrammer.getRhsLength()+"");//length of RHS
					StringBuilder sbTempStackList = new StringBuilder();
					for(String temp:tempStackList){
						sbTempStackList.append(temp);
					}
					tempOutputPrintList.add(sbTempStackList.toString());//temp stack
					tempOutputPrintList.add("["+tempStackList.get((tempStackList.size()-1))+","+traditionalGrammer.getLhsValue()+"]");//goto lookup
					String gotoParseTableValue = lrParsingTable.getValue(Integer.parseInt(tempStackList.get((tempStackList.size()-1))), traditionalGrammer.getLhsValue());
					tempOutputPrintList.add(gotoParseTableValue);//goto value
				}else{
					tempOutputPrintList.add("");
					tempOutputPrintList.add("");
					tempOutputPrintList.add("");
					tempOutputPrintList.add("");
					tempOutputPrintList.add("");
				}
				tempStackList.add(stackList.get(stackCursor-1));
				tempStackList.add(stackList.get(stackCursor));
				if(accept)
					tempOutputPrintList.add("");//stack action
				else{
					tempOutputPrintList.add("push "+stackList.get(stackCursor-1)+stackList.get(stackCursor));//stack action
					updateParseTreeStack(stackList.get(stackCursor-1)+stackList.get(stackCursor));
				}
				StringBuilder sbParseTreeStack = new StringBuilder();
				for(String temp : parseTreeStackList){
					sbParseTreeStack.append(temp);
				}
				tempOutputPrintList.add(sbParseTreeStack.toString());
				outputPrintingList.add(tempOutputPrintList);
			}else{
				tempOutputPrintList1.add("");
				tempOutputPrintList1.add("");
				tempOutputPrintList1.add("");
				tempOutputPrintList1.add("");
				tempOutputPrintList1.add("");
				tempStackList.add(stackList.get(stackCursor-1));
				tempStackList.add(stackList.get(stackCursor));
				
				//else
					tempOutputPrintList1.add("push "+stackList.get(stackCursor-1)+stackList.get(stackCursor));//stack action
				
				updateParseTreeStack(stackList.get(stackCursor-1)+stackList.get(stackCursor));
				StringBuilder sbParseTreeStack = new StringBuilder();
				for(String temp : parseTreeStackList){
					sbParseTreeStack.append(temp);
				}
				tempOutputPrintList1.add(sbParseTreeStack.toString());
				outputPrintingList.add(tempOutputPrintList1);
				counter++;
			}
			stackList.clear();
			for(String temp:tempStackList){
				stackList.add(temp);
			}
			stackCursor = stackList.size()-1;
			if(accept)
				break;
		}//end of infinit while loop
	}//end of function
		
	/*
	* Dummy code
	*/
	public void printParseTree(){
		if(result){
			for(List<String> tempOutputPrintList:outputPrintingList){
				for(Integer itr=0;itr<tempOutputPrintList.size();itr++){
					if((tempOutputPrintList.get(itr).length()>7))
						System.out.print(tempOutputPrintList.get(itr)+"\t");
					else
						System.out.print(tempOutputPrintList.get(itr)+"\t\t");
					//counter++;
					
				}
				System.out.println();
			}
		}else{
			System.out.println("Arithmetic expression parse is failed !!");
		}
		
		//Printing parse tree
		System.out.println("Parse tree");
		System.out.println(parseTreeStackList.get(0).charAt(1));
		String addTabSpace = "";
		Integer counter =0,counter1=0;
		boolean arithmeticValue = false;
		for(String temp : parseTreeStackList){
			if(counter==0){
				counter++;
				continue;
			}
			temp.trim();
			if(temp.contains("]")){
				try{
					if(counter1==0){
						addTabSpace = addTabSpace.substring(0,addTabSpace.length()-2);
						counter1++;
					}
					else
						addTabSpace = addTabSpace.substring(0,addTabSpace.length()-1);
				}catch(Exception e){
					
				}
				continue;
			}
			if(!arithmeticValue)
				addTabSpace+="\t";
			if(temp.contains("[")){
				if(arithmeticValue)
					System.out.println(addTabSpace+" "+temp.charAt(1));
				else
					System.out.println(addTabSpace+temp.charAt(1));
				arithmeticValue = false;
			}
			else{
				System.out.println(addTabSpace+temp);
				if(temp.contains("*") || temp.contains("+")){
					arithmeticValue = true;
					counter1=0;
				}
			}
		}
		System.out.println("$");
	}

	/*
	* Dummy code
	*/
	public void parse(){
        printParseTree();
	}

}
