package com.jordic.bathtubtest;

import com.jordic.bathtubtest.util.ConversionUtils;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by J on 03/01/2017.
 */

public class ConversionUtilsTest {

    @Test
    public void floatRoundTest()
    {
        assertEquals(0f, ConversionUtils.roundFloat(0,0));
        assertEquals(2.39f,ConversionUtils.roundFloat(2.39f,2));
        assertEquals(2.39f,ConversionUtils.roundFloat(2.386f,2));
    }

    @Test
    public void compareTest()
    {
        //EQUAL
        assertTrue(ConversionUtils.equalsFloat(2.53f,2.53f));
        assertFalse(ConversionUtils.equalsFloat(2.52782f,2.53f));

        //GREATHER OR EQUAL
        assertFalse(ConversionUtils.greatherThanOrEqualFloat(2.52782f,2.53f));
        assertTrue(ConversionUtils.greatherThanOrEqualFloat(2.5301f,2.53f));
        assertTrue(ConversionUtils.greatherThanOrEqualFloat(2.53f,2.53f));

        //LOWER OR EQUAL
        assertTrue(ConversionUtils.lowerThanOrEqualFloat(2.52f,2.53f));
        assertFalse(ConversionUtils.lowerThanOrEqualFloat(2.5301f,2.53f));
        assertTrue(ConversionUtils.lowerThanOrEqualFloat(2.53f,2.53f));
    }


}
