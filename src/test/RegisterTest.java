package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import org.junit.Test;

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
    
}
