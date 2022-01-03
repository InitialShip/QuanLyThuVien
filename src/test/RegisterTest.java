package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import main.mySqlConnector.Connector;
import main.service.LoginService;
import main.service.RegisterService;

public class RegisterTest {
    @Test
    public void testIsFromSchool() throws SQLException{
        assertEquals(RegisterService.isFromSchool("PS4160004"), true);
        assertEquals(RegisterService.isFromSchool("CS1960073"), true);
        assertEquals(RegisterService.isFromSchool("CS4160004"), false);
        assertEquals(RegisterService.isFromSchool("EP0650008"), true);
        assertEquals(RegisterService.isFromSchool("EP065106708"), false);
        assertEquals(RegisterService.isFromSchool("CS06510678"), false);
    }
    //CS1960068 Abc12345
    @Test
    public void testRegister() throws NoSuchAlgorithmException, SQLException{
        RegisterService.register("CS1960068", "Abc12345");
        assertEquals(LoginService.isAccountExisted("CS1960068"), true);
        assertEquals(LoginService.isPasswordCorrect("CS1960068", "Abc12345"), true);
        assertEquals(LoginService.isPasswordCorrect("CS1960068", "aBc12345"), false);
        RegisterService.createIdCard("CS1960068");
        Connector.open();
        Statement statement = Connector.getCnt().createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM library_db.id_card WHERE user_id = 'CS1960068'");
        while(rs.next()){
            assertEquals(rs.getString(1), "CS1960068");
        }
        statement.close();
        Connector.close();
    }
}
