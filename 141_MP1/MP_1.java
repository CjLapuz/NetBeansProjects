package pkg141_mps;

/**
 * @author Jeremy
 */

import java.io.*;
import java.util.*;


public class MP_1 {
    
    // Constants to be used
    final static String FILENAME = "C:\\Users\\Jeremy\\Documents\\NetBeansProjects\\141_MPs\\src\\pkg141_mps\\mpa1.in";
    final static String[] D_TYPES = {"int", "char", "float", "double", "void"};
    final static String[] RES_WORDS = {"return", "main", "while", "if", "else", "try", "catch", "for"};
    
    final static int D_CNT = 4;                                                  // num of data types for variables    
    final static int F_CNT = 5;                                                  // num of data types for functions   
      
    // Global variables
    public static String result;                                                 // holds the all results from each test case    
    public ArrayList<String> varNames = new ArrayList();                         // holds the variable names of valid declarations   
    public ArrayList<String> tCases = new ArrayList();
    
    // Checks if the line only holds a variable declaration
    static boolean aVariable (String test){
        return test.contains("=") || !test.contains("(");
    }
    
    // Checks if the data type is valid
    // x represents the number of data types to be checked
    // since functions have an extra void data type
    static boolean dataType(String test, int x) {
        // Checks for the 4 basic data types
        for (int i = 0; i < x; i++){
            if (test.equals(D_TYPES[i]))
                return true;
        }
        // Special check for void data type
        if (x > D_CNT)
           return test.equals(D_TYPES[x - 1]);
        
        return false;
    }
    
    // Check if the variable / function name is valid
    static boolean validName(String var) {
        // Check for valid starting
        if (Character.isAlphabetic(var.charAt(0)) || var.charAt(0) == '_' || var.charAt(0) == '$'){
            // Single character case
            if (var.length() == 1 && !var.equals("_"))
                return true;
            // String case
            else {
                int i;
                for (i = 0; i < var.length(); i++){
                    // Checks if there are non valid characters in the string
                    if (Character.isAlphabetic(var.charAt(i)) ||
                        Character.isDigit(var.charAt(i)) ||
                        var.charAt(0) == '_' || var.charAt(0) == '$'){
                        //do nothing
                    } else {
                        break;
                    }
                }
                // if string is traversed with no wrong cases 
                return i == var.length();
            }
        }
        return false;
    }
    
    // Checks the validity of the assigned value based on the data type
    static boolean valueCheck(String dataType, String val){
        switch (dataType){
            case "char":
                
                break;
            case "float":
                
                break;
            case "double":
                
                break;
            default:                                                             // int is the default case                                                 
                
        }
        return false;
    }
    
    // Checks the validity of a variable declaration
    static boolean isVarValid (String test){
        boolean validity = false;
        boolean hasValue = true;
        
        String token[] = test.split(" ");
        int pointer = 0;
        // Simple cases of validity
        if (test.endsWith(";") && !test.contains(")")){ 
            // data type validity check
            if (dataType(token[pointer], D_CNT)){
                pointer++;                                                       // now points to the variable name/s for checking
                String check = token[pointer];
                
                // check if there is assignment value
                if (!test.contains("=")) {
                    if (check.length() > 2) {
                        check = check.substring(0, check.length() - 1);              // Remove the ';' at the end
                    } else {
                        check = String.valueOf(check.charAt(0));
                    }
                    hasValue = false;
                }
                
                // Case for multiple variable decalarations
                if (check.contains(",")) {
                    String names[] = check.split(",");
                    validity = true;
                    for (int i = 0; i < names.length; i++){
                        if(!validName(names[i])){
                            validity = false;
                            break;
                        }
                            
                    }
                
                // Single variable declaration
                } else {
                    validity = validName(check); 
                }
                
                // value assignemnt check  
                if (hasValue && validity){
                    valueCheck(token[0], token[token.length - 1]);
                }
            }
        }
        return validity;
    }
    
    public static void main(String[] args) {
        // Variables to be used 
        int testCases;
        int holder = 0;
        char ptr;
        
        try {
            // File Reading
	    File file = new File(FILENAME);
	    FileReader fileReader = new FileReader(file);
	    BufferedReader buffReader = new BufferedReader(fileReader);

	    String line;
            testCases = Integer.parseInt(buffReader.readLine());
          
            // Reading a file line by line until all requested test cases have been evaluated
	    for (int i = 0 ; i < testCases ; i++){
                line = buffReader.readLine();
                ptr = line.charAt(holder);
                int ctr = 0;
                while(true){
                    if(ptr == ';')
                        // enter declare
                    
                    if (ptr == '{')
                        // enter define
                    
                    if (ctr == line.length() - 1){
                        line = buffReader.readLine();
                        break;
                    }
                    // Traversal 
                    ctr++; 
                    ptr = line.charAt(ctr);
                    
                }
                
            }
            
        } catch (IOException e) {
		e.printStackTrace();
	} 
    }
}
