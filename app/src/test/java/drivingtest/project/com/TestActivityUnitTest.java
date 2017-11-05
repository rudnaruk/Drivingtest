package drivingtest.project.com;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by piyaponf on 11/5/2017 AD.
 */

public class TestActivityUnitTest {
    @Test
    public void intToTwoDigitsStringWhenZero() throws Exception {
        TestActivity a = new TestActivity();
        assertEquals(a.intToTwoDigitsString(0), "00");
    }
    @Test
    public void intToTwoDigitsStringWhenLessThanTen() throws Exception {
        TestActivity a = new TestActivity();
        assertEquals(a.intToTwoDigitsString(9), "09");
    }
    @Test
    public void intToTwoDigitsStringWhenMoreThanTen() throws Exception {
        TestActivity a = new TestActivity();
        assertEquals(a.intToTwoDigitsString(60), "60");
    }

}
