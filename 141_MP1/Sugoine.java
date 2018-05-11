// Collaboration with :
//          *Cadavos
//          *Digamon
//          *Encabo
//          *Lapuz

package pkg141_mps;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by User on 19/02/2018.
 */
public class Sugoine {
    String filename;

    FileReader fr;
    BufferedReader br;

    String lineReader;
    String paramHolder;
    String lastname = "lapuz";
    String mpout = "1.out";

    ArrayList<String> container;
    ArrayList<String> validNames = new ArrayList();                              // Storage of valid varible names for redundancy
    ArrayList<String> reservedWords = new ArrayList();
    ArrayList<String> dataTypes = new ArrayList();
    ArrayList<String> answers;
    ArrayList<String> insideCurlies = new ArrayList<>();

    boolean isVarDec = false;
    boolean isFuncDec = false;
    boolean isFuncDef = false;

    int count = 0;

    public Sugoine() {
        answers = new ArrayList<String>();

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
        reservedWords.add("int,");
        reservedWords.add("double,");
        reservedWords.add("float,");
        reservedWords.add("char,");
        reservedWords.add("void,");
        reservedWords.add("if,");
        reservedWords.add("else,");
        reservedWords.add("for,");
        reservedWords.add("return,");
        reservedWords.add("while,");
        reservedWords.add("null,");

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter filename: ");
        filename = sc.next();

        fileRead(filename);

        for(int ctr = 0; ctr < count; ctr++) {

            ArrayList<String> tc = new ArrayList();

            String testcase1 = container.get(ctr);
            System.out.println("Test case number: " + (ctr+1));

            String tab = "\t";

            if(testcase1.contains(tab)) {
                testcase1 = testcase1.replaceAll(tab, " ");
            }


            StringTokenizer st = new StringTokenizer(testcase1, " ");

            while(st.hasMoreTokens()){
                tc.add(st.nextToken());
            }

            int temporary = start(tc);

        }
        fileWrite();
    }

    int start(ArrayList<String> tc) {
        //0 kay invalid var dec
        //1 kay valid var dec
        //2 kay valid func dec

        int varChecker = 1;

        ArrayList<String> insideParenthesis = new ArrayList();

        if(tc.get(0).equals("int") || tc.get(0).equals("char") || tc.get(0).equals("double") || tc.get(0).equals("float")) {
            String varName = "";
            for(int i = 1; i < tc.size(); i++) {
                varChecker = varCheck(tc.get(i), varName);
                varName = tc.get(i);
                if(varChecker == 0) {
                    answers.add("INVALID VARIABLE DECLARATION");
                    System.out.println("INVALID VARIABLE DECLARATION");
                    return 0;
                }
                if(varChecker == 2) {
                    String tmp ;
                    if(paramHolder.length() > 0){
                        int holder = 0;
                        for(int j = 0; j < paramHolder.length(); j++) {
                            if(paramHolder.charAt(j) == ',') {
                                insideParenthesis.add(paramHolder.substring(holder, j));
                                holder = j;
                            }
                        }
                    } else {
                        for (int j = i; j < tc.size(); j++) {
                            //add all tokens nga naa sulod sa parenthesis.
                            tmp = tc.get(j);
                            if (tmp.contains(")")) {
                                tmp = tmp.substring(0, tmp.length() - 1);
                            } else if (tmp.equals(")")) {
                                break;
                            }
                            insideParenthesis.add(tmp);
                        }
                    }
                    return checkFuncDec(insideParenthesis);
                }
                if(varName.endsWith(",")) {
                    validNames.add(varName.substring(0,varName.length() - 1));
                }
                if(varName.endsWith(";")) {
                    answers.add("VALID VARIABLE DECLARATION");
                    System.out.println("VALID VARIABLE DECLARATION");
                    return 1;
                }
                if(varName.contains("{")) {
                    answers.add("VALID FUNCTION DEFINITION");
                    return 9;
                }
            }
        } else if(tc.get(0).equals("void")){
            //check para its own function declaration or definition
        } else {
            //wrong jud iyang vartype
            String holder = "";
            for(int i = tc.size() - 1; i > 0; i--){
                holder = tc.get(i);
                if(holder.contains("}")){
                    answers.add("INVALID FUNCTION DEFINITION");
                    isFuncDef = true;
                    return 5;
                }
                if(holder.contains(")")){
                    answers.add("INVALID FUNCTION DECLARATION");
                    isFuncDec = true;
                    return 4;
                }
                if(!isFuncDef && !isFuncDec) {
                    answers.add("INVALID VARIABLE DECLARATION");
                    return 0;
                }
            }
        }
        return 0;
    }

    void fileRead(String filename){
        boolean isFirst = true;

        String temp = "";

        try {
            // FileReader reads text files in the default encoding.
            fr = new FileReader(filename);

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
    }

    void fileWrite() {
        try {
            String fileout = lastname + mpout;
            FileWriter fw = new FileWriter(fileout);
            BufferedWriter bw = new BufferedWriter(fw);
            System.out.println(answers);
            for(int i = 0; i < answers.size(); i++) {
                bw.write(answers.get(i));
                bw.newLine();
            }
            bw.close();
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + "mpa1.in" + "'");
        }
    }

    int checkFuncDec(ArrayList<String> insideParenthesis) {

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
        return 0;
    }

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

    int varCheck(String str, String str2) {
        // 0 is invalid
        // 1 is valid
        // 2 is function siya
        System.out.println(str + " " + str2);
        if(str.equals("=")){                //check if equals
            return checkEquals(str,str2);
        }
        if(str2.endsWith(",")) {
            //if the varname before has comma, check the valid names
            return checkValidNames(str);
        }
        if(!str.endsWith(",") && !str2.endsWith(",")) {
            if (!str2.equals("=") && !str.equals("=")) {
                return 0;
            }
        }
        if(str2.equals("=")) {
            return checkReservedWords(str);
        }
        if(str.endsWith(";") || str.endsWith(",")){
            if(str.contains("(")){
                int parStart = str.indexOf('(');
                paramHolder = str.substring(parStart, str.length() - 1);
                System.out.println("hello " + paramHolder);
                str = str.substring(0, parStart - 1);
                System.out.println("hi " + str);
                if(checkValidNames(str) == 1){
                    return 2;
                }
            }
            str = str.substring(0,str.length() - 1);
        }
        if(str.charAt(0) == '('){
            return 2;
        }
        return varNameChecker(str);
    }

    int checkReservedWords(String str) {
        for(int i = 0; i < reservedWords.size(); i++){
            if(str.equals(reservedWords.get(i))) {
                //invalid varname
                return 0;
            }
        }
        return 1;
    }

    int checkValidNames(String str){
        for (int i = 0; i < validNames.size(); i++) {
            if(str.equals(validNames.get(i))) {
                System.out.println("just here");
                return 0;
            }
        }
        return 1;
    }

    int checkEquals(String str, String str2) {
        if(str2.endsWith(",")) {
            return 0;
        }
        return 1;
    }

    int varNameChecker(String str) {
        if (Character.isAlphabetic(str.charAt(0)) || str.charAt(0) == '_' || str.charAt(0) == '$') {
            //valid varname
            return 1;
        } else {
            return checkReservedWords(str);
        }
    }

    public static void main(String[] args) {
        Sugoine a = new Sugoine();
    }
}
