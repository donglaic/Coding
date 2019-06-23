package com.delay;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class HelloMainSimpleTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public HelloMainSimpleTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( HelloMainSimpleTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
		HelloMainSimple.run();
        assertTrue( true );
    }
}
