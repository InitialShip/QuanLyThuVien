package main.utility;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
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
}
