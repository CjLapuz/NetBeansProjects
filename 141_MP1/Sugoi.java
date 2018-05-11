package pkg141_mps;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Sugoi {
    FileReader fr;
    BufferedReader br;

    String lineReader;
    String func;
    ArrayList<String> container;
    ArrayList<String> validNames = new ArrayList();                              // Storage of valid varible names for redundancy
    ArrayList<String> reservedWords = new ArrayList();
    ArrayList<String> dataTypes = new ArrayList();
    
    int count = 0;

    int datatype = 0;
    boolean name = false;
    boolean operator = false;
    boolean operand = false;
    boolean ender = false;
    boolean operation = false;


    public Sugoi() {

        String temp = "";

        container = new ArrayList<String>();

        dataTypes.add("int");
        dataTypes.add("double");
        dataTypes.add("float");
        dataTypes.add("char");
        dataTypes.add("int,");
        dataTypes.add("double,");
        dataTypes.add("float,");
        dataTypes.add("char,");        


        reservedWords.add("int");
        reservedWords.add("double");
        reservedWords.add("float");
        reservedWords.add("char");
        reservedWords.add("void");
        reservedWords.add("if");
        reservedWords.add("else");
        reservedWords.add("for");
        reservedWords.add("return");
        reservedWords.add("while");
        reservedWords.add("null");

        boolean isFirst = true;

        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Write the name of the file");
            // FileReader reads text files in the default encoding.
            fr = new FileReader("C:\\Users\\Jeremy\\Documents\\NetBeansProjects\\141_MPs\\src\\pkg141_mps\\" + sc.next());

            // Always wrap FileReader in BufferedReader.
            br = new BufferedReader(fr);

            while((lineReader = br.readLine()) != null) {
                if(isFirst) {
                    count = Integer.parseInt(lineReader);
                    isFirst = false;
                } else {
                    if (lineReader.contains("{")) {
                        temp = lineReader;
                        while((lineReader = br.readLine()) != null){
                            if(lineReader.contains("}")){
                                temp = temp + " " + lineReader;
                                container.add(temp);
                                temp = "";
                                break;
                            }else{
                                temp = temp + lineReader + " ";
                            }
                        }
                    } else if(lineReader.contains(";")){
                        temp = temp + lineReader;
                        container.add(temp);
                        temp = "";
                    } else {
                        temp = temp + lineReader + " ";
                    }
                }
            }
            System.out.println("diri sugod");
            System.out.println(container);
            // Always close files.
            br.close();
        }

        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            "mpa1.in" + "'");
        }

        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + "mpa1.in" + "'");
        }

        for(int j = 0; j < count; j++) {

            String testcase1 = container.get(j);
            System.out.println("Test case number: " + (j+1));

            String tab = "\t";
            if(testcase1.contains(tab)) {
                testcase1 = testcase1.replaceAll(tab, " ");
            }
            //System.out.println(testcase1);
            ArrayList<String> tc = new ArrayList();
            StringTokenizer st = new StringTokenizer(testcase1, " ");

            while(st.hasMoreTokens()){
                tc.add(st.nextToken());
            }

            ArrayList<String> namecolletion = new ArrayList<>();

            //Function Check:
            
            ArrayList<String> insideParenthesis = new ArrayList();
            for (int dd = 0; dd < tc.size(); dd++) {
                String tok = new String();
                String temp2;
                int hold = 0;


                String test = new String();
                test = tc.get(dd);
                System.out.println("TEST:"+test);
                if (test.contains("(")) {
                    if (tc.get(0).equals("int") || tc.get(0).equals("char") || tc.get(0).equals("double") || tc.get(0).equals("float") || tc.get(0).equals("void")) {
                        String temp1 = tc.get(1);
                        if(tc.get(1).contains("(")){
                            // function check
                            temp1 = temp1.substring(0, temp1.length() - 2);
                        // token only contains function/variable name
                        } else {
                            if (tc.size() < 3) {
                                //if naay space before sa "(" 
                                if (tc.get(2).contains("(")){
                                    if (checkName(temp1)){
                                        // do nthin
                                    } else {
                                        System.out.println("INVALID FUNCTION NAME DECLARATION");
                                        break;
                                    }  
                                }
                            } else {
                                if (checkName(temp1)){
                                    // do nothing
                                } else {
                                    System.out.println("INVALID VARIABLE NAME DECLARATION");
                                    break;            
                                }
                            }
                        }
                    } else {
                        System.out.println("INVALID FUNCTION DATA TYPE DECLARATION");
                        break;
                    }  
                    //check token get  (0)
                    if(test.contains(")")) {
                        //check for curly brace
                        int holdxd = 0;
                        String holderxd = new String();
                        for(int i = 0; i < test.length(); i++){
                            if(test.charAt(i) == '('){
                                holdxd = i+1;
                            }
                            
                            if(test.charAt(holdxd) == ')'){ 
                                // Extract expressions inside the curly brackets 
                                if(test.contains("{")){
                                    checkSulodCurlies(tc, test); 
                                    
                                }
                                else{
                                    System.out.println("VALID FUNCTION DECLARATION");
                                    //LABAY TO FUNCTION NA MUPRINT SA OUTPUT FILE KANA NAA SA TAAS                                    
                                }
                                break;
                            }
                            if(test.charAt(i) == ')'){
                                holderxd = test.substring(holdxd, i);
                                // System.out.println(holderxd);
                                break;
                            }
                        }
                        int checkIfValidDataType = checkFuncParameter(holderxd);
                        if(checkIfValidDataType == 1){
                            System.out.println("VALID FUNCTION DECLARATION");
                        }
                        else{
                            System.out.println("INVALID FUNCTION DATA TYPE DECLARATION");
                        }
                        //Function Declaration without parameters
                        //find curly braces or ;
                        
                        break;
                    }
                    else {
                        //ikapila na token
                        for(int o = dd; o < tc.size(); o++) {
                            boolean flag3 = false;
                            // System.out.println("size:"+tc.size());
                        //find substring after (
                            temp2 = tc.get(o);
                            hold = 0;
                            for(int y = 0; y < temp2.length(); y++) {
                                if(temp2.charAt(y) == '(') {
                                    //System.out.println("YEST " + temp2);
                                    hold = y + 1;
                                    insideParenthesis.add(temp2.substring(hold, temp2.length()));
                                    //System.out.println("FIRST TOK:"+temp2.substring(hold, temp2.length()));
                                    // System.out.println(temp2.charAt(hold));
                                    flag3 = true;
                                    break;
                                }
                                if(temp2.charAt(y) == ')') {
                                    tok = temp2.substring(hold, y);
                                    // System.out.println("temp2:"+temp2);
                                    insideParenthesis.add(tok);
                                    flag3 = true;
                                    break;
                                }

                            }
//                            if(!flag3){
//                                insideParenthesis.add(temp2);    
//                                break;
//                            }
                            
                            // System.out.println("Size of array: " + insideParenthesis.size());
                        }
                        // System.out.println("BRUH Test case: " + insideParenthesis.toString());
                        for(int y = 0; y  < insideParenthesis.size(); y++) {
                            boolean flag69 = false;
                            String funcTokens = insideParenthesis.get(y);
                            switch(checkFuncParameter(funcTokens)) { 
                                //1 - valid reserved word
                                //2 - valid reserved word && last element of array
                                //3 - valid variable name 
                                //4 - valid variable name && last element of array
                                //5 - invalid

                                case 1:
                                    // if(!flag69){
                                    //     flag69 = true;
                                    // }
                                    if(funcTokens.endsWith(",")) {
                                        break;
                                    } 
                                    if((y != insideParenthesis.size() - 1) && checkFuncParameter(insideParenthesis.get(y + 1)) == 1){
                                        //SEND TO FUNCTION NA MAG INGON NA INVALID VAR NAME
                                        System.out.println("INVALID VARIABLE NAME DECLARATION");
                                        break;
                                    }
                                    if (y == insideParenthesis.size() - 1) {
                                        System.out.println("VALID FUNCTION DECLARATION");
                                        break;
                                    }
                                    break;
                                case 2:
                                    if(funcTokens.endsWith(",")) {
                                        break;
                                    } else  if(y == insideParenthesis.size() - 1) {
                                        System.out.println("VALID FUNCTION DECLARATION");
                                        break;
                                    }
                                default:
                                    System.out.println("INVALID FUNCTION PARAMETERS DECLARATION");
                                        break;
                            }
                            
                        }
                    }
                    //STORE NEXT TOKENS INTO NEW ARRAY UNTIL YOU FIND ) 
                    //THEN CHECK IF AFTER ) HAS { } OR ;

                    }
                    
                    /*if(test.contains("{")) {
                    System.out.println("HAS FUNCTION DEFINITION");
                    break;
                    } else {
                        System.out.println("HAS FUNCTION DECLARATION");
                        break;
                    }*/
                }

            // VARIABLE CHECKER
            if ((!testcase1.contains("(") && !testcase1.contains(")")) && (!testcase1.contains("{") && !testcase1.contains("}"))
                || testcase1.contains("=")) 
            {
                varCheck(tc);
            }

    //        for(int i = 0; i < tc.size(); i++) {
    //            if(datatype == 0) {
    //                if (tc.get(i).equals("int") || tc.get(i).equals("char") || tc.get(i).equals("double") || tc.get(i).equals("float")) {
    //                    if (tc.get(i).equals("float")) {
    //                        datatype = 2;
    //                    } else {
    //                        datatype = 1;
    //                    }
    //                }
    //            }else{
    //                if(tc.get(i).equals("=")) {
    //
    //                }
    //            }
    //        }
        }
        validNames.clear();

    }

    public static void main(String[] args) {
        Sugoi a = new Sugoi();
    }

    void checkSulodCurlies(ArrayList<String> tc, String token){ 
        String returnType = tc.get(0); 
        String extracted;
        int start;
        for(int i = 0; i < tc.size(); i++){
            for(int j = 0; j < token.length(); j++){
                if(token.charAt(j) == '{' && token.length() == 1){
                    start = j;
                    break;
                    //ang next token ang start
                } 
            }    
        }
    }


    //checker if variable declaration na
   
    // Checks name validity
    // TODO: add DataTypes words checker
    int checkFuncParameter(String str){
        //1 - valid reserved word
        //2 - valid variable name 
       //3 - invalid
        for(int h = 0; h < dataTypes.size(); h++) {
            if(str.equals(dataTypes.get(h))) {
                //System.out.println("Reserved Word;");
                //valid data types as parameter
                return 1;
            } 
        }

        // Check for if valid starting character
        if (Character.isAlphabetic(str.charAt(0)) || str.charAt(0) == '_' || str.charAt(0) == '$'){
            // Single character case
            if (str.length() == 1 && !str.equals("_"))
                return 2;
            // String case
            else if (str.length() == 2 && str.endsWith(",")) {
                return 2;
            }
            else {
                int i;
                for (i = 1; i < str.length(); i++){
                    char currChar = str.charAt(i);
                    // Checks if there are non valid characters in the string
                    if (Character.isAlphabetic(currChar) ||
                        Character.isDigit(currChar) ||
                        currChar == '_' || currChar == '$'){
                        //do nothing
                    // } if (currChar == ';' && i == str.length() - 1 ||
                    //            currChar == '=' && i == str.length() - 1 ||
                    //            currChar == ',' && i == str.length() - 1){
                    //     // do nothing
                    // } 
                
                    }
                    // if string is traversed with no wrong cases
                    if (i + 1 == str.length())
                        return 2;
                }
            }
        //System.out.println("Invalid?");
        }
        return 3;
    }
    
    // Checks validity of a variable declaration
    void varCheck(ArrayList<String> tc){
        if (tc.get(0).equals("int") || tc.get(0).equals("char") || tc.get(0).equals("double") || tc.get(0).equals("float")) {
            //check the following statements after data type 
            String expression = new String();
            int holdr = 1;
            // expression holds everything after the data type in vairable declaration with no spaces
            for (; holdr < tc.size(); holdr++){
                if (isReservedWord(tc.get(holdr))){
                    System.out.println("INVALID VARIABLE DECLARATION (RESERVED WORD FOUND)");
                    break;
                } else {
                    expression = expression.concat(tc.get(holdr));
                    expression.trim();
                }
            }
            if (expression.endsWith(";")) {
                // Traverse String <minus 1 to stop the check on ';' at the end>
                int ctr = 0;
                char curr = 0;                                                   // Empty initialization
                boolean foundComma = false;
                String varName = new String();                                   // holds the variable name
                
                // First Character check
                if (Character.isAlphabetic(expression.charAt(0)) || expression.charAt(0) == '_' || expression.charAt(0) == '$'){
                    varName += expression.charAt(0);
                    ctr = 1;
                    // Checking the variable names
                    for (; ctr < expression.length() - 1; ctr++){ 
                        curr = expression.charAt(ctr);
                        
                        if (curr == ';' || curr == '='){ 
                            validNames.add(varName);
                            ctr++;
                            break;
                        // Comma case
                        } else if (curr == ','){
                            if (foundComma){
                                System.out.println("INVALID VARIABLE DECLARATION (INVALID NAME)");
                                break;
                            } else {
                                foundComma = true;
                                validNames.add(varName);
                                varName = "";
                            }
                        // Character check
                        } else {
                            // check if valid character
                            if (Character.isAlphabetic(curr) || Character.isDigit(curr) || curr == '_' || curr == '$'){
                                varName += curr;
                                foundComma = false;
                            } else {
                                break;
                            }
                            
                        }
                    }
                    if (ctr == 0){
                        System.out.println("INVALID VARIABLE DECLARATION (NO VAR NAME)");
                    
                    // Check assignement 
                    } else if (curr == '='){
                        checkValue(tc.get(0), expression.substring(ctr, expression.length() - 1));
                    // Check for cases of multiple semicolons
                    } else if (curr == ';'){
                       for (; ctr < expression.length() - 1; ctr++){
                           if (expression.charAt(ctr) != ';') {
                               System.out.println("INVALID VARIABLE DECLARATION (UNKNOWN CHARACTER AFTER ;)");
                           } 
                       }
                        System.out.println("VALID VARIABLE DECLARATION");
                    } else {
                        System.out.println("VALID VARIABLE DECLARATION");
                    }
                        
                } else {
                    System.out.println("INVALID VARIABLE DECLARATION (INVALID NAME)");
                }
                  
            // If the entire var decalaration is traversed and does not end in a semicolon
            } else if (holdr == tc.size() - 1){
                System.out.println("INVALID VARIABLE DECLARATION (NO ';')");
            }
              
        } else {
            System.out.println("INVALID VARIABLE DECLARATION (DATA TYPE X)");
        }
    }
    
    // Checks if the given string is a reserved word 
    boolean isReservedWord(String str){
        // Checks if it is a Reserved word
        for(int h = 0; h < reservedWords.size(); h++) {
            if(str.equals(reservedWords.get(h))) {
                return true;
            } 
        }
        return false;
    }
    
    // Check validity and availability of the name of the function or variable
    boolean checkName(String str){
        
        if (isReservedWord(str))
            return false;
        
        // Check for if valid starting character
        if (Character.isAlphabetic(str.charAt(0)) || str.charAt(0) == '_' || str.charAt(0) == '$'){ 
            // Single character case
            if (str.length() == 1 && !str.equals("_")) {
                return true;
            // More than one character case
            } else {
                // i is 1 since we already checked index 0
                int i = 1;
                char currChar;
                while (i < str.length()) {
                    currChar = str.charAt(i);
                  
                    if (Character.isAlphabetic(currChar) ||                      // proceeds to the next character when valid
                        Character.isDigit(currChar) ||
                        currChar == '_' || currChar == '$')                                     
                    {
                        i++;
                    } else if (currChar == ';' || currChar == ',') {      
                        return ((i + 1) == str.length());
                    } else {
                        return false;
                    }               
                    
                }
                return true;                                                     // reached when all characters are valid
            }
        }
        return false;
    }
    
    // Checks assigned value of the variable
    // TODO : arithmetics and self assignment check (int x = x case)
    public void checkValue (String dataType, String val){
        boolean done = false; 
        for (int i = 0; i < validNames.size(); i++){
            if (val.equals(validNames.get(i))) {
                System.out.println("VALID VARIABLE DECLARATION");
                done = true;
            }
        }
        if (!done){
            if (dataType.equals("char")){
                if (val.length() == 3) {
                    if (val.startsWith("'") && val.endsWith("'")){
                        if (!val.substring(1, 1).equals("'")) {
                            System.out.println("VALID VARIABLE DECLARATION");
                        } else {
                            System.out.println("INVALID VARIABLE DECLARATION (INVALID CHAR ASSIGNMENT VALUE)");
                        }
                    }
                } else {
                    System.out.println("INVALID VARIABLE DECLARATION (INVALID CHAR ASSIGNMENT)");
                }
            } else if (dataType.equals("int")){
                try {
                    Integer.valueOf(val);
                    System.out.println("VALID VARIABLE DECLARATION");
                } catch (NumberFormatException e){
                    if (val.charAt(0) == '"' && val.charAt(val.length() - 1) == '"' ||
                        val.startsWith("'") && val.endsWith("'")){
                        System.out.println("VALID VARIABLE DECLARATION");
                    } else {
                        System.out.println("INVALID VARIABLE DECLARATION (INVALID INT VALUE)");
                    }
                }
            // Same conditions for both float and double
            } else {
                try{
                    Double.valueOf(val);
                    System.out.println("VALID VARIABLE DECLARATION");
                } catch (NumberFormatException e){
                    if (val.startsWith("'") && val.endsWith("'")) {
                        System.out.println("VALID VARIABLE DECLARATION");
                    } else {
                    System.out.println("INVALID VARAIBLE DECLARATION (INVALID FLOAT/DOUBLE VALUE)");                
                    }
                }
            }
        }
    }

    public void checkMaths(String str){
        
    }
}
    
    
    




