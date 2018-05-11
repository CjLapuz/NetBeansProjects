package pkg141_mps;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Jeremy
 */

public class Maths {
    static String test;
    final char ar[] = {'+', '-', '*', '/'};
    static ArrayList<String> validNames = new ArrayList();
    static ArrayList<String> tokens = new ArrayList();
    
    public Maths(String test){
        this.test = test;
    }
    
    public boolean isMaths(){
        boolean operatorEncountered = false; 
        boolean operandEncountered = false;
        boolean digitEncountered = false;
        boolean varEncountered = false;
        int openParenthesisCount = 0, tokenIndx = 0, charIndx = 0;
        String varName = new String();
        
        StringTokenizer st = new StringTokenizer(test, " ");

        while(st.hasMoreTokens()){
            tokens.add(st.nextToken());
        }
            
        // Traversing the tokens
        for (; tokenIndx < tokens.size(); tokenIndx++) {
            String tok = tokens.get(tokenIndx);
            charIndx = 0;
            // Traversing the string
            for (; charIndx < tok.length(); charIndx++) {
                char curr = tok.charAt(charIndx);
                // Encounter a semi colon
                if (curr == ';'){
                    if (varEncountered){
                        if (!isValidName(varName)) {
                            return false;
                        }
                    }
                    // check for trailing semicolons
                    if (charIndx < tok.length() - 1){
                        for (; tokenIndx < tokens.size(); tokenIndx++) {
                            for (; charIndx < tok.length(); charIndx++) {
                                if (tok.charAt(charIndx) != ';'){
                                    return false;
                                }
                            }
                        }
                    }
                    break;
                    
                // Encounter an opening parenthesis
                } else if (curr == '(') {
                    if (!digitEncountered && !varEncountered) {
                        openParenthesisCount++;
                    } else {
                        return false;
                    }
                    
                // Encounter a closing parenthesis
                } else if (curr == ')'){
                    if (!operatorEncountered){
                        if (openParenthesisCount > 0) {
                            openParenthesisCount--;
                            operandEncountered = true;
                        } else { 
                            return false;
                        }
                    } else { 
                        return false;
                    }
                    
                // Encounter an operator
                // This also acts as the checker for the validity of the operand before it
                } else if (isOperator(curr)){
                    // Check if an operator has been encountered before this encounter
                    if (operatorEncountered || (tokenIndx == 0 && charIndx == 0)){
                        return false;
                    } else {
                        operatorEncountered = true;
                        // check if the variable is valid
                        if (varEncountered) {
                            if (isValidName(varName)){
                                varEncountered = false;
                                varName = "";
                            } else {
                                return false;
                            }
                        } else {
                            digitEncountered = false;
                        }
                        operandEncountered = false;
                    }
                    
                // Encounter a numeric value operand
                } else if (Character.isDigit(curr)){
                    if (!operandEncountered){
                        if (varEncountered){
                            varName+= curr;
                        } else {
                            digitEncountered = true;
                        }
                    }
                    
                    if (charIndx == tok.length() - 1){
                        operandEncountered = true;
                    }
                    operatorEncountered = false;
                        
                // Encountering the characters of a variable operand
                } else if (curr == '_' || curr == '$' || Character.isAlphabetic(curr)){
                    if (!operandEncountered && !digitEncountered){
                        varName += curr;
                        varEncountered = true;
                    } else {
                        return false;
                    }
                    
                    if (charIndx == tok.length() - 1){
                        operandEncountered = true;
                    }
                    operatorEncountered = false;
    
                // Encountering an invalid character
                } else {
                    return false;
                }
            }
        }
        return true;
    }
    
    // Checks if the character is an operator
    public boolean isOperator(char c){
        for (int i = 0; i < ar.length; i++){
            if (c == ar[i]){
                return true;
            }
        }
        return false;
    }
    
    // Check is the string is a registered name
    public boolean isValidName(String str){
        for (int i = 0; i < validNames.size(); i++){
            if (str.equals(validNames.get(i))){
                return true;
            }
        }
        return false;
    }
    
    // clears a file before writing
    public void clearFile(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("outu.out"));
            writer.write("");
            writer.close();
            
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    // Appends a given string to a file
    public void writeToFile(String str){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("outu.out"));
            writer.write(str);
            writer.newLine();
            writer.close();
            
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args){
        validNames.add("a1");
        validNames.add("b");
        validNames.add("c");
        
        Maths m = new Maths("( 6 + 1 ) * ( a1  -  b ) / ( c ) ; ;;");
        m.clearFile();
        if (m.isMaths()) {System.out.println("1");
            m.writeToFile("VALID");
        } else { System.out.println("2");
            m.writeToFile("INVALID");
        }   
    }
}
