package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import main.utility.Utils;

public class UtilsTest {
    @Test
    public void testIsValid(){
        assertEquals(Utils.isMatched("ABC", "^([A-Z]*\\.?)$"), true);
        assertEquals(Utils.isMatched("ABC  ABC", "^([A-Z]*\\.?)$"), false);
        assertEquals(Utils.isMatched("abc AB", "^([A-Z]*\\.?)$"), false);
        assertEquals(Utils.isMatched("ABC124", "^([A-Z0-9]*\\.?)$"), true);
        assertEquals(Utils.isMatched("abc", "^([a-z]*\\.?)$"), true);
        assertEquals(Utils.isMatched("ABC124", "^([a-z0-9]*\\.?)$"), false);
        assertEquals(Utils.isMatched("abcde", "^([a-z0-9]*\\.?)$"), true);
    }
    @Test
    public void testIsUserIdValid(){
        assertEquals(Utils.isUserIdValid("            "), false);
        assertEquals(Utils.isUserIdValid("ABCDEFGHIJ"), true);
        assertEquals(Utils.isUserIdValid("ABCDEF HIJ"), false);
        assertEquals(Utils.isUserIdValid("ABC"), false);
        assertEquals(Utils.isUserIdValid("ABeHIJ"), false);
        assertEquals(Utils.isUserIdValid("AB12345678GHIJ"), true);
        assertEquals(Utils.isUserIdValid("!231"), false);
        assertEquals(Utils.isUserIdValid("1234567890"), true);
        assertEquals(Utils.isUserIdValid("abce1356778"), false);
        assertEquals(Utils.isUserIdValid("abC123457"), false);
        assertEquals(Utils.isUserIdValid("CS18838593"), true);
        assertEquals(Utils.isUserIdValid("Cs18838593"), false);
        assertEquals(Utils.isUserIdValid("abcedfge"), false);
    }
    @Test
    public void testIsPassWordValid(){
        assertEquals(Utils.isUserPasswordValid("ABCde1234"), true);
        assertEquals(Utils.isUserPasswordValid("Ab1!!!!!!"), true);
        assertEquals(Utils.isUserPasswordValid("aBc12345"), true);
        assertEquals(Utils.isUserPasswordValid("de123AE4"), true);
        assertEquals(Utils.isUserPasswordValid("          "), false);
        assertEquals(Utils.isUserPasswordValid("aBc1"), false);
        assertEquals(Utils.isUserPasswordValid("abcdefth"), false);
        assertEquals(Utils.isUserPasswordValid("!!!!!!!!!@~"), false);
        assertEquals(Utils.isUserPasswordValid("abc Ab123"), false);
        assertEquals(Utils.isUserPasswordValid("ABCDEFGH"), false);
        assertEquals(Utils.isUserPasswordValid("123456789"), false);
        assertEquals(Utils.isUserPasswordValid("ABC12345"), false);
        assertEquals(Utils.isUserPasswordValid("abceg1235"), false);
    }
    @Test
    public void testReverseList(){
        List<String> listA = new ArrayList<>();
        listA.add("a");
        listA.add("b");
        listA.add("c");
        listA.add("d");
        List<String> listB = new ArrayList<>();
        listB.add("d");
        listB.add("c");
        listB.add("b");
        listB.add("a");
        Utils.revlist(listB);
        assertEquals(listA, listB);
    }
    @Test
    public void testDaysDiff(){
        assertEquals(Utils.getDaysDiff(LocalDate.now(), LocalDate.now()), 0);
        assertEquals(Utils.getDaysDiff(LocalDate.now(), LocalDate.now().plusDays(30)), 30);
        assertEquals(Utils.getDaysDiff(LocalDate.now(), LocalDate.now().minusDays(20)), -20);
        assertEquals(Utils.getDaysDiff(LocalDate.of(2021, 1, 1), LocalDate.of(2022, 1, 1)), 365);
        assertEquals(Utils.getDaysDiff(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 31)), 30);
        assertEquals(Utils.getDaysDiff(LocalDate.of(2021, 3, 1), LocalDate.of(2021,4,1)), 31);
    }
    @Test
    public void testMonthsDiff(){
        assertEquals(Utils.getMonthDiff(LocalDate.now(), LocalDate.now()), 0);
        assertEquals(Utils.getMonthDiff(LocalDate.now(), LocalDate.now().plusMonths(20)), 20);
        assertEquals(Utils.getMonthDiff(LocalDate.now(), LocalDate.now().minusMonths(30)), -30);
        assertEquals(Utils.getMonthDiff(LocalDate.of(2021, 1, 1), LocalDate.of(2022, 1, 1)), 12);
        assertEquals(Utils.getMonthDiff(LocalDate.of(2021, 5, 1), LocalDate.of(2021, 8, 1)), 3);
        assertEquals(Utils.getMonthDiff(LocalDate.of(2021, 1, 1), LocalDate.of(2019, 1, 1)), -24);
    }
    @Test
    public void testYearsDiff(){
        assertEquals(Utils.getYearDiff(LocalDate.now(), LocalDate.now()), 0);
        assertEquals(Utils.getYearDiff(LocalDate.now(), LocalDate.now().plusMonths(24)), 2);
        assertEquals(Utils.getYearDiff(LocalDate.now(), LocalDate.now().plusYears(10)), 10);
        assertEquals(Utils.getYearDiff(LocalDate.of(2024, 8, 1), LocalDate.of(2021, 8, 1)), -3);
        assertEquals(Utils.getYearDiff(LocalDate.of(2021, 12, 1), LocalDate.of(2025, 12, 1)), 4);
        assertEquals(Utils.getYearDiff(LocalDate.of(2021, 5, 1), LocalDate.of(2000, 5, 1)), -21);
    }
}
