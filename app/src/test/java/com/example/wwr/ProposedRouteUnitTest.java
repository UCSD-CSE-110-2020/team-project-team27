package com.example.wwr;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ProposedRouteUnitTest {
    @Test
    public void getAttendee_isCorrect() {
        ProposedRoute testRoute = new ProposedRoute("testroute", "UCSD", "none", "Bill Griswold", "2/20/2020",
                "1:05", "false", "test@ucsd.edu", "0", "Birdy", "Alex Garzander");
        assertEquals("Bill Griswold", testRoute.getAttendee());
    }
    @Test
    public void getRejected_isCorrect() {
        ProposedRoute testRoute = new ProposedRoute("testroute", "UCSD", "none", "Bill Griswold", "2/20/2020",
                "1:05", "false", "test@ucsd.edu", "0", "Birdy", "Alex Garzander");
        assertEquals("Alex Garzander", testRoute.getRejected());
    }
    @Test
    public void getOwnerColor_isCorrect() {
        ProposedRoute testRoute = new ProposedRoute("testroute", "UCSD", "none", "Bill Griswold", "2/20/2020",
                "1:05", "false", "test@ucsd.edu", "0", "Birdy", "Alex Garzander");
        assertEquals("0", testRoute.getOwnerColor());
    }
    @Test
    public void getOwnerName_isCorrect() {
        ProposedRoute testRoute = new ProposedRoute("testroute", "UCSD", "none", "Bill Griswold", "2/20/2020",
                "1:05", "false", "test@ucsd.edu", "0", "Birdy", "Alex Garzander");
        assertEquals("Birdy", testRoute.getOwnerName());
    }
    @Test
    public void updateAttendee_isCorrect() {
        ProposedRoute testRoute = new ProposedRoute("testroute", "UCSD", "none", "Bill Griswold,", "2/20/2020",
                "1:05", "false", "test@ucsd.edu", "0", "Birdy", "Alex Garzander\n(bad time),");
        String [] testarr = testRoute.updateAttendee("Alex Garzander", testRoute.getAttendee(), testRoute.getRejected());
        assertEquals("Bill Griswold,Alex Garzander,", testarr[0]);
        assertEquals("", testarr[1]);
    }
    @Test
    public void updateReject_isCorrect() {
        ProposedRoute testRoute = new ProposedRoute("testroute", "UCSD", "none", "Bill Griswold,", "2/20/2020",
                "1:05", "false", "test@ucsd.edu", "0", "Birdy", "Alex Garzander"+"\n(bad time),");
        String [] testarr = testRoute.updateReject("Alex Garzander", testRoute.getAttendee(), testRoute.getRejected(), "\n(not a good route)");
        assertEquals("Bill Griswold,", testarr[0]);
        assertEquals("Alex Garzander"+"\n(not a good route),", testarr[1]);
    }
}