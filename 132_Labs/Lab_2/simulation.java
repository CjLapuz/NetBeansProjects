package Lab_2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jeremy
 */
public class simulation {
    
    // Supposed destination register where data will be transferred
    static List<String> MDR = new ArrayList();
    static List<List> register = new ArrayList();
    static String MAR;
    
    // Object to be called to execute transfer
    public simulation(){
        writeData();
    }
    
    // Generates a register address
    public String generateAddrs() {
        String addrs = "";
        Random rand = new Random();
        for (int i = 0; i < 5; i++){
            int n = rand.nextInt(15);
            if (n > 9){
                addrs += getHex(n);
            } else {
                addrs += n;
            }
        }
        addrs += 'h';
        return addrs ;
    }
    
    // Gets the hexadecimal value of a two digit number
    public char getHex(int num){
        switch (num) {
            case 11:
                return 'B';
            case 12:
                return 'C';
            case 13:
                return 'D';
            case 14:
                return 'E';
            case 15:
                return 'F';
            default:
                return 'A';
        }
    }
    
    // Converts a text to each individual ascii values
    // Each value is stored in an ArrayList and displayed
    public void convertToAscii(String txt){
        List hold = new ArrayList();
        char arr[] = txt.toCharArray();
        
        // Convert each individual character to their decimal equivalent
        for (int i = 0; i < arr.length; i++){
            hold.add( ((int) arr[i]) );
        }
        register.add(hold);
        System.out.println(hold);
    }
    
    // reads the text file and adds the data to the Array list
    public void readFile() {
        try {
            FileReader fileReader = new FileReader("C://Users//Jeremy//Documents//NetBeansProjects//132_LABS//src//Lab_2//test.txt");
            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while(line != null) {
                MDR.add(line);
                line = bufferedReader.readLine();
            }   
            // Always close files
            bufferedReader.close();
        } catch(FileNotFoundException ex) {
            ex.printStackTrace();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    // Adds a delay
    public void delay(int time){
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException ex) {
            Logger.getLogger(simulation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Performs the basic steps for writing data into a register
    public void writeData() {
        // Retrieve data and storing it in the MDR
        delay(2);
        System.out.print("Loading word into the MDR");
        readFile();
        delay(2); System.out.print(" ."); delay(1); System.out.print(" ."); delay(1); System.out.print(" .");
        delay(2); System.out.print(" ."); delay(1); System.out.println(" ."); delay(1);
        System.out.println("All contents have been loaded into the MDR!\n");
        delay(2);
        
        // Generate a register address and storing it in the MAR
        MAR = generateAddrs();
        System.out.println("Address of Register: " + MAR); delay(2);
        System.out.print("Loading address of the register into the MAR");
        delay(2); System.out.print(" ."); delay(1); System.out.print(" ."); delay(1); System.out.println(" ."); delay(2);
        System.out.println("Load Complete!\n"); 
        delay(2);
        
        // Execution of Write Operation
        System.out.println("<! Executing write operation !>\n"); delay(2);
        System.out.print("Retrieving address from the MAR");
        delay(2); System.out.print(" ."); delay(1); System.out.print(" ."); delay(1); System.out.println(" ."); delay(1);
        System.out.println("Successful Retrieval!"); delay(1);
        System.out.print("Retrieving data from the MDR");
        delay(2); System.out.print(" ."); delay(1); System.out.print(" ."); delay(1); System.out.println(" ."); delay(1);
        System.out.println("Successful Retrieval!\n"); delay(1);
        System.out.println("Converting data to ASCII and storing transferring it into the address \n"); delay(2);
        for (int i = 0 ; i < MDR.size(); i++){
            convertToAscii(MDR.get(i));
            delay(2);
            System.out.println("Successful Transfer!");
            delay(1);
        }
        System.out.println("\nContents of Register with address " + MAR);
        System.out.println(register);
        System.out.println("\n\t\t\t\t\t\tTRANSFER COMPLETE, END OF SIMULATION!");     
    }
    
    // Main class //
    public static void main (String[] args){                
        simulation d = new simulation(); 
    }
}
