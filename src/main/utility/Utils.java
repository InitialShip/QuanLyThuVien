package main.utility;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.Alert;

public class Utils {
    public static boolean isUserIdValid(String input){
        return Utils.isMatched(input, "^(?=[A-Za-z0-9])(?=\\S+$).{8,20}$");
    }
    public static boolean isUserPasswordValid(String input){
        return Utils.isMatched(input, "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$");
    }
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
    
    /**
     * Get SHA256 Hash
     * @param input String
     * @return
     * @throws NoSuchAlgorithmException
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

    /**
     * Get an arlert box
     * @param message
     * @param type
     * @return
     */
    public static Alert getAlertBox(String message, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setContentText(message);
        return alert;
    }
    /**
     * Reverse order of a list
     * @param <T>
     * @param list
     */
    public static <T> void revlist(List<T> list){
        if (list.size() <= 1 || list == null)
            return;
        T value = list.remove(0);
        revlist(list);
        list.add(value);
    }
    /**
     * Get days diff
     * @param start
     * @param end
     * @return
     */
    public static long getDaysDiff(LocalDate start, LocalDate end){
        long days = ChronoUnit.DAYS.between(start, end);
        return days;
    }
    /**
     * Get months diff
     * @param start 
     * @param end
     * @return number of months
     */
    public static long getMonthDiff(LocalDate start, LocalDate end){
        long months = ChronoUnit.MONTHS.between(start, end);
        return months;
    }
    /**
     * Get years diff
     * @param start
     * @param end
     * @return
     */
    public static long getYearDiff(LocalDate start, LocalDate end){
        long years = ChronoUnit.YEARS.between(start, end);
        return years;
    }
}
