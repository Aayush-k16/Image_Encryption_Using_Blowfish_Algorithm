/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Decoder;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Separator;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Aayush
 */

class Encrypt{
    String encodedKey;
    KeyGenerator keyGenerator = null;
    SecretKey secretKey = null;
    Cipher cipher = null;
    SecretKey key = null;

    public Encrypt() {
        try {
            /**
             * Create a Blowfish key
             */
            keyGenerator = KeyGenerator.getInstance("Blowfish");
            SecureRandom secRandom = new SecureRandom();
            keyGenerator.init(secRandom);
            secretKey = keyGenerator.generateKey();
            System.out.println(secretKey);

            /**
             * Create an instance of cipher mentioning the name of algorithm
             *     - Blowfish
             */
            cipher = Cipher.getInstance("Blowfish");
        } catch (NoSuchPaddingException ex) {
            System.out.println(ex);
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex);
        }
    }
    
    public String base(){
        encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println(encodedKey);
        return encodedKey;
    }
    
     public SecretKey base_decrypt(String a5){
        Decoder decoder = Base64.getDecoder();
        byte[] decodedKey = Base64.getDecoder().decode(a5);
        SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "Blowfish");
        
        return key;
    }
    
    public void encrypt(String srcPath, String destPath, String a1, String a3) {
        
        String st= a1;
        File rawFile = new File("/Users/aayushkumar/Desktop/Image/"+st+a3);
        File encryptedFile = new File("/Users/aayushkumar/Desktop/Image/"+"encryptedFile");
        InputStream inStream = null;
        OutputStream outStream = null;
        try {
            /**
             * Initialize the cipher for encryption
             */
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            //System.out.println(secretKey);
            /**
             * Initialize input and output streams
             */
            inStream = new FileInputStream(rawFile);
            outStream = new FileOutputStream(encryptedFile);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inStream.read(buffer)) > 0) {
                outStream.write(cipher.update(buffer, 0, len));
                outStream.flush();
            }
            outStream.write(cipher.doFinal());
            inStream.close();
            outStream.close();
        } catch (IllegalBlockSizeException ex) {
            System.out.println(ex);
        } catch (BadPaddingException ex) {
            System.out.println(ex);
        } catch (InvalidKeyException ex) {
            System.out.println(ex);
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    public void decrypt(String srcPath, String destPath, String a3, String a4, SecretKey key) {
   
        File encryptedFile = new File("/Users/aayushkumar/Desktop/Image/"+a4);
        File decryptedFile = new File("/Users/aayushkumar/Desktop/Image/"+"decryptedFile"+a3+".jpeg");
        InputStream inStream = null;
        OutputStream outStream = null;
        try {
            /**
             * Initialize the cipher for decryption
             */
            cipher.init(Cipher.DECRYPT_MODE, key);
            /**
             * Initialize input and output streams
             */
            inStream = new FileInputStream(encryptedFile);
            outStream = new FileOutputStream(decryptedFile);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inStream.read(buffer)) > 0) {
                outStream.write(cipher.update(buffer, 0, len));
                outStream.flush();
            }
            outStream.write(cipher.doFinal());
            inStream.close();
            outStream.close();
        } catch (IllegalBlockSizeException ex) {
            System.out.println(ex);
        } catch (BadPaddingException ex) {
            System.out.println(ex);
        } catch (InvalidKeyException ex) {
            System.out.println(ex);
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

}
public class app1 extends Application {
    String s1;
    
