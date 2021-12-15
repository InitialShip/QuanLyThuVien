package main.utility;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import main.App;

public class Utils {
    //#UNIT TEST
    /*
    * Is a string follow a set pattern? 
    */
    public static boolean isValid(String input, String regex){
        Pattern pattern = Pattern.compile(regex);
        if (input == null)
            return false;
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
    /*
    * Validating user Id and user password from inputs
    */
    public static boolean isUserIdValid(String input){
        return isValid(input, "^(?=[A-Za-z0-9])(?=\\S+$).{8,20}$");
    }
    public static boolean isUserPasswordValid(String input){
        return isValid(input, "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$");
    }
    /* 
    * Get a SHA-256 hash of a given string
    */
    public static String getEncryted(String input) throws NoSuchAlgorithmException{
        return toHexString(getSHA(input));
    }
    private static byte[] getSHA(String input) throws NoSuchAlgorithmException{ 
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256"); 
  
        return messageDigest.digest(input.getBytes(StandardCharsets.UTF_8)); 
    }
    private static String toHexString(byte[] hash){
        BigInteger number = new BigInteger(1, hash); 
  
        StringBuilder hexString = new StringBuilder(number.toString(16)); 
  
        while (hexString.length() < 32){ 
            hexString.insert(0, '0'); 
        } 
  
        return hexString.toString(); 
    }

    /*
    * Switch to new scene relative to App class
    */
    public static void switchScene(ActionEvent event, String fxml) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml+".fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /*
    *  Create an alert box
    */
    public static Alert getAlertBox(String message, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setContentText(message);

        return alert;
    }
}
