package com.jordic.bathtubtest.util;

import java.math.BigDecimal;

/**
 * Created by J on 03/01/2017.
 */

public class ConversionUtils {

    /**
     * Round a float with a number of decimals. Example, if we have 4.6496 and we want to round to 2 decimals,
     * then the result will be 4.75
     * @param f Float to round
     * @param decimalPosition Number of decimals which we want to round (in the example this parameter would be 2)
     * @return The float rounded
     */
    public static float roundFloat(float f, int decimalPosition)
    {
        BigDecimal bigDecimal = new BigDecimal(f);
        bigDecimal=bigDecimal.setScale(decimalPosition,BigDecimal.ROUND_HALF_UP);
        return  bigDecimal.floatValue();
    }

    /**
     * A float cannot be compared just with the operator "==". Due to its imprecision,
     * we substract each one, and if the result is minor to a very small number, we assume that they are equal.
     * @param numberOne First number to compare
     * @param numberTwo Second number to compare
     * @return True if they are almost equal, False otherwise
     */
    public static boolean equalsFloat(float numberOne, float numberTwo)
    {
        return Math.abs(numberOne-numberTwo)<0.000001f;
    }

    /**
     * A float cannot be compared just with the operator "==". Due to its imprecision,
     * we substract each one, and if the result is minor to a very small number, we assume that they are equal.
     * And we check as well if numberOne is greather than numberTwo
     * @param numberOne First number to compare
     * @param numberTwo Second number to compare
     * @return True if they are almost equal or if numberOne is greather than numberTwo, False otherwise
     */
    public static boolean greatherThanOrEqualFloat(float numberOne, float numberTwo)
    {
        return Math.abs(numberOne-numberTwo)<0.000001f || numberOne>numberTwo;
    }

    /**
     * A float cannot be compared just with the operator "==". Due to its imprecision,
     * we substract each one, and if the result is minor to a very small number, we assume that they are equal.
     * And we check as well if numberOne is lower than numberTwo
     * @param numberOne First number to compare
     * @param numberTwo Second number to compare
     * @return True if they are almost equal or numberOne is lower than numberTwo, False otherwise
     */
    public static boolean lowerThanOrEqualFloat(float numberOne, float numberTwo)
    {
        return Math.abs(numberOne-numberTwo)<0.000001f || numberOne<numberTwo;
    }
}
