package main.utility;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.Alert;

public class Utils {
    //#UNIT TEST
    /*
    * Is a string follow a set pattern? 
    */
    public static boolean isMatched(String input, String regex){
        Pattern pattern = Pattern.compile(regex);
        if (input == null)
            return false;
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
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
    *  Create an alert box
    */
    public static Alert getAlertBox(String message, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setContentText(message);

        return alert;
    }
    /*
    * Reverse oder of a list
    */
    public static <T> void revlist(List<T> list){
        if (list.size() <= 1 || list == null)
            return;
        T value = list.remove(0);
        revlist(list);
        list.add(value);
    }
}
