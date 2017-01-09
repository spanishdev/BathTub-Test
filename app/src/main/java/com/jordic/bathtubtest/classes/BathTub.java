package com.jordic.bathtubtest.classes;

import com.jordic.bathtubtest.util.ConversionUtils;

/**
 * Created by Jordi on 31/12/2016.
 *
 * This class stores the data related to the Bathtub like Temperature, Capacity and Liters
 *
 */

public class BathTub {

    public static final int CAPACITY=150; //150 Liters of Capacity

    public static final int COLD_LITERS_PER_MINUTE=12;
    public static final int HOT_LITERS_PER_MINUTE=10;

    //Attributes
    private float hotLiters, coldLiters; //Liters of each temperature
    private int hotTemperature, coldTemperature; //Temperatures of each type
    private boolean isHotTapON,isColdTapON; // They indicate whether hot and cold taps are ON

    /**
     * Default constructor. This initialices sets each temperatures to 0
     */
    public BathTub()
    {
        hotLiters=0;
        coldLiters=0;
        isHotTapON=false;
        isColdTapON=false;
        hotTemperature=0;
        coldTemperature=0;
    }


    public boolean isHotTapON()
    {
        return isHotTapON;
    }

    public boolean isColdTapON()
    {
        return isColdTapON;
    }

    public void setHotTapState(boolean on)
    {
        isHotTapON=on;
    }

    public void setColdTapState(boolean on)
    {
        isColdTapON=on;
    }

    public int getColdTemperature()
    {
        return coldTemperature;
    }

    public int getHotTemperature()
    {
        return hotTemperature;
    }

    public void setHotLiters(float liters)
    {
        hotLiters=liters;
    }

    public void setColdLiters(float liters)
    {
        coldLiters=liters;
    }

    public float getHotLitersPerSecond()
    {
        return HOT_LITERS_PER_MINUTE/60f;
    }

    public float getColdLitersPerSecond()
    {
        return COLD_LITERS_PER_MINUTE/60f;
    }


    /**
     * Changes the Hot Tap state. If it's ON, it is turned OFF and vice-versa.
     * @return The current tap state
     */
    public boolean switchHotTap()
    {
        isHotTapON=!isHotTapON;
        return isHotTapON;
    }

    /**
     * Changes the Cold Tap state. If it's ON, it is turned OFF and vice-versa.
     * @return The current tap state
     */
    public boolean switchColdTap()
    {
        isColdTapON=!isColdTapON;
        return isColdTapON;
    }



    /**
     * Sets the temperatures
     * @param coldTemperature Cold temperature
     * @param hotTemperature Hot temperature
     */
    public void setTemperatures(int coldTemperature, int hotTemperature)
    {
        this.coldTemperature=coldTemperature;
        this.hotTemperature=hotTemperature;
    }

    /**
     * Adds cold liters in case that the Bath won't overflow.
     * The bath are filled with cold water in a ratio of 12 liters per minute, and therefore, 5 per second
     * @return True if bath can be filled, false otherwise
     */
    public synchronized boolean addColdLiters()
    {
        if(!canTurnOnColdTap()) return false;

        coldLiters+=getColdLitersPerSecond();

        //We round the Liters to 2 decimal float
        coldLiters=ConversionUtils.roundFloat(coldLiters,2);
        return true;
    }

    /**
     * Adds hot liters in case that the Bath won't overflow.
     * The bath are filled with hot water in a ratio of 10 liters per minute, and therefore, 6 per second
     * @return True if bath can be filled, false otherwise
     */
    public synchronized boolean addHotLiters()
    {
        if(!canTurnOnHotTap()) return false;

        hotLiters+=getHotLitersPerSecond();

        //We round the Liters to 2 decimal float
        hotLiters=ConversionUtils.roundFloat(hotLiters,2);
        return true;
    }

    /**
     * Temperature calculation will be based on temperature percentages.
     * The percentages are taken based on the overall water which is inside the bath.
     * For instance, if we have 10 cold liters, and 20 hot liters, we have 33% of cold water and 66% of hot water
     * The result will be round to an integer
     * @return The current temperature
     */
    public int getCurrentTemperature()
    {
        //We want to avoid dividing by 0, so if the total liters are 0, temperature will be 0 as well
        if(getCurrentLiters()==0) return 0;

        //First we get the percentages, but without multiplying x 100, because these will be the factors
        //where the hot and cold temperatures will be multiplied by
        float hotPercentage = (hotLiters/getCurrentLiters());
        float coldPercentage = (coldLiters/getCurrentLiters());

        //Next, we multiply the percentage of each temperature by the Tap temperatures.
        int currentHotTemperature = Math.round(hotPercentage * hotTemperature);
        int currentColdTemperature = Math.round(coldPercentage * coldTemperature);

        return currentHotTemperature+currentColdTemperature;
    }

    /**
     * Returns the current liters of the bath, calculated adding Hot Liters with Cold Liters
     * @return The current liters of the bath
     */
    public float getCurrentLiters()
    {
        return ConversionUtils.roundFloat(hotLiters+coldLiters,2);
    }

    /**
     * Tells if thebath is full or not
     * @return True if the current bathtub liters are greather or equal to its capacity, False otherwise
     */
    public boolean isFull()
    {
        return ConversionUtils.greatherThanOrEqualFloat(getCurrentLiters(),CAPACITY);
    }

    /**
     * Indicates if the cold tap can be turned on without causing overflow
     * @return True if turning on cold tap don't cause an overflow, false otherwise
     */
    public boolean canTurnOnColdTap()
    {
        return ConversionUtils.lowerThanOrEqualFloat(getCurrentLiters()+getColdLitersPerSecond(),CAPACITY);
    }

    /**
     * Indicates if the hot tap can be turned on without causing overflow
     * @return True if turning on hot tap don't cause an overflow, false otherwise
     */
    public boolean canTurnOnHotTap()
    {
        return ConversionUtils.lowerThanOrEqualFloat(getCurrentLiters()+getHotLitersPerSecond(),CAPACITY);
    }


}
