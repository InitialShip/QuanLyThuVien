package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.junit.Test;

import main.service.LoginService;

public class LoginTest {
    @Test
    public void testIsUserAccountExisted() throws SQLException{
        assertEquals(LoginService.isAccountExisted("CS1960008"), true);
        assertEquals(LoginService.isAccountExisted("CS1962008"), false);
    }
    @Test
    public void testIsAdminAccountExisted() throws SQLException{
        assertEquals(LoginService.isAdminAccountExisted("ADMIN007"), true);
        assertEquals(LoginService.isAdminAccountExisted("CS1960008"), false);
    }
    @Test
    public void testIsUserPasswordCorrect() throws NoSuchAlgorithmException, SQLException{
        assertEquals(LoginService.isPasswordCorrect("CS1960008", "Dab12345"), true);
        assertEquals(LoginService.isPasswordCorrect("CS1960008", "aab12345"), false);
        assertEquals(LoginService.isPasswordCorrect("Cs1960008", "Dab12345"), false);
    }
    @Test
    public void testIsAdminPasswordCorrect() throws NoSuchAlgorithmException, SQLException{
        assertEquals(LoginService.isAdminPasswordCorrect("ADMIN007", "Admin007"), true);
        assertEquals(LoginService.isAdminPasswordCorrect("ADMIN007", "ADmin007"), false);
        assertEquals(LoginService.isAdminPasswordCorrect("AdMIN007", "Admin007"), false);
    }
}