    @Override
    public void start(Stage stage) {                
        
        stage.setTitle("Image Encryption");
        
        Text t = new Text("Welcome to Image Encryption");
        t.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 37));  
        t.setFill(Color.ALICEBLUE); 
        
        Text tt = new Text("Steps to run the program: ");
        tt.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 24));   
        tt.setFill(Color.WHITE); 
        
        Text l = new Text();
        l.setText("1. Enter the name of the Image file you want\n     to encrypt or decrypt in the Text Box below.\n\n"
                + "2. Choose the file type from the drop down.\n\n" + "3. For Encyption, click on Encrypt.\n\n" 
                + "4. After Encryption, click on Get Key to know the\n    Secret Code.\n\n"
                + "5. Check your Folder, you will find the encrypted\n    image in the name of encryptedFile\n\n" 
                + "6. For Decryption, enter the name of encrypted\n    file and fill in the secret key and then\n    "
                + "click on decrypt to find the file in the folder\n");
        l.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 13)); 
        l.setFill(Color.WHITE); 
        
        Text t1=new Text("Input (file name):");
        t1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
        t1.setFill(Color.WHITE); 
        
        Text t2=new Text("Key for decryption:");
        t2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
        t2.setFill(Color.WHITE); 
        
        TextField tF1=new TextField();
        tF1.setMaxWidth(200);
        
        TextField tF2=new TextField();
        tF2.setMaxWidth(200);
        
        Text label = new Text ("Select Desired File type: ");
        label.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
        label.setFill(Color.WHITE); 
        
        ComboBox comboBox = new ComboBox();
        comboBox.getItems().add("");
        comboBox.getItems().add(".jpg");
        comboBox.getItems().add(".jpeg");
        comboBox.getItems().add(".png");
        comboBox.getItems().add(".tiff");
        comboBox.getItems().add(".gif");
        comboBox.setValue(".jpg");
        
        Button btn = new Button();
        btn.setText("Encrypt");
        btn.setStyle("-fx-background-color: green;" + "-fx-text-fill: white;" + "fx-font-family: verdana;" + "fx-font-weight: bold");
        
        Button btnn = new Button();
        btnn.setText("Decrypt");
        btnn.setStyle("-fx-background-color: green;" + "-fx-text-fill: white;" + "fx-font-family: verdana;" + "fx-font-weight: bold");
        
        Button btn1 = new Button();
        btn1.setText("Get Key");
        btn1.setStyle("-fx-background-color: green;" + "-fx-text-fill: white;" + "fx-font-family: verdana;" + "fx-font-weight: bold");
        
        
        Separator separator = new Separator(Orientation.HORIZONTAL);
        separator.setMinWidth(500);
        separator.setMaxWidth(500);
   
        
        EventHandler<javafx.scene.input.MouseEvent> eventhandler = new EventHandler<javafx.scene.input.MouseEvent>(){
            @Override
            public void handle(javafx.scene.input.MouseEvent e){

                String a1=tF1.getText();
                Object a2=comboBox.getValue();
                String a3 = a2.toString();
                String fileToEncrypt = a1;
                String encryptedFile = "encryptedFile";
                String directoryPath = "/Users/aayushkumar/Desktop/Image/";
                Encrypt Encrypt = new Encrypt();
                System.out.println("Starting Encryption...");
                Encrypt.encrypt(directoryPath + fileToEncrypt,
                        directoryPath + encryptedFile,a1,a3);
                System.out.println("Encryption completed...");
                s1 = Encrypt.base(); 
                System.out.println(s1);
            }
        };
        
         EventHandler<javafx.scene.input.MouseEvent> eventhandler2 = new EventHandler<javafx.scene.input.MouseEvent>(){
            @Override
            public void handle(javafx.scene.input.MouseEvent e){
                String a4=tF1.getText();
                String a5 = tF2.getText();
                Object a2=comboBox.getValue();
                String a3 = a2.toString();
                String encryptedFile = a4+a3;
                String decryptedFile = "decryptedFile"+a3;
                String directoryPath = "/Users/aayushkumar/Desktop/Image/";
                System.out.println("Starting Decryption...");
                Encrypt Encrypt = new Encrypt();
                SecretKey key = Encrypt.base_decrypt(a5);
                Encrypt.decrypt(directoryPath + encryptedFile,
                        directoryPath + decryptedFile,a3,a4,key);
                System.out.println("Decryption completed...");    
            }
         };
        
        
        EventHandler<javafx.scene.input.MouseEvent> eventhandler1 = new EventHandler<javafx.scene.input.MouseEvent>(){
            @Override
            public void handle(javafx.scene.input.MouseEvent e){
                Encrypt b1 = new Encrypt();
                //String s1 = b1.base();
                Text tt1 = new Text("Your Secret key is: "+s1);
                tt1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
                tt1.setFill(Color.WHITE); 
                
                Button btn3 = new Button();
                btn3.setText("Exit");
                btn3.setStyle("-fx-background-color: green;" + "-fx-text-fill: white;" + "fx-font-family: verdana;" + "fx-font-weight: bold");
                
                EventHandler<javafx.scene.input.MouseEvent> eventhandler3 = new EventHandler<javafx.scene.input.MouseEvent>(){
                @Override
                public void handle(javafx.scene.input.MouseEvent e){
                    Platform.exit();
                    System.exit(0);
                }
            };
                btn3.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED,eventhandler3);
                
                VBox vbox = new VBox();
                vbox.setStyle("-fx-background-image: url('file:///Users/aayushkumar/Desktop/b.jpeg');" + "-fx-background-repeat: repeat;" +
                "-fx-border-color: black;" + "-fx-padding: 10;" + "-fx-background-position: center center;" +
                "-fx-border-width: 3;" + "-fx-border-style: solid inside;" + 
                "-fx-border-radius: 5;" );
                vbox.setSpacing(20);
                vbox.getChildren().addAll(tt1, btn3);
                vbox.setAlignment(Pos.CENTER);
                Scene scene = new Scene(vbox, 600, 450);

                stage.setScene(scene);
                stage.show();
            }
        };
        
        btn.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED,eventhandler);
        
        btn1.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED,eventhandler1);
        
        btnn.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED,eventhandler2);
        
        
        VBox vbox = new VBox();
        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();
        HBox hbox3 = new HBox();
        HBox hbox4 = new HBox();
        HBox hbox5 = new HBox();
        HBox hbox6 = new HBox();
        HBox hbox7 = new HBox();
        HBox hbox8 = new HBox();
        
        hbox1.setAlignment(Pos.TOP_CENTER);
        hbox2.setAlignment(Pos.CENTER_RIGHT);
        hbox3.setAlignment(Pos.TOP_CENTER);
        hbox4.setAlignment(Pos.TOP_CENTER);
        hbox5.setAlignment(Pos.CENTER_RIGHT);
        hbox6.setAlignment(Pos.TOP_CENTER);
        hbox7.setAlignment(Pos.TOP_CENTER);
        hbox8.setAlignment(Pos.TOP_CENTER);
        
        hbox1.getChildren().addAll(t);
        hbox2.getChildren().addAll(tt);
        hbox3.getChildren().addAll(t1,tF1,label);
        hbox4.getChildren().addAll(label,comboBox);
        hbox5.getChildren().addAll(l);
        hbox6.getChildren().addAll(separator);
        hbox7.getChildren().addAll(btn,btnn);
        hbox8.getChildren().addAll(t2,tF2);
        
        hbox2.setMinWidth(110);
        hbox2.setMaxWidth(110);
        
        hbox5.setMinWidth(600);
        hbox5.setMaxWidth(600);
        
        hbox7.setSpacing(100);
        
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(hbox1,hbox2,hbox5,hbox3,hbox8,hbox4,hbox7,btn1,hbox6);
        vbox.setSpacing(20);
        vbox.setStyle("-fx-background-image: url('file:///Users/aayushkumar/Desktop/b.jpeg');" + "-fx-background-repeat: repeat;" +
                "-fx-border-color: black;" + "-fx-padding: 10;" + "-fx-background-position: center center;" +
                "-fx-border-width: 3;" + "-fx-border-style: solid inside;" + 
                "-fx-border-radius: 5;" );
        
        
        Scene scene = new Scene(vbox, 700, 750);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}