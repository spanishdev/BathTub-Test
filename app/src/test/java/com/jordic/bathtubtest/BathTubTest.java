package com.jordic.bathtubtest;

import com.jordic.bathtubtest.classes.BathTub;
import com.jordic.bathtubtest.util.ConversionUtils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by J on 01/01/2017.
 */

public class BathTubTest {

    BathTub bathTub;

    @Before
    public void initializeBathTub()
    {
        bathTub=new BathTub();
    }

    @Test
    public void bathTubSwitchHotTapTest()
    {
        assertFalse(bathTub.isHotTapON());

        bathTub.switchHotTap();

        assertTrue(bathTub.isHotTapON());

        bathTub.setHotTapState(false);

        assertFalse(bathTub.isHotTapON());
    }

    @Test
    public void bathTubSwitchColdTapTest()
    {
        assertFalse(bathTub.isColdTapON());

        bathTub.switchColdTap();

        assertTrue(bathTub.isColdTapON());

        bathTub.setColdTapState(false);

        assertFalse(bathTub.isColdTapON());
    }

    @Test
    public void addColdLitersTest()
    {
        assertTrue(bathTub.addColdLiters());

        assertFalse(bathTub.isFull());

        //Test that the sum is equal to capacity
        bathTub.setColdLiters(150-(12/60f));
        assertTrue(bathTub.addColdLiters());

        assertEquals(150f,ConversionUtils.roundFloat(bathTub.getCurrentLiters(),2),0.000001f);
        assertTrue(bathTub.isFull());

        //We do the same with hotLiters added
        bathTub.setHotLiters(105);
        bathTub.setColdLiters(44.9f);
        assertFalse(bathTub.addColdLiters());

        assertEquals(149.9f,ConversionUtils.roundFloat(bathTub.getCurrentLiters(),2),0.000001f);
        assertFalse(bathTub.isFull());
    }

    @Test
    public void addHotLitersTest()
    {
        assertTrue(bathTub.addHotLiters());

        assertFalse(bathTub.isFull());

        //Test that the sum is equal to capacity
        bathTub.setHotLiters(150-(10/60f));
        assertTrue(bathTub.addHotLiters());

        assertEquals(150,bathTub.getCurrentLiters(),0.000001f);
        assertTrue(bathTub.isFull());

        //We do the same with hotLiters added
        bathTub.setColdLiters(105);
        bathTub.setHotLiters(40);
        assertTrue(bathTub.addHotLiters());

        assertEquals(145.17f, ConversionUtils.roundFloat(bathTub.getCurrentLiters(),2),0.000001f);
        assertFalse(bathTub.isFull());
    }

    @Test
    public void temperaturesTest()
    {
        //We set cold temperature to 10 and hot temperature to 50
        bathTub.setTemperatures(10,50);

        //We test that temperatures are correct
        assertEquals(10,bathTub.getColdTemperature());

        assertEquals(50,bathTub.getHotTemperature());

        assertEquals(0,bathTub.getCurrentTemperature());

        //We set cold liters to 10
        bathTub.setColdLiters(10);

        //Temperature will be equal to the cold one, 10 degres
        assertEquals(10,bathTub.getCurrentTemperature(),0.000001f);

        //We try again but with hot water
        bathTub.setColdLiters(0);
        bathTub.setHotLiters(10);

        assertEquals(50,bathTub.getCurrentTemperature(),0.000001f);

        //We add some cold water
        bathTub.setColdLiters(10);

        //Temperature are distributed to half of each, so the temperature will be
        //50% of hot (25) and 50% of cold (5), in total 30º
        assertEquals(30,bathTub.getCurrentTemperature(),0.000001f);

        //RESTART
        bathTub.setColdLiters(0);
        bathTub.setHotLiters(0);

        //We will test adding just like the thread
        bathTub.addHotLiters();

        assertEquals(50,bathTub.getCurrentTemperature(),0.000001f);
    }
}
