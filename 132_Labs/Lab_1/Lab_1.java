package Lab_1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Scanner;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author Jeremy
 */
public class Lab_1 extends Application{
    
    private final int BUFFER_SIZE = 128000;
    private File soundFile;
    private AudioInputStream audioStream;
    private AudioFormat audioFormat;
    private SourceDataLine sourceLine;
    
    final static File txtFile = new File("C://Users//Jeremy//Documents//NetBeansProjects//132_LABS//src//Lab_1//text2.txt");
    final File audioFile = new File("C://Users//Jeremy//Documents//NetBeansProjects//132_LABS//src//Lab_1//audio.wav");
    
    public Lab_1() {}
    
    public void readTxtFile(){
        try {
            FileReader fileReader = new FileReader(txtFile);
            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while(line != null) {
                System.out.println(line);
                line = bufferedReader.readLine();
            }   
            // Always close files
            bufferedReader.close();
        } catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + txtFile + "'");   
        } catch(IOException ex) {
            System.out.println("Error reading file '"  + txtFile + "'");
        }
    }
    
    public void readAudioFile(){
        try {
            soundFile = new File(audioFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            audioStream = AudioSystem.getAudioInputStream(soundFile);
        } catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }

        audioFormat = audioStream.getFormat();

        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        try {
            sourceLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceLine.open(audioFormat);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        sourceLine.start();

        int nBytesRead = 0;
        byte[] abData = new byte[BUFFER_SIZE];
        while (nBytesRead != -1) {
            try {
                nBytesRead = audioStream.read(abData, 0, abData.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (nBytesRead >= 0) {
                @SuppressWarnings("unused")
                int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
            }
        }

        sourceLine.drain();
        sourceLine.close();
    
    }
    
    // The video player
    @Override
    public void start(Stage primaryStage) {

        StackPane root = new StackPane();

        MediaPlayer player = new MediaPlayer( new Media(getClass().getResource("video.mp4").toExternalForm()));
        MediaView mediaView = new MediaView(player);

        root.getChildren().add( mediaView);

        Scene scene = new Scene(root, 1024, 768);

        primaryStage.setScene(scene);
        primaryStage.show();
        player.play();
        
       
        player.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                primaryStage.close();
            }
        });
    }
    
    /** Get CPU time in nanoseconds. */
    public static long getCpuTime( ) {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
        return bean.isCurrentThreadCpuTimeSupported( ) ?
            bean.getCurrentThreadCpuTime( ) : 0L;
    } 
            
    public static void main (String[] args){
        Lab_1 l1 = new Lab_1();
        
        double startTime = 0, stopTime = 0, time;

        System.out.println("Choose what type of file to read:"
                + "\n 1. Text File  \t\t  2. Audio File  \t\t  3. Video File");
        Scanner sc = new Scanner (System.in);
        int file = Integer.valueOf(sc.next());
        
        System.out.println("\n");
        
        System.out.println("Do you want to get:"
                + "\n 1. Execution time  \t\t  2. CPU Time");
        int timeType = Integer.valueOf(sc.next());
        
        // Text Reading 
        if (file == 1){
            if (timeType == 1) {
                startTime = System.nanoTime();
                l1.readTxtFile();
                stopTime = System.nanoTime();
            } else {
                startTime = getCpuTime();
                l1.readTxtFile();
                stopTime = getCpuTime();
            }
            
        // Audio Reading
        } else if (file == 2){
            if (timeType == 1) {
                startTime = System.nanoTime();
                l1.readAudioFile();
                stopTime = System.nanoTime();
            } else {
                startTime = getCpuTime();
                l1.readAudioFile();
                stopTime = getCpuTime();
            }
        
        // Video Reading
        }else {
            if (timeType == 1) {
                startTime = System.nanoTime();
                Application.launch(args);
                stopTime = System.nanoTime();
            } else {
                startTime = getCpuTime();
                Application.launch(args);
                stopTime = getCpuTime();
            }
        }
        System.out.println("START :" +startTime);
        System.out.println("STOP  :" +stopTime);
        time = (stopTime - startTime);
        System.out.println("Time in nanoseconds : " + (time));
        
        System.exit(0);
    }
    
    
}